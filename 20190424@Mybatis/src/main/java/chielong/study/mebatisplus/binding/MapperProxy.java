package chielong.study.mebatisplus.binding;

import chielong.study.mebatisplus.session.DefaultSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * MapperProxy代理类，用于代理Mapper接口
 *
 * Created by chielong on 2019-05-10.
 */
public class MapperProxy implements InvocationHandler {
    private DefaultSqlSession sqlSession;
    private Class object;

    public MapperProxy(DefaultSqlSession sqlSession, Class object) {
        this.sqlSession = sqlSession;
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statementId = mapperInterface + "." + methodName;

        if(sqlSession.getConfiguration().hasStatement(statementId)) {
            return sqlSession.selectOne(statementId , args , object);
        }

        return method.invoke(proxy , args);
    }
}
