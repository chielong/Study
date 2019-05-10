package chielong.study.mebatisplus.binding;

import chielong.study.mebatisplus.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * 用于产生MapperProxy代理类
 *
 * Created by chielong on 2019-05-10.
 */
public class MapperProxyFactory<T> {
    private Class<T> mapperInterface;
    private Class object;

    public MapperProxyFactory(Class<T> mapperInterface, Class object) {
        this.mapperInterface = mapperInterface;
        object = object;
    }

    public T newProxyInstance(DefaultSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader() ,
                    new Class[]{mapperInterface} ,
                    new MapperProxy(sqlSession , object));
    }
}
