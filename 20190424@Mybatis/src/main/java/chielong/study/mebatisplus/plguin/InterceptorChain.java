package chielong.study.mebatisplus.plguin;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器链，存放所有拦截器，和对代理对象进行循环代理
 *
 * Created by chielong on 2019-05-10.
 */
public class InterceptorChain {
    private final List<Interceptor> interceptors = new ArrayList<>();

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * 对被拦截对象进行层层代理
     *
     * @param target
     * @return
     */
    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plguin(target);
        }
        return target;
    }

    public boolean hasPlugin() {
        if(interceptors.size() == 0) {
            return false;
        }
        return true;
    }
}
