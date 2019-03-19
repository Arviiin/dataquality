package com.arviiin.dataquality.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DimensionScore implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

   //数据文件完备性//:
    private Double dataFileCompletenessScore;
   //数据值完备性//:
    private Double dataValueCompletenessScore;
    //数据完备性//:
    private Double dataCompletenessScore;


   //数据引用一致性//:
    private Double referentialConsistencyScore;
   //数据格式一致性//:
    private Double formatConsistencyScore;
    //数据一致性//:
    private Double dataConsistencyScore;


   //数据记录依从性//:
    private Double dataRecordComplianceScore;
   //数据范围准确性//:
   private Double rangeAccuracyScore;
   //数据记录唯一性//:
    private Double recordUniquenessScore;
   //基于时间段的时效性//:
    private Double timeBasedTimelinessScore;
   //数据非脆弱性//:
    private Double dataNonVulnerabilityScore;

    //总分数//:
    private Double totalDataQualityScore;

    private Timestamp createtime;
    private Timestamp updatetime;

    public DimensionScore(Double dataFileCompletenessScore, Double dataValueCompletenessScore, Double dataCompletenessScore, Double referentialConsistencyScore, Double formatConsistencyScore, Double dataConsistencyScore, Double dataRecordComplianceScore, Double rangeAccuracyScore, Double recordUniquenessScore, Double timeBasedTimelinessScore, Double dataNonVulnerabilityScore) {
        this.dataFileCompletenessScore = dataFileCompletenessScore;
        this.dataValueCompletenessScore = dataValueCompletenessScore;
        this.dataCompletenessScore = dataCompletenessScore;
        this.referentialConsistencyScore = referentialConsistencyScore;
        this.formatConsistencyScore = formatConsistencyScore;
        this.dataConsistencyScore = dataConsistencyScore;
        this.dataRecordComplianceScore = dataRecordComplianceScore;
        this.rangeAccuracyScore = rangeAccuracyScore;
        this.recordUniquenessScore = recordUniquenessScore;
        this.timeBasedTimelinessScore = timeBasedTimelinessScore;
        this.dataNonVulnerabilityScore = dataNonVulnerabilityScore;
    }

    public DimensionScore(Long id, Double dataFileCompletenessScore, Double dataValueCompletenessScore, Double dataCompletenessScore, Double referentialConsistencyScore, Double formatConsistencyScore, Double dataConsistencyScore, Double dataRecordComplianceScore, Double rangeAccuracyScore, Double recordUniquenessScore, Double timeBasedTimelinessScore, Double dataNonVulnerabilityScore, Double totalDataQualityScore, Timestamp createtime, Timestamp updatetime) {
        this.dataFileCompletenessScore = dataFileCompletenessScore;
        this.dataValueCompletenessScore = dataValueCompletenessScore;
        this.dataCompletenessScore = dataCompletenessScore;
        this.referentialConsistencyScore = referentialConsistencyScore;
        this.formatConsistencyScore = formatConsistencyScore;
        this.dataConsistencyScore = dataConsistencyScore;
        this.dataRecordComplianceScore = dataRecordComplianceScore;
        this.rangeAccuracyScore = rangeAccuracyScore;
        this.recordUniquenessScore = recordUniquenessScore;
        this.timeBasedTimelinessScore = timeBasedTimelinessScore;
        this.dataNonVulnerabilityScore = dataNonVulnerabilityScore;
        this.totalDataQualityScore = totalDataQualityScore;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDataFileCompletenessScore() {
        return dataFileCompletenessScore;
    }

    public void setDataFileCompletenessScore(Double dataFileCompletenessScore) {
        this.dataFileCompletenessScore = dataFileCompletenessScore;
    }

    public Double getDataValueCompletenessScore() {
        return dataValueCompletenessScore;
    }

    public void setDataValueCompletenessScore(Double dataValueCompletenessScore) {
        this.dataValueCompletenessScore = dataValueCompletenessScore;
    }

    public Double getDataCompletenessScore() {
        return dataCompletenessScore;
    }

    public void setDataCompletenessScore(Double dataCompletenessScore) {
        this.dataCompletenessScore = dataCompletenessScore;
    }

    public Double getReferentialConsistencyScore() {
        return referentialConsistencyScore;
    }

    public void setReferentialConsistencyScore(Double referentialConsistencyScore) {
        this.referentialConsistencyScore = referentialConsistencyScore;
    }

    public Double getFormatConsistencyScore() {
        return formatConsistencyScore;
    }

    public void setFormatConsistencyScore(Double formatConsistencyScore) {
        this.formatConsistencyScore = formatConsistencyScore;
    }

    public Double getDataConsistencyScore() {
        return dataConsistencyScore;
    }

    public void setDataConsistencyScore(Double dataConsistencyScore) {
        this.dataConsistencyScore = dataConsistencyScore;
    }

    public Double getDataRecordComplianceScore() {
        return dataRecordComplianceScore;
    }

    public void setDataRecordComplianceScore(Double dataRecordComplianceScore) {
        this.dataRecordComplianceScore = dataRecordComplianceScore;
    }

    public Double getRangeAccuracyScore() {
        return rangeAccuracyScore;
    }

    public void setRangeAccuracyScore(Double rangeAccuracyScore) {
        this.rangeAccuracyScore = rangeAccuracyScore;
    }

    public Double getRecordUniquenessScore() {
        return recordUniquenessScore;
    }

    public void setRecordUniquenessScore(Double recordUniquenessScore) {
        this.recordUniquenessScore = recordUniquenessScore;
    }

    public Double getTimeBasedTimelinessScore() {
        return timeBasedTimelinessScore;
    }

    public void setTimeBasedTimelinessScore(Double timeBasedTimelinessScore) {
        this.timeBasedTimelinessScore = timeBasedTimelinessScore;
    }

    public Double getDataNonVulnerabilityScore() {
        return dataNonVulnerabilityScore;
    }

    public void setDataNonVulnerabilityScore(Double dataNonVulnerabilityScore) {
        this.dataNonVulnerabilityScore = dataNonVulnerabilityScore;
    }

    public Double getTotalDataQualityScore() {
        return totalDataQualityScore;
    }

    public void setTotalDataQualityScore(Double totalDataQualityScore) {
        this.totalDataQualityScore = totalDataQualityScore;
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
}
