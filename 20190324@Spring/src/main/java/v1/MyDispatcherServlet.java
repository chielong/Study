package v1;

import v1.annotation.Autowired;
import v1.annotation.Controller;
import v1.annotation.RequestMapping;
import v1.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 *  关于beanName默认是类名，但类名首字母小写的情况：
 *  在put进map的时候直接：chars[0] |= (1 << 5)
 *  这样做不管首字母是否是小写，都做到了放入小写，即：
 *  类名:MyDispatcherServlet -> myDispatcherServlet
 *  类名:myDispatcherServlet -> myDispatcherServlet
 *
 *  但是这样必须放入的是getSimpleName();
 */
public class MyDispatcherServlet extends HttpServlet {
    private Properties properties = new Properties();
    private List<String> classNames = new ArrayList<String>();
    private Map<String , Object> ioc = new HashMap<String , Object>();
    private Map<String , Method> handlerMapping = new HashMap<String , Method>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2.扫描相关的类
        doScanner(properties.getProperty("scanPackage"));

        //3.初始化扫描到的类，并且将它们放入到ioc容器之中
        doInstance();
        
        //4.依赖注入
        doAutowired();
        
        //5.初始化HandlerMapping
        initHandleMapping();
    }

    public void doDispatch(HttpServletRequest request , HttpServletResponse response) {
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replaceAll(contextPath , "").replaceAll("/+" , "/");

        if(!this.handlerMapping.containsKey(url)) {
            try {
                response.getWriter().write("404");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ;
        }

        Method method = this.handlerMapping.get(url);
        //弄丢了了对象->弄丢了key
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        try {
            method.invoke(ioc.get(beanName) , new Object[] {request , response , null});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void initHandleMapping() {
        if(ioc.isEmpty()) {
            return;
        }

        for(Map.Entry<String , Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();

            if(!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }

            String baseUrl = "";
            if(clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value();
            }

            for(Method method : clazz.getDeclaredMethods()) {
                if(!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }

                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String url = requestMapping.value();
                String trueUrl = ("/" + baseUrl + "/" + url).replace("/+" , "/");
                handlerMapping.put(trueUrl , method);
            }

        }
    }

    private void doAutowired() {
        if(ioc.isEmpty()) {
            return;
        }

        for(Map.Entry<String , Object> enrty : ioc.entrySet()) {
            Field[] fields = enrty.getValue().getClass().getDeclaredFields();
            for(Field field : fields) {
                if(field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String beanName = autowired.value();
                    if("".equals(beanName)) {
                        beanName = toLowerFirstCase(field.getType().getName());
                    }

                    field.setAccessible(true);
                    try {
                        field.set(enrty.getValue() , ioc.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void doInstance() {
        if(classNames.isEmpty()) {
            return ;
        }
        try{
            for(String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(Controller.class)) {
                    Object instance = clazz.newInstance();
                    ioc.put(toLowerFirstCase(clazz.getName()) , instance);
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = clazz.getAnnotation(Service.class);
                    String beanName = service.value();
                    if("".equals(beanName)) {
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }

                    Object instance = clazz.newInstance();
                    ioc.put(beanName , instance);

                    for(Class<?> i : clazz.getInterfaces()) {
                        ioc.put(i.getName() , instance);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String toLowerFirstCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] |= (1 << 5);
        return String.valueOf(chars);
    }

    private void doScanner(String scanPackge) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackge.replaceAll("\\." , "/"));

        File classPath = new File(url.getFile());
        for(File file : classPath.listFiles()) {
            if(file.isDirectory()) {
                doScanner(scanPackge + "." + file.getName());
            } else if(file.isFile()) {
                if(!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackge + "." + file.getName().replace(".class" , ""));
                classNames.add(className);
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
