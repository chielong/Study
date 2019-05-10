package chielong.study.four.simple;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by chielong on 2019-05-05.
 */
public class MeMapperProxy implements InvocationHandler {
    private MeSqlSession sqlSession;

    public MeMapperProxy(MeSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperInterface = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String statemenId = mapperInterface + "." + methodName;

        return sqlSession.selectOne(statemenId , args[0]);
    }
}
