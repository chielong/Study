package chielong.pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKMama implements InvocationHandler {
    private Girl target;

    public Object getInstance(Girl target) throws Exception {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader() , clazz.getInterfaces() , this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.target , args);
        after();
        return obj;
    }

    public void before() {
        System.out.println("i'm mama");
    }

    public void after() {
        System.out.println("see you soon");
    }
}
