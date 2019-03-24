package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionMapper;
import com.arviiin.dataquality.mapper.WeightMapper;
import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeightServiceImpl implements WeightService {

    @Autowired
    private WeightMapper weightMapper;

    @Autowired
    private DimensionMapper dimensionMapper;

    @Override
    public Integer getExpectedTotalRecordAmount(DimensionBean dimensionBean) {
        return dimensionMapper.getExpectedTotalRecordAmount(dimensionBean);
    }

    @Override
    public WeightBean getDefaultWeightResult() {
        return weightMapper.getDefaultWeightResult();
    }

    @Override
    public void saveWeightBean(WeightBean weightResult) {
        weightMapper.saveWeightBean(weightResult);
    }

    @Override
    public WeightBean getWeightResult() {
        return weightMapper.getWeightResult();
    }

}
