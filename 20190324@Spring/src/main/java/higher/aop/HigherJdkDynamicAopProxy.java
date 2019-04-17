package higher.aop;

import higher.aop.intercept.HigherMethodInvocation;
import higher.aop.support.HigherAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by chielong on 2019-04-16.
 */
public class HigherJdkDynamicAopProxy implements HigherAopProxy , InvocationHandler {
    private HigherAdvisedSupport advised;

    public HigherJdkDynamicAopProxy(HigherAdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getPorxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader , this.advised.getTargetClass().getInterfaces() , this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method , this.advised.getTargetClass());
        HigherMethodInvocation invocation = new HigherMethodInvocation(proxy , method , this.advised.getTarget() , args , interceptorsAndDynamicMethodMatchers , this.advised.getTargetClass());
        return invocation.proceed();
    }
}
