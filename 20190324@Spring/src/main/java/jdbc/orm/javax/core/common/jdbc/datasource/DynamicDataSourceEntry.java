package jdbc.orm.javax.core.common.jdbc.datasource;

import org.aspectj.lang.JoinPoint;

/**
 * Created by chielong on 2019-04-22.
 */
public class DynamicDataSourceEntry {
    public final static String DEFAULT_SOURCE = null;
    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    /**
     * 清空数据源
     */
    public void clear() {
        local.remove();
    }

    /**
     * 获取当前正在使用的数据源的名字
     * @return
     */
    public String get() {
        return local.get();
    }

    /**
     * 还原指定切面的数据源
     * @param join
     */
    public void restore(JoinPoint join) {
        local.set(DEFAULT_SOURCE);
    }

    /**
     * 还原当前切面的数据源
     */
    public void retore() {
        local.set(DEFAULT_SOURCE);
    }

    public void set(String source) {
        local.set(source);
    }

    /**
     * 根据年份动态设置数据源
     * @param year
     */
    public void set(int year) {
        local.set("DB_" + year);
    }
}
