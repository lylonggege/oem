package com.zhangying.oem1688.internet;



/**
 * Created by xialihao on 2018/11/22.
 */
public class ResponseException extends RuntimeException {

    private SimpleResult simpleResult;

    public ResponseException(SimpleResult simpleResult) {
        this.simpleResult = simpleResult;
    }

    @Override
    public String getMessage() {
        return simpleResult.getMsg();
    }

    public boolean isLogAgain() {
        return simpleResult.isLogAgain();
    }

}
