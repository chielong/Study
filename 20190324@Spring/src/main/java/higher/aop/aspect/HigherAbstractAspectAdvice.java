package higher.aop.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chielong on 2019-04-16.
 */
public class HigherAbstractAspectAdvice implements HigherAdvice {
    private Method aspectMethod;
    private Object aspectTarget;

    public HigherAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public Object invokeAdviceMethod(HigherJoinPoint joinPoint , Object returnValue , Throwable tx) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length == 0) {
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[paramTypes.length];
            for(int i = 0 ; i < paramTypes.length ; i++) {
                if(paramTypes[i] == HigherJoinPoint.class) {
                    args[i] = joinPoint;
                } else if(paramTypes[i] == Throwable.class) {
                    args[i] = tx;
                } else if (paramTypes[i] == Object.class) {
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget , args);
        }

    }
}
