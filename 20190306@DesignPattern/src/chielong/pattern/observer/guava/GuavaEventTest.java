package chielong.pattern.observer.guava;

import com.google.common.eventbus.EventBus;

public class GuavaEventTest {
    public static void main(String[] args) {
        EventBus student = new EventBus();
        GuavaTeacher tom = new GuavaTeacher("tom");
        GuavaTeacher mic = new GuavaTeacher("mic");

        student.register(tom);
        student.register(mic);

        student.post("我什么时候才能有女朋友!");
    }
}
