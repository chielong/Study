package higher.webmvc;

import annotation.RequestMapping;
import higher.annotation.HigherController;
import higher.annotation.HigherRequestMapping;
import higher.context.HigherApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HigherDispatcherServlet extends HttpServlet {
    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";
    private HigherApplicationContext context;
    private List<HigherHandlerMapping> handlerMappings = new ArrayList<HigherHandlerMapping>();
    private Map<HigherHandlerMapping , HigherHandlerAdapter> handlerAdapterMap = new HashMap<HigherHandlerMapping, HigherHandlerAdapter>();
    private List<HigherViewResolver> viewResolvers = new ArrayList<HigherViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatcher(req , resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception , Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]" , "").replaceAll(",\\s" , "\r\n"));
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        HigherHandlerMapping handler = getHandler(req);
        if(handler == null) {
            processDispatcherResult(req , resp , new HigherModelAndView("404-dino"));
        }
        HigherHandlerAdapter ha = getHandlerAdapter(handler);
        if(ha == null) {
            processDispatcherResult(req , resp , new HigherModelAndView("404-dino"));
        }
        HigherModelAndView mv = ha.handle(req , resp , handler);
        processDispatcherResult(req , resp , mv);
    }

    private void processDispatcherResult(HttpServletRequest req, HttpServletResponse resp, HigherModelAndView mv) {
        if(null == mv) {
            return ;
        }
        if(this.viewResolvers.isEmpty()) {
            return;
        }
        for (HigherViewResolver viewResolver : this.viewResolvers) {
            HigherView view = viewResolver.resolveViewName(mv.getViewName() , null);
            try {
                view.render(mv.getModel() , req , resp);
                return ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private HigherHandlerAdapter getHandlerAdapter(HigherHandlerMapping handler) {
        if(this.handlerAdapterMap.isEmpty()) {
            return null;
        }
        HigherHandlerAdapter ha = this.handlerAdapterMap.get(handler);
        if(ha.supports(handler)) {
            return ha;
        }
        return null;
    }


    private HigherHandlerMapping getHandler(HttpServletRequest req) {
        if(this.handlerMappings.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath , "").replaceAll("/+" , "/");

        for (HigherHandlerMapping handlerMapping : this.handlerMappings) {
            Matcher matcher = handlerMapping.getPattern().matcher(url);
            if(!matcher.matches()) {
                continue;
            }
            return handlerMapping;
        }

        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = new HigherApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        initStrategies(context);
    }

    private void initStrategies(HigherApplicationContext context) {
        // 多文件上传组件
        initMultipartResolver(context);
        // 初始化本地语言环境
        initLocaleResolver(context);
        // 初始化模板处理器
        initThemeResolver(context);
        // handlerMapping,必须实现
        initHandlerMappings(context);
        // 初始化参数适配器，必须实现
        initHandlerAdapters(context);
        // 初始化异常拦截器
        initHandlerExceptionResolvers(context);
        // 初始化视图预处理器
        initRequestToViewNameTrandlator(context);
        // 初始化视图解析器，必须实现
        initViewResolvers(context);
        // 初始化参数缓存器
        initFlashMapManager(context);
        
    }

    private void initFlashMapManager(HigherApplicationContext context) {

    }

    private void initViewResolvers(HigherApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);
        String[] templates = templateRootDir.list();
        for(int i = 0 ; i < templates.length ; i++) {
            this.viewResolvers.add(new HigherViewResolver(templateRoot));
        }
    }

    private void initRequestToViewNameTrandlator(HigherApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(HigherApplicationContext context) {

    }

    private void initHandlerAdapters(HigherApplicationContext context) {
        for (HigherHandlerMapping handlerMapping : this.handlerMappings) {
            this.handlerAdapterMap.put(handlerMapping , new HigherHandlerAdapter());
        }
    }

    private void initHandlerMappings(HigherApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if(!clazz.isAnnotationPresent(HigherController.class)) {
                    continue;
                }
                String baseUrl = "";
                if(clazz.isAnnotationPresent(HigherRequestMapping.class)) {
                    HigherRequestMapping requestMapping = clazz.getAnnotation(HigherRequestMapping.class);
                    baseUrl = requestMapping.value();
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(HigherRequestMapping.class)) {
                        continue;
                    }
                    HigherRequestMapping requestMapping = method.getAnnotation(HigherRequestMapping.class);
                    String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*" , ".*")).replaceAll("/+" , "/");
                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappings.add(new HigherHandlerMapping(controller , method , pattern));
                }

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initThemeResolver(HigherApplicationContext context) {

    }


    private void initLocaleResolver(HigherApplicationContext context) {

    }

    private void initMultipartResolver(HigherApplicationContext context) {

    }
}
