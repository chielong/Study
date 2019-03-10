package chielong.pattern.Singleton.register;

import java.util.HashMap;
import java.util.Map;

public class ContainerSingleton {
    private static final Map<String , Object> map = new HashMap<String, Object>();
    private ContainerSingleton() {

    }

    //或者是传入className,反射做。
    public Object get(String beanName) {
        Object bean = map.get(beanName);
        if(null != bean) {
            return bean;
        }
        synchronized (ContainerSingleton.class) {
            bean = map.get(beanName);
            if(null == bean) {
                map.put(beanName , new Object());
            }
        }
        return bean;
    }

}
