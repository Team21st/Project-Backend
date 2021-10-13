package com.CS353.cs353project.param.out;

import java.io.Serializable;

/**
 * @author wanggl
 * @version V1.0
 * @Title: ServiceHeader
 * @Package com.doone.tool.common.invoke
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/2/28 16:53
 */
public class ServiceHeader implements Serializable {

    /** 响应时间 */
    private String respTime;
    /** 响应编码 0成功 -1失败 */
    private Integer respCode;
    /** 响应信息描述 */
    private String respMsg;

    public String getRespTime() {
        return respTime;
    }

    public void setRespTime(String respTime) {
        this.respTime = respTime;
    }

    public Integer getRespCode() {
        return respCode;
    }
    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
