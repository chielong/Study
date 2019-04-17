package higher.aop.aspect;

import java.lang.reflect.Method;

/**
 * Created by chielong on 2019-04-16.
 */
public interface HigherJoinPoint {
    Object getThis();
    Object[] getArguments();
    Method getMethod();
    void setUserAttribute(String key , Object value);
    Object getUserAttribute(String key);
}
