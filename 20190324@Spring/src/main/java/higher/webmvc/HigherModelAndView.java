package higher.webmvc;

import lombok.Getter;

import java.util.Map;

@Getter
public class HigherModelAndView {
    private final String viewName;
    private Map<String , ?> model;

    public HigherModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public HigherModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }
}
