package chielong.study.mebatisplus.session;

import chielong.study.mebatisplus.executor.Executor;

import java.sql.Statement;

/**
 * Created by chielong on 2019-05-10.
 */
public class DefaultSqlSession {
    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = configuration.newExecutor();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz ,this);
    }

    public Object selectOne(String statementId, Object[] args, Class pojo) {
        String sql = getConfiguration().getMappedStatement(statementId);
        return executor.query(sql , args , pojo);
    }
}
