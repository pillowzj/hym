package com.hym.project;

/**
 * 返回的JSON数据结构标准
 *
 * @param <T>
 */
public class ResponseWraper<T> {

    private String resultCode;

    private String resultMsg;

    private T data;

    private T chainData;

    public ResponseWraper(T data) {
        this.data = data;
    }

    public ResponseWraper(String resultCode) {
        this.resultCode = resultCode;
    }

    public ResponseWraper(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public ResponseWraper(String resultCode, T data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public ResponseWraper(String resultCode, String resultMsg, T data) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }

    public ResponseWraper(String resultCode, String resultMsg, T data, T chainData) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
        this.chainData=chainData;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getChainData() {
        return chainData;
    }

    public void setChainData(T chainData) {
        this.chainData = chainData;
    }
}
