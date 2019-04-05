package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.WeightBean;

import java.util.Map;

public interface WeightService {

    WeightBean getDefaultWeightResult();

    void saveWeightBean(WeightBean weightResult);

    WeightBean getWeightResult();

    Map<String,String> formatWeightResultBean(WeightBean weightResultBean);
}
