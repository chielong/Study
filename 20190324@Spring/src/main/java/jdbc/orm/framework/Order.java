package jdbc.orm.framework;

/**
 * sql排序组件
 *
 * Created by chielong on 2019-04-28.
 */
public class Order {
    private boolean ascending;  // 升序还是降序
    private String propertyName; // 哪个字段升序，哪个字段降序

    public String toString() {
        return propertyName + " " + (ascending ? "asc" : "desc");
    }

    protected Order(boolean ascending, String propertyName) {
        this.ascending = ascending;
        this.propertyName = propertyName;
    }

    public static Order asc(String propertyName) {
        return new Order(true , propertyName);
    }

    public static Order desc(String propertyName) {
        return new Order(false , propertyName);
    }
}
