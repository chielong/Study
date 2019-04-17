package higher.aop.aspect;

import higher.aop.intercept.HigherMethodInterceptor;
import higher.aop.intercept.HigherMethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chielong on 2019-04-16.
 */
public class HigherMethodBeforeAdviceInterceptor extends HigherAbstractAspectAdvice implements HigherAdvice , HigherMethodInterceptor {
    private HigherJoinPoint joinPoint;

    public HigherMethodBeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public void before(Method method , Object[] args , Object target) throws InvocationTargetException, IllegalAccessException {
        super.invokeAdviceMethod(this.joinPoint , null , null);
    }

    @Override
    public Object invoke(HigherMethodInvocation invocation) throws Throwable {
        this.joinPoint = invocation;
        before(invocation.getMethod() , invocation.getArguments() , invocation.getThis());
        return invocation.proceed();
    }
}
