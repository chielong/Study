package chielong.study.mebatisplus.executor;

/**
 * Created by chielong on 2019-05-10.
 */
public interface Executor {
    <T> T query(String statement , Object[] parameter , Class pojo);
}
