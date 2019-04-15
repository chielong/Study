package higher.webmvc;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Getter
@Setter
public class HigherHandlerMapping {
    private Object controller;
    private Method method;
    private Pattern pattern;

    public HigherHandlerMapping() {
    }

    public HigherHandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }
}
