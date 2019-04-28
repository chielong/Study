package jdbc.orm.javax.core.common;

import java.io.Serializable;

/**
 * Created by chielong on 2019-04-22.
 */
public class ResultMsg<T> implements Serializable {
    private static final long serialVersionUID = -820185902400036993L;

    private int status; // 状态码，系统的返回码
    private String msg; // 状态码的解释
    private T data; // 放任意结果

    public ResultMsg() {}

    public ResultMsg(int status) {
        this.status = status;
    }

    public ResultMsg(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResultMsg(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResultMsg(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
