package com.arviiin.dataquality.model;

import java.io.Serializable;

public class JsonResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status = null;
    private Object result = null;//这里的result也当做msg来用
    /*private String status;
    private String msg;*/

    public JsonResult() {
    }

    public JsonResult(String status, Object result) {
        this.status = status;
        this.result = result;
    }

    public JsonResult status(String status) {
        this.status = status;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
