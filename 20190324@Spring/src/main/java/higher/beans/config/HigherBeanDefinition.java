package higher.beans.config;

import lombok.Getter;
import lombok.Setter;

import javax.management.ConstructorParameters;

@Getter
@Setter
public class HigherBeanDefinition {
    /**
     * 类名
     * 是否延迟加载
     * 在容器中的名字
     */

    private String beanClassName;

    private boolean lazyInit = false;

    private String factoryBeanName;

    private boolean singleton = true;

    public HigherBeanDefinition() {
    }

    public HigherBeanDefinition(String beanClassName, String factoryBeanName) {
        this.beanClassName = beanClassName;
        this.factoryBeanName = factoryBeanName;
    }
}
