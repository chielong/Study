package chielong.study.mebatisplus.binding;

import chielong.study.mebatisplus.session.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 维护接口和工厂类关系，用于获取MapperProxy代理对象
 * 工厂类指定了POJO类型，用于处理结果集返回
 *
 * Created by chielong on 2019-05-10.
 */
public class MapperProxyRegistry {
    private final Map<Class<?> , MapperProxyFactory> knowMappers = new HashMap<>();

    /**
     * 在Configuration中解析接口上的注解时，存入接口和工厂类的映射关系
     * 在此处传入pojo类型，是为了最终处理结果集的时候将结果转化为POJO类型
     *
     * @param clazz
     * @param pojo
     * @param <T>
     */
    public <T> void addMapper(Class<T> clazz , Class pojo) {
        knowMappers.put(clazz , new MapperProxyFactory(clazz , pojo));
    }

    public <T> T getMapper(Class<T> clazz , DefaultSqlSession sqlSession) {
        MapperProxyFactory proxyFactory = knowMappers.get(clazz);

        if(proxyFactory == null) {
            throw new RuntimeException("Type : " + clazz + " can not find");
        }

        return (T) proxyFactory.newProxyInstance(sqlSession);
    }

}
