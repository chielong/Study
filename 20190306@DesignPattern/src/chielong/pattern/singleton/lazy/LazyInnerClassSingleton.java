package chielong.pattern.singleton.lazy;


/**
 * 没用到sync关键字，性能更好
 * 反射可以破坏
 *      构造方法做判断
 * 序列化可以破坏！
 *      ObjectInputStream
 *      ObjectOutputStream
 *      需要重构readReslove方法，只不过是覆盖了反序列化出来的对象
 *      还是创建了两次，发生在JVM层面，相对来说比较安全
 *      之前反序列化的对象被GC
 */
public class LazyInnerClassSingleton {

    private  LazyInnerClassSingleton() {
        //可以利用反射搞出来
        //强迫不能反射搞出来
        if(null != LazyHolder.LAZY) {
            throw new RuntimeException("让你不听话");
        }

    }

    public static final LazyInnerClassSingleton get() {
        return LazyHolder.LAZY;
    }


    //LazyHolder里面的逻辑需要等待外面方法调用时才执行
    //巧妙的利用了内部类特性
    private static class LazyHolder {
        private static final  LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
