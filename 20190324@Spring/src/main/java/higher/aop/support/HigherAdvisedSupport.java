package higher.aop.support;

import higher.aop.aspect.HigherAfterReturningAdviceInterceptor;
import higher.aop.aspect.HigherAfterThrowingAdviceInterceptor;
import higher.aop.aspect.HigherMethodBeforeAdviceInterceptor;
import higher.aop.config.HigherAopConfig;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chielong on 2019-04-16.
 */
public class HigherAdvisedSupport {
    @Getter
    public Class<?> targetClass;
    @Getter
    @Setter
    private Object target;
    private HigherAopConfig config;
    private Pattern pointCutClassPattern;
    private transient Map<Method , List<Object>> methodCache;

    public HigherAdvisedSupport(HigherAopConfig config) {
        this.config = config;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method , Class<?> targetClass) throws Exception {
        List<Object> cached = methodCache.get(method);
        if(cached == null) {
            Method m = targetClass.getMethod(method.getName() , method.getParameterTypes());
            cached = methodCache.get(m);
            this.methodCache.put(m , cached);
        }
        return cached;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    private void parse() {
        String pointCut = config.getPointCut().replaceAll("\\." , "\\\\.").replaceAll("\\\\.\\*" , ".*")
                                .replaceAll("\\(" , "\\\\(").replaceAll("\\)" , "\\\\)");
        String pointurForClassRegex = pointCut.substring(0 , pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class" + pointurForClassRegex.substring(pointurForClassRegex.lastIndexOf(" ") + 1));

        try {
            methodCache = new HashMap<Method, List<Object>>();
            Pattern pattern = Pattern.compile(pointCut);

            Class<?> aspectClass = Class.forName(this.config.getAspectClass());
            Map<String , Method> aspectMethods = new HashMap<String , Method>();
            for(Method m : aspectClass.getMethods()) {
                aspectMethods.put(m.getName() , m);
            }

            for (Method method : this.targetClass.getMethods()) {
                String methodString = method.toString();
                if(methodString.contains("throws")) {
                    methodString = methodString.substring(0 , methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pattern.matcher(methodString);
                if(matcher.matches()) {
                    List<Object> advices = new LinkedList<Object>();
                    if(!(null == config.getAspectBefore()) || "".equals(config.getAspectBefore())) {
                        advices.add(new HigherMethodBeforeAdviceInterceptor(aspectMethods.get(config.getAspectBefore()) , aspectClass.newInstance()));
                    }
                    if(!(null == config.getAspectAfter() || "".equals(config.getAspectAfter()))) {
                        advices.add(new HigherAfterReturningAdviceInterceptor(aspectMethods.get(config.getAspectBefore()) , aspectClass.newInstance()));
                    }

                    if(!(null == config.getAspectAfterThrow() || "".equals(config.getAspectAfterThrow()))) {
                        HigherAfterThrowingAdviceInterceptor throwingAdvice = new HigherAfterThrowingAdviceInterceptor(
                                        aspectMethods.get(config.getAspectAfterThrow()) ,
                                        aspectClass.newInstance());
                        throwingAdvice.setThrowingName(config.getAspectAfterThrowingName());
                        advices.add(throwingAdvice);
                    }
                    methodCache.put(method , advices);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

}
