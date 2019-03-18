package chielong.pattern.adapter.login.v2.adapters;

import chielong.pattern.adapter.login.ResultMsg;

public interface LoginAdapter {
    boolean support(Object adapter);
    ResultMsg login(String id , Object adapter);
}
