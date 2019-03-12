package chielong.pattern.proxy.dbroute;

public class DynamicDataSourceEntity {
    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    public final static String DEFAULT_SOURCE = null;

    private DynamicDataSourceEntity() {
    }

    public void restore() {
        local.set(DEFAULT_SOURCE);
    }

    public static String get() {
        return local.get();
    }

    public static void set(String source) {
        local.set(source);
    }

    public static void set(int year) {
        local.set(year + "");
    }
}
