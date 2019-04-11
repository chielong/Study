package higher.context;

import higher.core.HigherBeanFactory;
import higher.beans.config.HigherBeanDefinition;
import higher.beans.support.HigherBeanDefinitionReader;
import higher.beans.support.HigherDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;

public class HigherApplicationContext extends HigherDefaultListableBeanFactory implements HigherBeanFactory {
    private String[] configLocations;
    private HigherBeanDefinitionReader beanDefinitionReader;

    public HigherApplicationContext(String... configLocations) {
        this.configLocations = configLocations;

        refresh();
    }

    /**
     * 1.定位配置文件
     * 2.加载配置文件，扫描相关类，把他们封装成BeanDefinition
     * 3.注册，把配置信息放到ioc容器里面
     * 4.把不是延迟加载的类，提前初始化
     */
    @Override
    public void refresh() {
        this.beanDefinitionReader = new HigherBeanDefinitionReader(this.configLocations);
        List<HigherBeanDefinition> beanDefinitions = this.beanDefinitionReader.loadBeanDefinitions();
        doRegisterBeanDefinition(beanDefinitions);
        doAutowrited();
    }

    private void doAutowrited() {
        for(Map.Entry<String , HigherBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()) {
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<HigherBeanDefinition> beanDefinitions) {
        for(HigherBeanDefinition beanDefinition : beanDefinitions) {
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                System.out.println("the " + beanDefinition.getFactoryBeanName() + " is existes!");
                continue;
            }

            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName() , beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
