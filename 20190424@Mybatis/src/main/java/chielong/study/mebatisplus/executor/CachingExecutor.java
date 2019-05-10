package chielong.study.mebatisplus.executor;

import chielong.study.mebatisplus.cache.CacheKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 带缓存的执行器，用于装饰基本执行器
 *
 * Created by chielong on 2019-05-10.
 */
public class CachingExecutor implements Executor {
    private Executor delegate;
    private static final Map<Integer , Object> cache = new HashMap<>();

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(statement);
        cacheKey.update(joinStr(parameter));

        if(cache.containsKey(cacheKey.getCode())) {
            System.out.println("命中缓存");
            return (T)cache.get(cacheKey.getCode());
        } else {
            Object obj = delegate.query(statement, parameter, pojo);
            cache.put(cacheKey.getCode() , obj);
            return (T)obj;
        }
    }

    private Object joinStr(Object[] objs) {
        StringBuffer sb = new StringBuffer();
        if(objs != null && objs.length > 0) {
            for (Object obj : objs) {
                sb.append(obj.toString() + ",");
            }
        }

        int len = sb.length();
        if(len > 0) {
            sb.deleteCharAt(len - 1);
        }
        return sb.toString();
    }
}
