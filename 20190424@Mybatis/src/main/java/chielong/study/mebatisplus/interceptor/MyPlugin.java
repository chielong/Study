package chielong.study.mebatisplus.interceptor;

import chielong.study.mebatisplus.annotation.Intercepts;
import chielong.study.mebatisplus.plguin.Interceptor;
import chielong.study.mebatisplus.plguin.Invocation;
import chielong.study.mebatisplus.plguin.Plugin;

import java.util.Arrays;

/**
 * Created by chielong on 2019-05-10.
 */
@Intercepts("query")
public class MyPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String statement = (String) invocation.getArgs()[0];
        Object[] parameter = (Object[]) invocation.getArgs()[1];
        Class pojo = (Class) invocation.getArgs()[2];

        System.out.println("插件输入：SQL：【 " + statement + " 】");
        System.out.println("插件输出：Parameters：" + Arrays.toString(parameter));

        return invocation.proceed();
    }

    @Override
    public Object plguin(Object target) {
        return Plugin.wrap(target , this);
    }
}
