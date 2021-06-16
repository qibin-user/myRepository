package com.bjpowernode.crm.commons.domain;

/**
 * 齐斌
 * 2021/5/20
 *
 *
 *
 * 返回的obj
 */
public class ReturnObject {
    private String condition;//condition：1等于成功，0等于失败
    private String message;//message: 消息，返回失败的消息原因
    private Object returnData;//成功时返回的data

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }
}
