package chielong.pattern.Singleton.seriable;

import java.io.Serializable;

//反序列化将破坏单例
public class SeriableSingleton implements Serializable {
    public final static SeriableSingleton INSTANCE = new SeriableSingleton();

    private SeriableSingleton() {

    }

    public static SeriableSingleton getInstance() {
        return INSTANCE;
    }

    //解决：重写readResolve方法
    private Object readResolve() {
        return INSTANCE;
    }
}
