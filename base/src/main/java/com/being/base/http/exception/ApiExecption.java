package com.being.base.http.exception;

/**
 * Created by zhangpeng on 17/2/21.
 */

public class ApiExecption extends Throwable {

    protected int mCode;

    public ApiExecption(int code, String message) {
        super(message);
        mCode = code;
    }

    @Override
    public String toString() {
        return "ApiExecption{" +
                "Code=" + mCode +
                "} " + super.toString();
    }
}
