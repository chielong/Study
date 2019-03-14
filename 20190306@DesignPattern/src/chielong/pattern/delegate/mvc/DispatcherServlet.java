package chielong.pattern.delegate.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DispatcherServlet extends HttpServlet {
    private List<Handler> handlerMapping = new ArrayList<Handler>();

    public void init(){
        try {
            Class<?> memberControllerClass = MemberController.class;
            Handler h = new Handler();
            h.setController(memberControllerClass.newInstance());
            h.setMethod(memberControllerClass.getMethod("getMemberById", String.class));
            h.setUrl("/getMemberById.json");
            handlerMapping.add(h);
        } catch (Exception e) {

        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    private void doDispatcher(HttpServletRequest req , HttpServletResponse resp) {
        String uri = req.getRequestURI();
/*
        String mid = req.getParameter("mid");

        if("getMemeberById".equals(uri)) {
            new MemberController().getMemberById(mid);
        } else if("getOrderById".equals(uri)) {
            new OrderController().getOrderById(mid);
        }
*/
        Handler handler = null;
        for(Handler h : handlerMapping) {
            if(uri.equals(h.url)) {
                handler = h;
                break;
            }
        }

        try {
            Object obj = handler.getMethod().invoke(handler.getController() , req.getParameter(""));
            resp.getWriter().write(obj.toString());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class Handler {
        private Object Controller;
        private Method method;
        private String url;

        public Object getController() {
            return Controller;
        }

        public void setController(Object controller) {
            Controller = controller;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
