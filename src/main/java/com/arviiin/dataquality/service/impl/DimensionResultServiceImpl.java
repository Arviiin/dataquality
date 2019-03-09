package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionResultMapper;
import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import com.arviiin.dataquality.service.DimensionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DimensionResultServiceImpl implements DimensionResultService {


    @Autowired
    private DimensionResultMapper dimensionResultMapper;

    @Autowired
    private RedisMapper redisMapper;


    @Override
    public DimensionResultBean getDimensionResultData() {
        return dimensionResultMapper.getDimensionResultData();
    }

    @Override
    public DimensionDetailResultBean getDimensionDetailResultData() {
        return dimensionResultMapper.getDimensionDetailResultData();
    }

    @Override
    public Integer saveDimensionScore(DimensionScore dimensionScore) {
        return dimensionResultMapper.saveDimensionScore(dimensionScore);
    }

    @Override
    public void saveDimensionScoreToRedis(DimensionScore dimensionScore) {
        redisMapper.saveDimensionScoreToRedis(dimensionScore);
    }

}
