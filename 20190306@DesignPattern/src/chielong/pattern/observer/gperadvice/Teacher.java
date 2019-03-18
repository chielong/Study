package chielong.pattern.observer.gperadvice;

import chielong.pattern.observer.Question;

import java.util.Observable;
import java.util.Observer;

public class Teacher implements Observer {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        GPer gPer = (GPer)o;
        Question question = (Question)arg;
        System.out.println("============");
        System.out.println(name + "老师\n" + "收到" + gPer.getName() + "的提问"
                + "内容" + question.getContent() + "\n提问者" + question.getUserName());
    }
}
