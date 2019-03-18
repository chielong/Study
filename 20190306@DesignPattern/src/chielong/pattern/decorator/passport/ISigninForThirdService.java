package chielong.pattern.decorator.passport;

import chielong.pattern.adapter.login.ResultMsg;

public interface ISigninForThirdService extends ISigninService {
    ResultMsg loginForQQ(String id);

    ResultMsg loginForWeChat(String id);
}
