package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;

import java.util.Map;

public interface DimensionResultService {

    DimensionResultBean getDimensionResultData();

    DimensionDetailResultBean getDimensionDetailResultData();

    Integer saveDimensionScore(DimensionScore dimensionScore);

    void saveDimensionScoreToRedis(DimensionScore dimensionScore);

    Map<String,Object> getDimensionResultRatio(DimensionDetailResultBean dimensionDetailResultBean);

    Map<String,Object> getDimensionResultRatioScore(DimensionDetailResultBean dimensionDetailResultBean);

    DimensionScore getDimensionScore();

    Map<String,String> formatDimensionScore(DimensionScore dimensionScore);

    String getDimensionResultMinRatio(DimensionDetailResultBean dimensionDetailResultBean);

    String getDimensionEvaluationLevel(DimensionScore dimensionScore);

    /**
     * 把结果中的保密性分数进行转变
     * @param dimensionDetailResult
     * @return
     */
    DimensionDetailResultBean transformDimensionDetailResult(DimensionDetailResultBean dimensionDetailResult);
}
