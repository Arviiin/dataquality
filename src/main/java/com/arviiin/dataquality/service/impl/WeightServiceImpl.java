package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.WeightMapper;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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

    /**
    * @Author: jlzhuang
    * @Date:
    * @Description: 格式化保留三位小数，不足用填充0
    * @Version 1.0.0
    */
    @Override
    public Map<String, String> formatWeightResultBean(WeightBean weightResultBean) {
        DecimalFormat df = new DecimalFormat("0.000");//定义小数保留三位不足用零填充
        Map<String, String> dataMap = new HashMap<>();
        //completeness;
        //consistency;
        //compliance;
        //accuracy;
        //uniqueness;
        //timeliness;
        //vulnerability;
        dataMap.put("completeness",df.format(weightResultBean.getCompleteness()));
        dataMap.put("consistency",df.format(weightResultBean.getConsistency()));
        dataMap.put("compliance",df.format(weightResultBean.getCompliance()));
        dataMap.put("accuracy",df.format(weightResultBean.getAccuracy()));
        dataMap.put("uniqueness",df.format(weightResultBean.getUniqueness()));
        dataMap.put("timeliness",df.format(weightResultBean.getTimeliness()));
        dataMap.put("vulnerability",df.format(weightResultBean.getVulnerability()));
        return dataMap;
    }

}
