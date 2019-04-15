package higher.context;

import higher.annotation.HigherAutowired;
import higher.annotation.HigherController;
import higher.annotation.HigherService;
import higher.beans.HigherBeanWrapper;
import higher.beans.config.HigherBeanPostProcessor;
import higher.core.HigherBeanFactory;
import higher.beans.config.HigherBeanDefinition;
import higher.beans.support.HigherBeanDefinitionReader;
import higher.beans.support.HigherDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class HigherApplicationContext extends HigherDefaultListableBeanFactory implements HigherBeanFactory {
    private String[] configLocations;
    private HigherBeanDefinitionReader beanDefinitionReader;
    private Map<String , Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();
    private Map<String , HigherBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<String, HigherBeanWrapper>();

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
        try {
            for(Map.Entry<String , HigherBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
                String beanName = beanDefinitionEntry.getKey(); // factoryBeanName
                if(!beanDefinitionEntry.getValue().isLazyInit()) { // beanDefinition.isLazyInit?
                    getBean(beanName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public Object getBean(String beanName) throws Exception {
        HigherBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        Object instance = null;

        HigherBeanPostProcessor postProcessor = new HigherBeanPostProcessor();
        postProcessor.postProcessBeforeInitialization(instance , beanName);

        instance = instantiateBean(beanName , beanDefinition);

        HigherBeanWrapper beanWrapper = new HigherBeanWrapper(instance);
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);

        populateBean(beanName , new HigherBeanDefinition() , beanWrapper);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInsatnce();
    }

    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return this.getBean(beanClass.getName());
    }

    private void populateBean(String beanName, HigherBeanDefinition higherBeanDefinition, HigherBeanWrapper higherBeanWrapper) {
        Object instance = higherBeanWrapper.getWrappedInsatnce();
        Class<?> clazz = higherBeanWrapper.getWrapperClass();

        if(!(clazz.isAnnotationPresent(HigherController.class) || clazz.isAnnotationPresent(HigherService.class))) {
            return ;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if(!field.isAnnotationPresent(HigherAutowired.class)) {
                continue;
            }

            HigherAutowired higherAutowired = field.getAnnotation(HigherAutowired.class);
            String autowiredBeanName = higherAutowired.value().trim();
            if("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }

            field.setAccessible(true);
            try {
                if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null) {
                    continue;
                }

                field.set(instance , this.factoryBeanInstanceCache.get(beanName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object instantiateBean(String beanName, HigherBeanDefinition higherBeanDefinition) {
        // 1.得到要实例化的对象的类名
        // 2.反射实例化，得到一个对象
        // 3.把这个对象封装为BeanWrapper
        // 4.把BeanWrapper放到ioc容器中

        String className = higherBeanDefinition.getBeanClassName();
        Object instance = null;
        try {
            if (this.factoryBeanObjectCache.containsKey(className)) {
                instance = this.factoryBeanObjectCache.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.factoryBeanObjectCache.put(className , instance);
                this.factoryBeanObjectCache.put(higherBeanDefinition.getFactoryBeanName() , instance);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return  instance;
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.beanDefinitionReader.getConfig();
    }
}
