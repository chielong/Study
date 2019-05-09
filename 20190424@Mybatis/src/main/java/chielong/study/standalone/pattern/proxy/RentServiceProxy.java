package chielong.study.standalone.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by chielong on 2019-05-09.
 */
public class RentServiceProxy implements InvocationHandler {
    private Object target;

    public RentServiceProxy(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader() , target.getClass().getInterfaces() , this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("中介：这个是房子[" + args[0] + "]的照片");
        //执行方法，相当于调用HelloServiceImpl的sayHello方法
        Object result = method.invoke(target, args);
        //反射方法调用后
        System.out.println("中介：带领验房[" + args[0] + "]，交尾款和押金，交钥匙");
        return result;
    }
}
