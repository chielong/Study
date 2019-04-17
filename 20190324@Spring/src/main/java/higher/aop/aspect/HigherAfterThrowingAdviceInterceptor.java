package higher.aop.aspect;

import higher.aop.intercept.HigherMethodInterceptor;
import higher.aop.intercept.HigherMethodInvocation;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * Created by chielong on 2019-04-16.
 */
public class HigherAfterThrowingAdviceInterceptor extends HigherAbstractAspectAdvice implements  HigherAdvice , HigherMethodInterceptor {
    @Setter
    private String throwingName;

    public HigherAfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(HigherMethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable throwable) {
            invokeAdviceMethod(invocation , null  , throwable.getCause());
            throw  throwable;
        }
    }

}
