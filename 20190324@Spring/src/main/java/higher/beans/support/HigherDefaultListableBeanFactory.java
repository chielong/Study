package higher.beans.support;


import higher.beans.config.HigherBeanDefinition;
import higher.context.support.HigherAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HigherDefaultListableBeanFactory extends HigherAbstractApplicationContext {

    protected final Map<String , HigherBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, HigherBeanDefinition>();

    @Override
    public void refresh() {

    }
}
