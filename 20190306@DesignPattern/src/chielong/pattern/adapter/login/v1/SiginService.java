package chielong.pattern.adapter.login.v1;

import chielong.pattern.adapter.login.Member;
import chielong.pattern.adapter.login.ResultMsg;

public class SiginService {

    /**
     * 注册方法
     *
     * @param username
     * @param password
     * @return
     */
    public ResultMsg regist(String username , String password) {
        return new ResultMsg(200 , "注册成功" , new Member());
    }

    /**
     * 登陆的方法
     *
     * @param username
     * @param passwrod
     * @return
     */
    public ResultMsg login(String username, String passwrod) {
        return null;
    }
}
