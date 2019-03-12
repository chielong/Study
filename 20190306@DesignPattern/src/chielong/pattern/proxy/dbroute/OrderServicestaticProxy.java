package chielong.pattern.proxy.dbroute;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServicestaticProxy implements IOrderService {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    private IOrderService orderService;

    public OrderServicestaticProxy(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public int createOrder(Order order) {
        Long time = order.getCreatetime();
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("dbRouter=" + dbRouter);
        this.orderService.createOrder(order);

        return 0;
    }
}
