package chielong.pattern.Singleton.lazy;

public class LazyDlcSingleton {
    //防止指令冲排序
    private volatile static LazyDlcSingleton lazyMan = null;

    private LazyDlcSingleton() {

    }

    //1.synchronized方法->性能问题
    //2.synchronized块
    public static LazyDlcSingleton get() {
        if(null == lazyMan) {
            synchronized (LazyDlcSingleton.class) {
                if(null == lazyMan) {
                    lazyMan = new LazyDlcSingleton();
                    /**
                     * 对象初始化过程
                     * 1。分配对象内存
                     * 2。初始化对象
                     * 3。初始化好的对象与内存地址建立关联（赋值）
                     * 4。用户初次访问
                     */
                }
            }
        }

        return  lazyMan;
    }
}
