package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.config.RedisConstants;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import com.arviiin.dataquality.util.JsonUtils;
import com.arviiin.dataquality.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "RedisMapper")
public class RedisMapper {

    @Autowired
    private RedisUtil redisUtil;

    @Deprecated
    public void saveDimensionResultDataToRedis(DimensionResultBean dimensionResultBean) {
        redisUtil.set("dimensionResult",JsonUtils.objectToJson(dimensionResultBean), RedisConstants.datebase1);
    }

    /**
     * 现在多采用详细计数
     * @return
     */
    @Deprecated
    public List<Float> getDataStatisticsFromRedis() {
        String dimensionResult = redisUtil.get("dimensionResult", RedisConstants.datebase1);
        DimensionResultBean dimensionResultBean = JsonUtils.jsonToPojo(dimensionResult, DimensionResultBean.class);
        List<Float> dataStatistics = new ArrayList<>();
        int totalRecordAmount = dimensionResultBean.getTotalRecordAmount();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionResultBean.getDataFileCompleteness() / dimensionResultBean.getExpectedTotalRecordAmount();
        dataStatistics.add(dataFileCompletenessResult);
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionResultBean.getDataValueCompleteness() / totalRecordAmount;
        dataStatistics.add(dataValueCompletenessResult);

        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionResultBean.getReferentialConsistency() / totalRecordAmount;
        dataStatistics.add(referentialConsistencyResult);

        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionResultBean.getFormatConsistency() / totalRecordAmount;
        dataStatistics.add(formatConsistencyResult);

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionResultBean.getDataRecordCompliance() / totalRecordAmount;
        dataStatistics.add(dataRecordComplianceResult);
        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionResultBean.getRangeAccuracy() / totalRecordAmount;
        dataStatistics.add(rangeAccuracyResult);
        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionResultBean.getRecordUniqueness() / totalRecordAmount;
        dataStatistics.add(recordUniquenessResult);
        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionResultBean.getTimeBasedTimeliness() / totalRecordAmount;
        dataStatistics.add(timeBasedTimelinessResult);
        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionResultBean.getDataNonVulnerability()/100;
        dataStatistics.add(dataNonVulnerabilityResult);
        return dataStatistics;
    }

    public void saveDimensionDetailResultDataToRedis(DimensionDetailResultBean dimensionDetailResultBean) {
        redisUtil.set("dimensionDetailResult",JsonUtils.objectToJson(dimensionDetailResultBean), RedisConstants.datebase1);
    }

    public DimensionDetailResultBean getDimensionDetailResultDataFromRedis() {
        String dimensionDetailResult = redisUtil.get("dimensionDetailResult", RedisConstants.datebase1);
        return  JsonUtils.jsonToPojo(dimensionDetailResult, DimensionDetailResultBean.class);
    }

    public void saveDimensionScoreToRedis(DimensionScore dimensionScore) {
        redisUtil.set("dimensionScore",JsonUtils.objectToJson(dimensionScore), RedisConstants.datebase1);
    }
}
