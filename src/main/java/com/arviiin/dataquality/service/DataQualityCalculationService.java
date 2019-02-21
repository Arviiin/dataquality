package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.WeightBean;

public interface DataQualityCalculationService {


    Double dimensionWeightCalculation(DimensionResultBean dimensionResultData, WeightBean receiveWeightResult);
}
