package higher.beans;

public class HigherBeanWrapper {
    private Object wrappedInsatnce;
    private Class<?> wrapperClass;

    public HigherBeanWrapper(Object wrappedInsatnce) {
        this.wrappedInsatnce = wrappedInsatnce;
    }

    public Object getWrappedInsatnce() {
        return wrappedInsatnce;
    }

    public Class<?> getWrapperClass() {
        return this.wrappedInsatnce.getClass();
    }
}
