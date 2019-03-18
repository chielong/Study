package chielong.pattern.adapter.login.v2.adapters;

import chielong.pattern.adapter.login.ResultMsg;

public class RegisterForQQAdapter implements RegisterAdapter , LoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return false;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
