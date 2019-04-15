package higher.beans.config;

public class HigherBeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean , String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean , String beanName) throws Exception {
        return bean;
    }
}
