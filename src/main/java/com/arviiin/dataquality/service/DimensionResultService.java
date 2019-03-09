package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;

public interface DimensionResultService {

    DimensionResultBean getDimensionResultData();

    DimensionDetailResultBean getDimensionDetailResultData();

    Integer saveDimensionScore(DimensionScore dimensionScore);

    void saveDimensionScoreToRedis(DimensionScore dimensionScore);
}
