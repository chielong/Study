package chielong.study.standalone.pattern.proxy;

/**
 * Created by chielong on 2019-05-09.
 */
public class RentServiceImpl implements RentService{
    @Override
    public void rent(String str) {
        System.out.println("房东:身份验证通过，我的房子 [ " + str + " ] 可以出租");
    }
}
