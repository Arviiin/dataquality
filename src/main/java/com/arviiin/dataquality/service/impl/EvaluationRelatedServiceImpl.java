package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.EvaluationRelatedMapper;
import com.arviiin.dataquality.service.EvaluationRelatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/3/19
 * @Version 1.0.0
 */
@Service
public class EvaluationRelatedServiceImpl implements EvaluationRelatedService {

    @Autowired
    private EvaluationRelatedMapper evaluationRelatedMapper;

    @Override
    public int saveEvaluationInitData(String username, String evaluationName, String evaluationRemark) {
        Timestamp createtime = new Timestamp(System.currentTimeMillis());
        Timestamp updatetime = new Timestamp(System.currentTimeMillis());

        int res = evaluationRelatedMapper.saveEvaluationInitData(username, evaluationName, evaluationRemark,createtime,updatetime);
        return res;
    }

    @Override
    public Map<String, Object> getEvaluationInitData(String username) {
        return evaluationRelatedMapper.getEvaluationInitData(username);
    }
}
