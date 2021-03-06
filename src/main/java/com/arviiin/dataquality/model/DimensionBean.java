package com.arviiin.dataquality.model;

import java.io.Serializable;

/**
 * dimensionname是指标名    tablename是表名，columnname是字段名，rule是规则约束
 */
public class DimensionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dimensionname;
    private String tablename;
    private String columnname;
    private String rule;

    public String getDimensionname() {
        return dimensionname;
    }

    public void setDimensionname(String dimensionname) {
        this.dimensionname = dimensionname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "DimensionBean{" +
                "dimensionname='" + dimensionname + '\'' +
                ", tablename='" + tablename + '\'' +
                ", columnname='" + columnname + '\'' +
                ", rule='" + rule + '\'' +
                '}';
    }

}
