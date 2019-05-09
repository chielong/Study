package chielong.study.standalone.interceptor;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by chielong on 2019-05-09.
 */
@Intercepts({
        @Signature(
            type = Executor.class ,
            method = "query" ,
            args = {MappedStatement.class , Object.class , RowBounds.class , ResultHandler.class}
        )
})
public class MyPageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("这是[逻辑分页->物理分页]的插件");

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        BoundSql boundSql = ms.getBoundSql(args[1]);
        RowBounds rb = (RowBounds) args[2];

        if(rb == RowBounds.DEFAULT) {
            return invocation.proceed();
        }

        String sql = boundSql.getSql();
        String limit = String.format("LIMIT %d , %d" , rb.getOffset() , rb.getLimit());
        sql = sql + " " + limit;

        SqlSource sqlSource = new StaticSqlSource(ms.getConfiguration() , sql , boundSql.getParameterMappings());
        Field field = MappedStatement.class.getDeclaredField("sqlSource");
        field.setAccessible(true);
        field.set(ms , sqlSource);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target , this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
