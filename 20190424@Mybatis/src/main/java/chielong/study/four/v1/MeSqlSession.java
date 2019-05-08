package chielong.study.four.v1;

/**
 * Created by chielong on 2019-05-05.
 */
public class MeSqlSession {
    private MeExecutor executor;
    private MeConfiguration configuration;

    public MeSqlSession(MeExecutor executor, MeConfiguration configuration) {
        this.executor = executor;
        this.configuration = configuration;
    }

    /**
     * 调用executor执行单表查询
     * @param statementId namespace.methodName
     * @param parameter
     * @param <T>
     * @return
     */
    public <T> T selectOne(String statementId , Object parameter) {
        String sql = configuration.sqlMappings.getString(statementId);

        return executor.query(sql , parameter);
    }

    /**
     * 获取一个代理对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz , this);
    }

}
