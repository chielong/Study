package chielong.study.mebatisplus.plguin;

/**
 * 拦截器接口，所有自定义拦截器必须实现此接口
 *
 * Created by chielong on 2019-05-10.
 */
public interface Interceptor {

    /**
     * 插件的核心逻辑实现
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    Object intercept(Invocation invocation) throws Throwable;

    /**
     * 对被拦截对象进行代理
     *
     * @param target
     * @return
     */
    Object plguin(Object target);
}
