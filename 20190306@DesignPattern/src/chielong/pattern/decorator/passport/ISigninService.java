package chielong.pattern.decorator.passport;

import chielong.pattern.adapter.login.ResultMsg;

public interface ISigninService {
    ResultMsg register(String username , String password);

    ResultMsg login(String username , String password);
}
