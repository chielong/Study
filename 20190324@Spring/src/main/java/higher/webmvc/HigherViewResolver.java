package higher.webmvc;

import java.io.File;
import java.util.Locale;

public class HigherViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    private final File templateRootDir;

    public HigherViewResolver(String templateRootDirPathStr) {
        String templateRootDirPath = this.getClass().getClassLoader().getResource(templateRootDirPathStr).getFile();
        this.templateRootDir = new File(templateRootDirPath);
    }

    public HigherView resolveViewName(String viewName , Locale locale) {
        if(null == viewName || "".equals(viewName.trim())) {
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName: (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+" , "/"));
        return new HigherView(templateFile);
    }
}
