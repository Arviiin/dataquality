package com.arviiin.dataquality.model;

import java.sql.Timestamp;

public class DimensionResultBean {

    private Integer id;

    private Integer totalRecordAmount ;
   //数据文件完备性//:
    private Integer expectedTotalRecordAmount;

    private Integer dataFileCompleteness;
   //数据值完备性//:

    private Integer dataValueCompleteness;
   //数据引用一致性//:

    private Integer referentialConsistency;

   //数据格式一致性//:

    private Integer formatConsistency;

   //数据记录依从性//:

    private Integer dataRecordCompliance;
   //数据范围准确性//:
   private Integer rangeAccuracy;
   //数据记录唯一性//:

    private Integer recordUniqueness;
   //基于时间段的时效性//:

    private Integer timeBasedTimeliness;
   //数据非脆弱性//:

    private Integer dataNonVulnerability;

    private Timestamp createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalRecordAmount() {
        return totalRecordAmount;
    }

    public void setTotalRecordAmount(Integer totalRecordAmount) {
        this.totalRecordAmount = totalRecordAmount;
    }

    public Integer getExpectedTotalRecordAmount() {
        return expectedTotalRecordAmount;
    }

    public void setExpectedTotalRecordAmount(Integer expectedTotalRecordAmount) {
        this.expectedTotalRecordAmount = expectedTotalRecordAmount;
    }

    public Integer getDataFileCompleteness() {
        return dataFileCompleteness;
    }

    public void setDataFileCompleteness(Integer dataFileCompleteness) {
        this.dataFileCompleteness = dataFileCompleteness;
    }

    public Integer getDataValueCompleteness() {
        return dataValueCompleteness;
    }

    public void setDataValueCompleteness(Integer dataValueCompleteness) {
        this.dataValueCompleteness = dataValueCompleteness;
    }

    public Integer getReferentialConsistency() {
        return referentialConsistency;
    }

    public void setReferentialConsistency(Integer referentialConsistency) {
        this.referentialConsistency = referentialConsistency;
    }

    public Integer getFormatConsistency() {
        return formatConsistency;
    }

    public void setFormatConsistency(Integer formatConsistency) {
        this.formatConsistency = formatConsistency;
    }

    public Integer getDataRecordCompliance() {
        return dataRecordCompliance;
    }

    public void setDataRecordCompliance(Integer dataRecordCompliance) {
        this.dataRecordCompliance = dataRecordCompliance;
    }

    public Integer getRangeAccuracy() {
        return rangeAccuracy;
    }

    public void setRangeAccuracy(Integer rangeAccuracy) {
        this.rangeAccuracy = rangeAccuracy;
    }

    public Integer getRecordUniqueness() {
        return recordUniqueness;
    }

    public void setRecordUniqueness(Integer recordUniqueness) {
        this.recordUniqueness = recordUniqueness;
    }

    public Integer getTimeBasedTimeliness() {
        return timeBasedTimeliness;
    }

    public void setTimeBasedTimeliness(Integer timeBasedTimeliness) {
        this.timeBasedTimeliness = timeBasedTimeliness;
    }

    public Integer getDataNonVulnerability() {
        return dataNonVulnerability;
    }

    public void setDataNonVulnerability(Integer dataNonVulnerability) {
        this.dataNonVulnerability = dataNonVulnerability;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "DimensionResultBean{" +
                "id=" + id +
                ", totalRecordAmount=" + totalRecordAmount +
                ", expectedTotalRecordAmount=" + expectedTotalRecordAmount +
                ", dataFileCompleteness=" + dataFileCompleteness +
                ", dataValueCompleteness=" + dataValueCompleteness +
                ", referentialConsistency=" + referentialConsistency +
                ", formatConsistency=" + formatConsistency +
                ", dataRecordCompliance=" + dataRecordCompliance +
                ", rangeAccuracy=" + rangeAccuracy +
                ", recordUniqueness=" + recordUniqueness +
                ", timeBasedTimeliness=" + timeBasedTimeliness +
                ", dataNonVulnerability=" + dataNonVulnerability +
                ", createtime=" + createtime +
                '}';
    }
}
