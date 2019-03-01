package com.arviiin.dataquality.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DimensionDetailResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    //数据文件完备性//:
    private Integer expectedTotalRecordAmount;
    private Integer dataFileCompletenessResult;

   //数据值完备性//:
    private  Integer totalRecordAmountOfDataValueCompleteness;
    private Integer dataValueCompletenessResult;

   //数据引用一致性//:
    private Integer totalRecordAmountOfReferentialConsistency;
    private Integer referentialConsistencyResult;

   //数据格式一致性//:
    private Integer totalRecordAmountOfFormatConsistency;
    private Integer formatConsistencyResult;

   //数据记录依从性//:
    private Integer totalRecordAmountOfDataRecordCompliance;
    private Integer dataRecordComplianceResult;

   //数据范围准确性//:
   private Integer totalRecordAmountOfRangeAccuracy;
   private Integer rangeAccuracyResult;

   //数据记录唯一性//:
    private Integer totalRecordAmountOfRecordUniqueness;
    private Integer recordUniquenessResult;

   //基于时间段的时效性//:
    private Integer totalRecordAmountOfTimeBasedTimeliness;
    private Integer timeBasedTimelinessResult;

   //数据非脆弱性//:
    private Integer dataNonVulnerabilityResult;

    private Timestamp createtime;
    private Timestamp updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExpectedTotalRecordAmount() {
        return expectedTotalRecordAmount;
    }

    public void setExpectedTotalRecordAmount(Integer expectedTotalRecordAmount) {
        this.expectedTotalRecordAmount = expectedTotalRecordAmount;
    }

    public Integer getDataFileCompletenessResult() {
        return dataFileCompletenessResult;
    }

    public void setDataFileCompletenessResult(Integer dataFileCompletenessResult) {
        this.dataFileCompletenessResult = dataFileCompletenessResult;
    }

    public Integer getTotalRecordAmountOfDataValueCompleteness() {
        return totalRecordAmountOfDataValueCompleteness;
    }

    public void setTotalRecordAmountOfDataValueCompleteness(Integer totalRecordAmountOfDataValueCompleteness) {
        this.totalRecordAmountOfDataValueCompleteness = totalRecordAmountOfDataValueCompleteness;
    }

    public Integer getDataValueCompletenessResult() {
        return dataValueCompletenessResult;
    }

    public void setDataValueCompletenessResult(Integer dataValueCompletenessResult) {
        this.dataValueCompletenessResult = dataValueCompletenessResult;
    }

    public Integer getTotalRecordAmountOfReferentialConsistency() {
        return totalRecordAmountOfReferentialConsistency;
    }

    public void setTotalRecordAmountOfReferentialConsistency(Integer totalRecordAmountOfReferentialConsistency) {
        this.totalRecordAmountOfReferentialConsistency = totalRecordAmountOfReferentialConsistency;
    }

    public Integer getReferentialConsistencyResult() {
        return referentialConsistencyResult;
    }

    public void setReferentialConsistencyResult(Integer referentialConsistencyResult) {
        this.referentialConsistencyResult = referentialConsistencyResult;
    }

    public Integer getTotalRecordAmountOfFormatConsistency() {
        return totalRecordAmountOfFormatConsistency;
    }

    public void setTotalRecordAmountOfFormatConsistency(Integer totalRecordAmountOfFormatConsistency) {
        this.totalRecordAmountOfFormatConsistency = totalRecordAmountOfFormatConsistency;
    }

    public Integer getFormatConsistencyResult() {
        return formatConsistencyResult;
    }

    public void setFormatConsistencyResult(Integer formatConsistencyResult) {
        this.formatConsistencyResult = formatConsistencyResult;
    }

    public Integer getTotalRecordAmountOfDataRecordCompliance() {
        return totalRecordAmountOfDataRecordCompliance;
    }

    public void setTotalRecordAmountOfDataRecordCompliance(Integer totalRecordAmountOfDataRecordCompliance) {
        this.totalRecordAmountOfDataRecordCompliance = totalRecordAmountOfDataRecordCompliance;
    }

    public Integer getDataRecordComplianceResult() {
        return dataRecordComplianceResult;
    }

    public void setDataRecordComplianceResult(Integer dataRecordComplianceResult) {
        this.dataRecordComplianceResult = dataRecordComplianceResult;
    }

    public Integer getTotalRecordAmountOfRangeAccuracy() {
        return totalRecordAmountOfRangeAccuracy;
    }

    public void setTotalRecordAmountOfRangeAccuracy(Integer totalRecordAmountOfRangeAccuracy) {
        this.totalRecordAmountOfRangeAccuracy = totalRecordAmountOfRangeAccuracy;
    }

    public Integer getRangeAccuracyResult() {
        return rangeAccuracyResult;
    }

    public void setRangeAccuracyResult(Integer rangeAccuracyResult) {
        this.rangeAccuracyResult = rangeAccuracyResult;
    }

    public Integer getTotalRecordAmountOfRecordUniqueness() {
        return totalRecordAmountOfRecordUniqueness;
    }

    public void setTotalRecordAmountOfRecordUniqueness(Integer totalRecordAmountOfRecordUniqueness) {
        this.totalRecordAmountOfRecordUniqueness = totalRecordAmountOfRecordUniqueness;
    }

    public Integer getRecordUniquenessResult() {
        return recordUniquenessResult;
    }

    public void setRecordUniquenessResult(Integer recordUniquenessResult) {
        this.recordUniquenessResult = recordUniquenessResult;
    }

    public Integer getTotalRecordAmountOfTimeBasedTimeliness() {
        return totalRecordAmountOfTimeBasedTimeliness;
    }

    public void setTotalRecordAmountOfTimeBasedTimeliness(Integer totalRecordAmountOfTimeBasedTimeliness) {
        this.totalRecordAmountOfTimeBasedTimeliness = totalRecordAmountOfTimeBasedTimeliness;
    }

    public Integer getTimeBasedTimelinessResult() {
        return timeBasedTimelinessResult;
    }

    public void setTimeBasedTimelinessResult(Integer timeBasedTimelinessResult) {
        this.timeBasedTimelinessResult = timeBasedTimelinessResult;
    }

    public Integer getDataNonVulnerabilityResult() {
        return dataNonVulnerabilityResult;
    }

    public void setDataNonVulnerabilityResult(Integer dataNonVulnerabilityResult) {
        this.dataNonVulnerabilityResult = dataNonVulnerabilityResult;
    }

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

    @Override
    public String toString() {
        return "DimensionDetailResultBean{" +
                "id=" + id +
                ", expectedTotalRecordAmount=" + expectedTotalRecordAmount +
                ", dataFileCompletenessResult=" + dataFileCompletenessResult +
                ", totalRecordAmountOfDataValueCompleteness=" + totalRecordAmountOfDataValueCompleteness +
                ", dataValueCompletenessResult=" + dataValueCompletenessResult +
                ", totalRecordAmountOfReferentialConsistency=" + totalRecordAmountOfReferentialConsistency +
                ", referentialConsistencyResult=" + referentialConsistencyResult +
                ", totalRecordAmountOfFormatConsistency=" + totalRecordAmountOfFormatConsistency +
                ", formatConsistencyResult=" + formatConsistencyResult +
                ", totalRecordAmountOfDataRecordCompliance=" + totalRecordAmountOfDataRecordCompliance +
                ", dataRecordComplianceResult=" + dataRecordComplianceResult +
                ", totalRecordAmountOfRangeAccuracy=" + totalRecordAmountOfRangeAccuracy +
                ", rangeAccuracyResult=" + rangeAccuracyResult +
                ", totalRecordAmountOfRecordUniqueness=" + totalRecordAmountOfRecordUniqueness +
                ", recordUniquenessResult=" + recordUniquenessResult +
                ", totalRecordAmountOfTimeBasedTimeliness=" + totalRecordAmountOfTimeBasedTimeliness +
                ", timeBasedTimelinessResult=" + timeBasedTimelinessResult +
                ", dataNonVulnerabilityResult=" + dataNonVulnerabilityResult +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
