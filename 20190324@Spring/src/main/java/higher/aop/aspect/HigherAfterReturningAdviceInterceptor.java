package higher.aop.aspect;

import higher.aop.intercept.HigherMethodInterceptor;
import higher.aop.intercept.HigherMethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by chielong on 2019-04-16.
 */
public class HigherAfterReturningAdviceInterceptor extends HigherAbstractAspectAdvice implements HigherAdvice , HigherMethodInterceptor {
    private HigherJoinPoint joinPoint;

    public HigherAfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(HigherMethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        this.joinPoint = invocation;
        this.afterReturning(retVal , invocation.getMethod() , invocation.getArguments() , invocation.getThis());

        return null;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable{
        super.invokeAdviceMethod(this.joinPoint , retVal , null);
    }
}
