package chielong.pattern.template.course;

public abstract class NetworkCourse {

    protected boolean needHomework = false;

    public NetworkCourse() {
    }

    public NetworkCourse(boolean needHomework) {
        this.needHomework = needHomework;
    }

    protected final void createCourse() {
        this.postPreResource();

        this.createPPT();

        this.liveVideo();

        this.postNote();

        this.postSource();

        if(needHomework()) {
            checkHomework();
        }
    }

    protected abstract void checkHomework();

    //钩子方法
    protected final boolean needHomework() {
        return needHomework;
    }

    public final void postSource() {
        System.out.println("发布-源代码");
    }

    public final void postNote(){
        System.out.println("发布-笔记");
    }

    public final void liveVideo(){
        System.out.println("直播");
    }

    public final void createPPT(){
        System.out.println("创建ppt");
    }

    public final void postPreResource(){
        System.out.println("发布-预习资料");
    }
}
