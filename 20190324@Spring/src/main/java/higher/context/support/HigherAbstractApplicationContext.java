package higher.context.support;

public abstract class HigherAbstractApplicationContext {
    // 只提供给子类实现,希望是模板模式
    public abstract void refresh();
}
