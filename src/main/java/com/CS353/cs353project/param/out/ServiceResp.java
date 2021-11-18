package com.CS353.cs353project.param.out;


import java.io.Serializable;
import java.util.Date;

/**
 * @author wanggl
 * @version V1.0
 * @Title: ServiceResp
 * @Package com.doone.tool.common.invoke
 * @Description: (标准的服务层返回结果)
 * @date 2017/2/28 16:53
 */
public class ServiceResp<T> implements Serializable {

    /** 响应头 */
    private ServiceHeader head = new ServiceHeader();
    /** 响应内容 */
    private T body;

    public ServiceResp error(){
        this.head.setRespCode(-1);
        this.head.setRespMsg("operation failed");
        this.setBody(null);
        this.head.setRespTime(String.valueOf(new Date().getTime()));
        return this;
    }

    public ServiceResp error(String respMsg){
        this.head.setRespCode(-1);
        this.head.setRespMsg(respMsg);
        this.setBody(null);
        this.head.setRespTime(String.valueOf(new Date().getTime()));
        return this;
    }

    public ServiceResp error(int respCode, String respMsg){
        this.head.setRespCode(respCode);
        this.head.setRespMsg(respMsg);
        this.setBody(null);
        this.head.setRespTime(String.valueOf(new Date().getTime()));
        return this;
    }

    public boolean success(){
        return this.getHead().getRespCode()==0?true:false;
    }

    public boolean hasRecord(){
        return success() && this.body != null ? true : false;
    }

    public ServiceResp success(T body, String respMsg){
        this.head.setRespCode(0);
        this.head.setRespMsg(respMsg);
        this.setBody(body);
        this.head.setRespTime(String.valueOf(new Date().getTime()));
        return this;
    }

    public ServiceResp success(T body){
        this.head.setRespCode(0);
        this.head.setRespMsg("operation success");
        this.setBody(body);
        this.head.setRespTime(String.valueOf(new Date().getTime()));
        return this;
    }

    public ServiceHeader getHead() {
        return head;
    }

    public void setHead(ServiceHeader head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ServiceResp{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
