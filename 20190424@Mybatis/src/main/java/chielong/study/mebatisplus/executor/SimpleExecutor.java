package chielong.study.mebatisplus.executor;


/**
 * Created by chielong on 2019-05-10.
 */
public class SimpleExecutor implements Executor {
    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {
        StatementHandler statementHandler = new StatementHandler();
        return statementHandler.query(statement , parameter , pojo);
    }
}
