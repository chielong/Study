package chielong.study.mebatisplus.session;

/**
 * 会话工厂类，用于解析配置文件，产生sqlSession
 *
 * Created by chielong on 2019-05-10.
 */
public class SqlSessionFactory {
    private Configuration configuration;

    /**
     * build方法用于初始化configuration，
     * 解析配置文件的工作在configuration的构造函数中
     *
     * @return
     */
    public SqlSessionFactory build() {
        configuration = new Configuration();
        return this;
    }

    public DefaultSqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
