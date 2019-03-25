package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.WeightBean;

public interface WeightService {

    WeightBean getDefaultWeightResult();

    void saveWeightBean(WeightBean weightResult);

    WeightBean getWeightResult();
}
