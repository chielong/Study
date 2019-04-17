package higher.aop;

/**
 * Created by chielong on 2019-04-16.
 */
public interface HigherAopProxy {
    Object getPorxy();

    Object getProxy(ClassLoader classLoader);
}
