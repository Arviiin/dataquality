package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.WeightBean;

public interface WeightService {

    Integer getExpectedTotalRecordAmount(DimensionBean dimensionBean);


    WeightBean getDefaultWeightResult();

    void saveWeightBean(WeightBean weightResult);

    WeightBean getWeightResult();
}
