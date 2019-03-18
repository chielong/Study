package chielong.pattern.decorator.passport;

import chielong.pattern.adapter.login.ResultMsg;

public class SigninForThirdService implements ISigninForThirdService {
    private ISigninService signinService;

    public SigninForThirdService(ISigninService signinService) {
        this.signinService = signinService;
    }

    @Override
    public ResultMsg loginForQQ(String id) {
        return null;
    }

    @Override
    public ResultMsg loginForWeChat(String id) {
        return null;
    }

    @Override
    public ResultMsg register(String username, String password) {
        return signinService.register(username , password);
    }

    @Override
    public ResultMsg login(String username, String password) {
        return signinService.login(username, password);
    }
}
