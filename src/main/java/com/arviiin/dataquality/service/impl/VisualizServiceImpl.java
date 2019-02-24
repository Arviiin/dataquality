package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionResultMapper;
import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.service.VisualizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisualizServiceImpl implements VisualizService {

    @Autowired
    private DimensionResultMapper dimensionResultMapper;

    @Autowired
    private RedisMapper redisMapper;
    /**
     * 获取最近七天的日期
     * @return
     */
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");

        /*categories.add("文件完备性");
        categories.add("值完备性");
        categories.add("引用一致性");
        categories.add("格式一致性");
        categories.add("数据依从性");
        categories.add("范围准确性");
        categories.add("记录唯一性");
        categories.add("时效性");
        categories.add("非脆弱性");*/
        return categories;
    }

    /**
     * 获取最近七天的数据
     * @return
     */
    public List<Float> getDataStatistics() {
        DimensionResultBean dimensionResultData = dimensionResultMapper.getDimensionResultData();
        List<Float> dataStatistics = new ArrayList<>();
        int totalRecordAmount = dimensionResultData.getTotalRecordAmount();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionResultData.getDataFileCompleteness() / dimensionResultData.getExpectedTotalRecordAmount();
        dataStatistics.add(dataFileCompletenessResult);
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionResultData.getDataValueCompleteness() / totalRecordAmount;
        dataStatistics.add(dataValueCompletenessResult);

        // "数据引用一致性":
        float referentialConsistencyResult = 1 - (float)dimensionResultData.getReferentialConsistency() / totalRecordAmount;
        dataStatistics.add(referentialConsistencyResult);

        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionResultData.getFormatConsistency() / totalRecordAmount;
        dataStatistics.add(formatConsistencyResult);

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionResultData.getDataRecordCompliance() / totalRecordAmount;
        dataStatistics.add(dataRecordComplianceResult);
        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionResultData.getRangeAccuracy() / totalRecordAmount;
        dataStatistics.add(rangeAccuracyResult);
        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionResultData.getRecordUniqueness() / totalRecordAmount;
        dataStatistics.add(recordUniquenessResult);
        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionResultData.getTimeBasedTimeliness() / totalRecordAmount;
        dataStatistics.add(timeBasedTimelinessResult);
        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionResultData.getDataNonVulnerability()/100;
        dataStatistics.add(dataNonVulnerabilityResult);
        return dataStatistics;
    }

    @Override
    public List<Float> getDataStatisticsFromRedis() {

        return redisMapper.getDataStatisticsFromRedis();
    }

}
