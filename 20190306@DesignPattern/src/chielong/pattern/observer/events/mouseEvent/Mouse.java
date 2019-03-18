package chielong.pattern.observer.events.mouseEvent;

import chielong.pattern.observer.events.core.EventListener;

public class Mouse extends EventListener {
    public void click() {
        System.out.println("调用单击方法");
        this.trigger(MouseEventType.ON_CLICK);
    }

    public void doubleClick() {
        System.out.println("调用双击方法");
        this.trigger(MouseEventType.ON_DOUBLE_CLICK);
    }
}
