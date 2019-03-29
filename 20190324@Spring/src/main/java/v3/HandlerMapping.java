package v3;

import annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存了一个url和一个method的关系。
 */
public class HandlerMapping {
    private String url;
    private String method;
    private Object controller;
    //名字和顺序
    private Class<?>[] parameterTypes;

    private Map<String , Integer> paramMap;

    public HandlerMapping(String url, String method, Object controller) {
        this.url = url;
        this.method = method;
        this.controller = controller;
        this.paramMap = new HashMap<String , Integer>();

    }

    private void putParamIndexMapping(Method method) {
        Annotation[][] pa = method.getParameterAnnotations();

        for(int index = 0 ; index < pa.length ; index++) {
            for(Annotation a : pa[index]) {
                if(!(a instanceof RequestParam)) {
                    continue;
                }
                String paramName = ((RequestParam) a).value();
                if(params.containsKey(paramName)) {
                    for(Map.Entry<String , String[]> param : params.entrySet()) {
                        String value = Arrays.toString(params.get(param))
                                .replaceAll("\\[|\\]" , "")
                                .replaceAll("\\s" , ",");
                        paramValues[i] = convert(parameterType , Arrays.toString(param.getValue()));
                    }
                }
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Object getController() {
        return controller;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Map<String, Integer> getParamMap() {
        return paramMap;
    }
}
