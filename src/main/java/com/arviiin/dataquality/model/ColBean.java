package com.arviiin.dataquality.model;

import java.io.Serializable;

/**
 * Created with IDEA 用于列名展示
 *
 * @Author: jlzhuang
 * @Date: 2019/2/25
 * @Version 1.0.0
 */

public class ColBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String prop;
    private String lable;

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
