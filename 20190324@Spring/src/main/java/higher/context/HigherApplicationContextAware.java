package higher.context;

/**
 * 通过解耦的方式获得ioc容器的顶层设计
 * 后面通过一个监听器去扫描所有的类，
 * 只要实现了此接口
 * 就会自动调用setApplicationContext方法
 * 从而将容器注入到目标类中
 */
public interface HigherApplicationContextAware {
    void setApplicationContext(HigherApplicationContext context);
}
