package com.arviiin.dataquality.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class WeightBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Double completeness;
    private Double consistency;
    private Double compliance;
    private Double accuracy;
    private Double uniqueness;
    private Double timeliness;
    private Double vulnerability;

    private String type;

    private Timestamp createtime;
    private Timestamp updatetime;

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Double completeness) {
        this.completeness = completeness;
    }

    public Double getConsistency() {
        return consistency;
    }

    public void setConsistency(Double consistency) {
        this.consistency = consistency;
    }

    public Double getCompliance() {
        return compliance;
    }

    public void setCompliance(Double compliance) {
        this.compliance = compliance;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(Double uniqueness) {
        this.uniqueness = uniqueness;
    }

    public Double getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(Double timeliness) {
        this.timeliness = timeliness;
    }

    public Double getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Double vulnerability) {
        this.vulnerability = vulnerability;
    }

    @Override
    public String toString() {
        return "WeightBean{" +
                "id=" + id +
                ", completeness=" + completeness +
                ", consistency=" + consistency +
                ", compliance=" + compliance +
                ", accuracy=" + accuracy +
                ", uniqueness=" + uniqueness +
                ", timeliness=" + timeliness +
                ", vulnerability=" + vulnerability +
                ", type='" + type + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
