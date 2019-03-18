package chielong.pattern.adapter.login.v2;

import chielong.pattern.adapter.login.ResultMsg;

public interface IPassportForThird {

    /**
     * QQ登陆
     * @param id
     * @return
     */
    ResultMsg loginForQQ(String id);

    /**
     * 微信登陆
     * @param id
     * @return
     */
    ResultMsg loginForWechat(String id);
}
