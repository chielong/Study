package higher.aop;

import higher.aop.config.HigherAopConfig;
import higher.aop.support.HigherAdvisedSupport;

/**
 * Created by chielong on 2019-04-16.
 */
public class HigherCglibAopProxy implements HigherAopProxy{
    public HigherCglibAopProxy(HigherAdvisedSupport higherAdvisedSupport) {
    }

    @Override
    public Object getPorxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
