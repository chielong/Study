package chielong.study.mebatisplus.plguin;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对被代理对象进行包装
 *
 * Created by chielong on 2019-05-10.
 */
@Getter
public class Invocation {
    private Object target;
    private Method method;
    private Object[] args;

    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed() throws InvocationTargetException , IllegalAccessException {
        return method.invoke(target , args);
    }
}
