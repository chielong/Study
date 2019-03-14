package chielong.pattern.strategy.pay;

public class MsgResult {
    private int code;
    private String msg;
    private String reason;

    public MsgResult(int code, String msg, String reason) {
        this.code = code;
        this.reason = reason;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgResult{" +
                "code=" + code +
                ", reason=" + reason +
                ", msg='" + msg + '\'' +
                '}';
    }
}
