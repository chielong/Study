package chielong.study.four.v1;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
 * Created by chielong on 2019-05-05.
 */
public class MeConfiguration {
    public final static ResourceBundle sqlMappings;

    static {
        sqlMappings = ResourceBundle.getBundle("mebatisSQL.properties");
    }


    /**
     * 返回接口的代理类对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> clazz , MeSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader() , new Class[]{clazz} , new MeMapperProxy(sqlSession));
    }
}
