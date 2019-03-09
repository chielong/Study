package chielong.pattern.Singleton.hungry;

public class HungrySingleton {
    //低级版本：这里不是static，那么如果用反射，就会有不止一个hungryMan
    private static final HungrySingleton hungryMan = new HungrySingleton();

    private HungrySingleton() {

    }

    public static HungrySingleton get() {
        return hungryMan;
    }
}
