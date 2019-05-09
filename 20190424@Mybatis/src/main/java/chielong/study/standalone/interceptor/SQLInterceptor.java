package chielong.study.standalone.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * Created by chielong on 2019-05-09.
 */
@Intercepts(
        @Signature(
                type = StatementHandler.class ,
                method = "query" ,
                args = {Statement.class , ResultHandler.class}
        )
)
public class SQLInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        System.out.println("获取sql : " + sql );

        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("sql执行耗时 : " + (endTime - startTime) + "ms");
        }

    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o , this);
    }

    @Override
    public void setProperties(Properties properties) {
        String chielong = properties.getProperty("chielong");
        System.out.println("获取到的参数为:" + chielong);
    }
}
