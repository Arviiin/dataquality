package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.WeightMapper;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeightServiceImpl implements WeightService {

    @Autowired
    private WeightMapper weightMapper;

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
