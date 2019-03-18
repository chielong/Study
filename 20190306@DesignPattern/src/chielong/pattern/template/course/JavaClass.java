package chielong.pattern.template.course;

public class JavaClass extends NetworkCourse {

    @Override
    protected void checkHomework() {
        System.out.println("这是java课程");
    }
}
