package chielong.pattern.adapter.login.v1;

import chielong.pattern.adapter.login.ResultMsg;

import java.util.Hashtable;

public class SigninForThirdService extends SiginService {

    /**
     * chielong认为：
     *      改方法体现了适配器模式在于：从qq的登陆信息->转变为本站的登陆信息
     *      如果按照注释说明，则每次都会注册一个，在实际中根本不可能
     *      所以注册方法会有一个返回值，这个返回值就是一个用户信息
     *      类似于HashTable的put。
     *
     *
     * @param openId
     * @return
     */
    public ResultMsg loginForQQ(String openId) {
        // 1.openId全剧唯一，我们可以把它当作是一个用户名(加长)
        // 2.密码默认QQ_EMPTY
        // 3.注册(在原有系统里创建一个用户)
        // 4.调用原来登陆方法
        return loginForRegist(openId , null);
    }

    public ResultMsg loginForWechat(String openId) {
        return null;
    }

    public ResultMsg loginForToken(String openId) {
        return null;
    }

    public ResultMsg loginForTelphone(String openId) {
        return null;
    }

    public ResultMsg loginForRegist(String username, String password) {
        super.regist(username , null);
        return super.login(username , null);
    }
}
