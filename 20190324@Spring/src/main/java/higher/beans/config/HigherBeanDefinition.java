package higher.beans.config;

import lombok.Getter;
import lombok.Setter;

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

}
