package chielong.pattern.adapter.login.v2;

import chielong.pattern.adapter.login.ResultMsg;
import chielong.pattern.adapter.login.v1.SiginService;
import chielong.pattern.adapter.login.v2.adapters.LoginAdapter;
import chielong.pattern.adapter.login.v2.adapters.LoginForQQAdapter;
import chielong.pattern.adapter.login.v2.adapters.LoginForWechatAdapter;

public class PassportForThirdAdapter extends SiginService implements IPassportForThird {
    @Override
    public ResultMsg loginForQQ(String id) {
        return processLogin(id , LoginForQQAdapter.class);
    }

    @Override
    public ResultMsg loginForWechat(String id) {
        return processLogin(id , LoginForWechatAdapter.class);
    }

    private ResultMsg processLogin(String key , Class<? extends LoginAdapter> clazz) {
        try {
            LoginAdapter adapter = clazz.newInstance();
            if(adapter.support(adapter)) {
                return adapter.login(key , adapter);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
