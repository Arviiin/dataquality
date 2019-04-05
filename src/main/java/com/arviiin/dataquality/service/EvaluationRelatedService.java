package com.arviiin.dataquality.service;

import java.util.Map;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/3/19
 * @Version 1.0.0
 */
public interface EvaluationRelatedService {

    int saveEvaluationInitData(String username, String evaluationName, String evaluationRemark);

    Map<String,Object> getEvaluationInitData(String username);

    Map<String,Object> getLatestEvaluationInitData();

    int saveEvaluationInitData(String username, String email, String evaluationName, String evaluationRemark);
}
