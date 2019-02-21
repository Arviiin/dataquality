package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.DimensionResultBean;

public interface DimensionService {

    /**
     * 本应该有的记录数
     * @param dimensionBean
     * @return
     */
    Integer getExpectedTotalRecordAmount(DimensionBean dimensionBean);

    Integer getTotalRecordAmount(DimensionBean dimensionBean);

    Integer getDataFileCompletenessResult(DimensionBean dimensionBean);

    Integer getDataValueCompletenessResult(DimensionBean dimensionBean);

    Integer getReferentialConsistencyResult(DimensionBean dimensionBean);

    Integer getFormatConsistencyResult(DimensionBean dimensionBean);

    Integer getDataRecordComplianceResult(DimensionBean dimensionBean);

    Integer getRangeAccuracyResult(DimensionBean dimensionBean);

    Integer getRecordUniquenessResult(DimensionBean dimensionBean);

    Integer getTimeBasedTimelinessResult(DimensionBean dimensionBean);

    Integer getDataNonVulnerabilityResult(DimensionBean dimensionBean);

    void saveDimensionResultData(DimensionResultBean dimensionResultBean);
}
