package higher.webmvc;

import higher.annotation.HigherRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HigherHandlerAdapter {
    public boolean supports(Object handler) {
        return handler instanceof HigherHandlerAdapter;
    }

    public HigherModelAndView handle(HttpServletRequest request , HttpServletResponse response , Object handler) throws InvocationTargetException, IllegalAccessException {
        HigherHandlerMapping handlerMapping = (HigherHandlerMapping) handler;
        Map<String , Integer> paramIndexMapping = new HashMap<String, Integer>();
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
        // eg : public void test(@HigherRequestParam("id") String theId , @HigherRequestParam("name") String theName)
        // => id : 1   name : 2
        for (int i = 0 ; i < pa.length ; i++) {
            for (Annotation annotation : pa[i]) {
                if(!(annotation instanceof HigherRequestParam)) {
                    continue;
                }
                String paramName = ((HigherRequestParam)annotation).value();
                if("".equals(paramName)) {
                    continue;
                }
                paramIndexMapping.put(paramName , i);
            }

        }

        Class<?>[] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0 ; i < paramsTypes.length ; i++) {
            Class<?> type = paramsTypes[i];
            if(type == HttpServletRequest.class || type == HttpServletResponse.class) {
                paramIndexMapping.put(type.getName() , i);
            }
        }

        Map<String , String[]> params = request.getParameterMap();
        Object[] paramValues = new Object[paramsTypes.length];
        for(Map.Entry<String , String[]> parm : params.entrySet()) {
            String value = Arrays.toString(parm.getValue()).replaceAll("\\[|\\]" , "").replaceAll("\\s" , ",");
            if(!paramIndexMapping.containsKey(parm.getKey())) {
                continue;
            }
            int index = paramIndexMapping.get(parm.getKey());
            paramValues[index] = caseStringValue(value , paramsTypes[index]);
        }
        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }
        if(paramIndexMapping.containsKey(HttpResponse.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpResponse.class.getName());
            paramValues[reqIndex] = response;
        }

        Object result = handlerMapping.getMethod().invoke(handlerMapping.getController() , paramValues);
        if(request == null || result instanceof Void) {
            return null ;
        }

        boolean isModelAndView = handlerMapping.getMethod().getReturnType() == HigherModelAndView.class;
        if(isModelAndView) {
            return (HigherModelAndView) result;
        }

        return null;
    }

    private Object caseStringValue(String value, Class<?> paramsType) {
        if(String.class == paramsType) {
            return value;
        } else if(Integer.class == paramsType) {
            return Integer.valueOf(value);
        } else {
            if(value != null) {
                return value;
            }
            return null;
        }
    }
}
