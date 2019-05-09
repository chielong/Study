package chielong.study.standalone.pattern.proxy;

/**
 * Created by chielong on 2019-05-09.
 */
public class ProxyTest {
    public static void main(String[] args) {
        /**
         * 中介帮助房东出租房子的过程
         */
        RentServiceProxy HelloHandler = new RentServiceProxy(new RentServiceImpl());
        RentService proxy = (RentService) HelloHandler.getProxy();
        proxy.rent("3B12D");
    }
}
