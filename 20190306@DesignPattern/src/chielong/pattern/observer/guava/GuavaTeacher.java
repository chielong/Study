package chielong.pattern.observer.guava;

import chielong.pattern.observer.Question;
import com.google.common.eventbus.Subscribe;

public class GuavaTeacher {
    private String name;

    public GuavaTeacher(String name) {
        this.name = name;
    }

    @Subscribe
    public void subscribe(Question question) {
        System.out.println(name + "回答" + question.getUserName() + "的" + question.getContent());
    }
}
