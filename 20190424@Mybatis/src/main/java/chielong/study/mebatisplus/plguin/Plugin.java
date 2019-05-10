package chielong.study.mebatisplus.plguin;

import chielong.study.mebatisplus.annotation.Intercepts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by chielong on 2019-05-10.
 */
public class Plugin implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object obj , Interceptor interceptor) {
        Class clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader() , clazz.getInterfaces() , new Plugin(obj , interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 自定义的插件上有@Intercepts注解，指定了拦截的方法
        if(interceptor.getClass().isAnnotationPresent(Intercepts.class)) {
            // 如果是被拦截的方法，则进入自定义拦截器的逻辑
            if(method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())) {
                return interceptor.intercept(new Invocation(target , method , args));
            }
        }

        // 非陪拦截的方法，执行原逻辑
        return method.invoke(target , method , args);
    }
}
