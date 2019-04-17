package higher.aop.intercept;

/**
 * Created by chielong on 2019-04-16.
 */
public interface HigherMethodInterceptor {
    Object invoke(HigherMethodInvocation invocation) throws Throwable;
}
