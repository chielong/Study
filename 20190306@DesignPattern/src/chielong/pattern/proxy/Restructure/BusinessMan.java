package chielong.pattern.proxy.Restructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BusinessMan implements InvocationHandler {
    private XXBB xxbb;

    public BusinessMan(XXBB xxbb) {
        this.xxbb = xxbb;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我是商人，宝哥要收东西了");
        Object obj = method.invoke(xxbb , args);
        System.out.println("我是商人，全靠宝哥恰饭");

        return obj;
    }
}
