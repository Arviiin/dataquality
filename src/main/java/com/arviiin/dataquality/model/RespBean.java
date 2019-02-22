package com.arviiin.dataquality.model;

import java.io.Serializable;

/**
 * Created by sang on 2017/12/17.
 */
public class RespBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String status;
    private String msg;

    public RespBean() {
    }

    public RespBean(String status, String msg) {

        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
