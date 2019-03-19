package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionResultMapper;
import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import com.arviiin.dataquality.service.DimensionResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DimensionResultServiceImpl implements DimensionResultService {
    private static final Logger logger = LoggerFactory.getLogger(DimensionResultServiceImpl.class);


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

    @Override
    public Map<String, Object> getDimensionResultRatio(DimensionDetailResultBean dimensionDetailResultBean) {
        Map<String, Object> hashMap = new HashMap<>();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultBean.getDataFileCompletenessResult() / dimensionDetailResultBean.getExpectedTotalRecordAmount();
        logger.info( "数据文件完备性"+dataFileCompletenessResult);
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultBean.getDataValueCompletenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataValueCompleteness();
        logger.info("数据值完备性"+ dataValueCompletenessResult);
        hashMap.put("Completeness",(dataFileCompletenessResult+dataValueCompletenessResult)/2f);


        // "数据引用一致性":
        float referentialConsistencyResult = 1 - (float)dimensionDetailResultBean.getReferentialConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfReferentialConsistency();
        logger.info("数据引用一致性"+ (1 - referentialConsistencyResult));
        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultBean.getFormatConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfFormatConsistency();
        logger.info( "数据格式一致性"+formatConsistencyResult);
        hashMap.put("Consistency",(referentialConsistencyResult+formatConsistencyResult)/2f);

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultBean.getDataRecordComplianceResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataRecordCompliance();
        logger.info( "数据记录依从性"+dataRecordComplianceResult);
        hashMap.put("Compliance",dataRecordComplianceResult);

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultBean.getRangeAccuracyResult() / dimensionDetailResultBean.getTotalRecordAmountOfRangeAccuracy();
        logger.info("数据范围准确性"+ rangeAccuracyResult);
        hashMap.put("Accuracy",rangeAccuracyResult);

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultBean.getRecordUniquenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfRecordUniqueness();
        logger.info( "数据记录唯一性"+recordUniquenessResult);
        hashMap.put("Uniqueness",recordUniquenessResult);

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultBean.getTimeBasedTimelinessResult() / dimensionDetailResultBean.getTotalRecordAmountOfTimeBasedTimeliness();
        logger.info("基于时间段的时效性"+ timeBasedTimelinessResult);
        hashMap.put("Timeliness",timeBasedTimelinessResult);

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultBean.getDataNonVulnerabilityResult()/100;
        logger.info("数据非脆弱性"+dataNonVulnerabilityResult+"");
        hashMap.put("Confidentiality",dataNonVulnerabilityResult);

        return hashMap;
    }

    @Override
    public DimensionScore getDimensionScore() {
        return dimensionResultMapper.getDimensionScore();
    }

}
