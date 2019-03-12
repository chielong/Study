package chielong.pattern.singleton.register;


/**
 * 注册式，类名+枚举值被JVM保存
 * 所以才是真·单例无双
 *
 * 不能用反射创建枚举
 */
public enum  EnumSingleton {
    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton get() {
        return INSTANCE;
    }
}
