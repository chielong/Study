package chielong.pattern.observer.events.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventListener {
    protected Map<String , Event> eventMap = new HashMap<String, Event>();

    public void addListener(String eventType , Object target) {
        try {
            this.addLisener(eventType , target ,
                    target.getClass().getMethod("on" + toUpperFirstCase(eventType),Event.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param eventType eventMap的key
     * @param target
     * @param callback
     */
    public void addLisener(String eventType , Object target , Method callback) {
        eventMap.put(eventType , new Event(target , callback));
    }

    private void trigger(Event event) {
        event.setSource(this);
        event.setTime(System.currentTimeMillis());

        try {
            if(null != event.getCallback()) {
                event.getCallback().invoke(event.getTarget() , event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 蹩脚，因为初始化的时候没设置进去
     *
     * @param trigger 事件名称
     */
    protected void trigger(String trigger) {
        if(!this.eventMap.containsKey(trigger)) {
            return ;
        }
        trigger(this.eventMap.get(trigger).setTrigger(trigger));
    }

    private String toUpperFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }

}
