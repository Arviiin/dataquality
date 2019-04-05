package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionResultMapper;
import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.mapper.VisualizMapper;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.service.VisualizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisualizServiceImpl implements VisualizService {

    @Autowired
    private DimensionResultMapper dimensionResultMapper;

    @Autowired
    private RedisMapper redisMapper;

    @Autowired
    private VisualizMapper visualizMapper;
    /**
     * 获取表的横坐标
     * @return
     */
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        //categories = visualizMapper.getCategories();//没有必要与数据库交互

        /*categories.add("文件完备性");
        categories.add("值完备性");
        categories.add("引用一致性");
        categories.add("格式一致性");
        categories.add("数据依从性");
        categories.add("范围准确性");
        categories.add("记录唯一性");
        categories.add("时效性");
        categories.add("非脆弱性");*/

        categories.add("完备性");
        categories.add("一致性");
        categories.add("依从性");
        categories.add("准确性");
        categories.add("唯一性");
        //categories.add("时效性");
        categories.add("现实性");
        categories.add("保密性");
        return categories;
    }

    /**
     * 从Mysql中获取表的纵坐标的比值，已弃用，因为使用的是DimensionResultBean
     * @return  dimensionResultData
     */
    @Deprecated
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
        float referentialConsistencyResult = (float)dimensionResultData.getReferentialConsistency() / totalRecordAmount;
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

    /**
    * @Author: jlzhuang
    * @Date:
    * @Description: 得到9种性的结果
    * @Version 1.0.0
    */
    @Override
    public List<Float> getDetailDataStatistics() {
        DimensionDetailResultBean dimensionDetailResultData = dimensionResultMapper.getDimensionDetailResultData();
        List<Float> dataStatistics = new ArrayList<>();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultData.getDataFileCompletenessResult() / dimensionDetailResultData.getExpectedTotalRecordAmount();
        dataStatistics.add(dataFileCompletenessResult);
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultData.getDataValueCompletenessResult() / dimensionDetailResultData.getTotalRecordAmountOfDataValueCompleteness();
        dataStatistics.add(dataValueCompletenessResult);

        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionDetailResultData.getReferentialConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfReferentialConsistency();
        dataStatistics.add(referentialConsistencyResult);

        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultData.getFormatConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfFormatConsistency();
        dataStatistics.add(formatConsistencyResult);

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultData.getDataRecordComplianceResult() / dimensionDetailResultData.getTotalRecordAmountOfDataRecordCompliance();
        dataStatistics.add(dataRecordComplianceResult);

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultData.getRangeAccuracyResult() / dimensionDetailResultData.getTotalRecordAmountOfRangeAccuracy();
        dataStatistics.add(rangeAccuracyResult);

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultData.getRecordUniquenessResult() / dimensionDetailResultData.getTotalRecordAmountOfRecordUniqueness();
        dataStatistics.add(recordUniquenessResult);

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultData.getTimeBasedTimelinessResult() / dimensionDetailResultData.getTotalRecordAmountOfTimeBasedTimeliness();
        dataStatistics.add(timeBasedTimelinessResult);

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultData.getDataNonVulnerabilityResult()/100;
        dataStatistics.add(dataNonVulnerabilityResult);

        return dataStatistics;
    }

    /**
     * @Author: jlzhuang
     * @Date:
     * @Description: 得到7种性的结果
     * @Version 1.0.0
     */
    @Override
    public List<String> getSevenDetailDataStatistics() {
        DimensionDetailResultBean dimensionDetailResultData = dimensionResultMapper.getDimensionDetailResultData();
        //格式化保留两位小数
        return formatSevenDetailDataStatistics(dimensionDetailResultData);
    }
    //格式化保留两位小数，再转成float，未能解决显示时，丢失0的问题
    /*public List<Float> getSevenDetailDataStatistics() {
        DimensionDetailResultBean dimensionDetailResultData = dimensionResultMapper.getDimensionDetailResultData();
        DecimalFormat df = new DecimalFormat("0.00");//定义小数保留两位不足用零填充
        List<Float> dataStatistics = new ArrayList<>();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultData.getDataFileCompletenessResult() / dimensionDetailResultData.getExpectedTotalRecordAmount();
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultData.getDataValueCompletenessResult() / dimensionDetailResultData.getTotalRecordAmountOfDataValueCompleteness();
        //完备性
        dataStatistics.add(Float.parseFloat(df.format((dataFileCompletenessResult+dataValueCompletenessResult)/2f)));

        // "数据引用一致性":
        float referentialConsistencyResult =  (float)dimensionDetailResultData.getReferentialConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfReferentialConsistency();
        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultData.getFormatConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfFormatConsistency();
        //一致性
        dataStatistics.add(Float.parseFloat(df.format((referentialConsistencyResult+formatConsistencyResult)/2f)));

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultData.getDataRecordComplianceResult() / dimensionDetailResultData.getTotalRecordAmountOfDataRecordCompliance();
        dataStatistics.add(Float.parseFloat(df.format((dataRecordComplianceResult))));

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultData.getRangeAccuracyResult() / dimensionDetailResultData.getTotalRecordAmountOfRangeAccuracy();
        dataStatistics.add(Float.parseFloat(df.format((rangeAccuracyResult))));

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultData.getRecordUniquenessResult() / dimensionDetailResultData.getTotalRecordAmountOfRecordUniqueness();
        dataStatistics.add(Float.parseFloat(df.format((recordUniquenessResult))));

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultData.getTimeBasedTimelinessResult() / dimensionDetailResultData.getTotalRecordAmountOfTimeBasedTimeliness();
        dataStatistics.add(Float.parseFloat(df.format((timeBasedTimelinessResult))));

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultData.getDataNonVulnerabilityResult()/100;
        dataStatistics.add(Float.parseFloat(df.format((dataNonVulnerabilityResult))));

        return dataStatistics;
    }*/

    /**
    * @Author: jlzhuang
    * @Date:
    * @Description: 九特性的数
    * @Version 1.0.0
    */
    @Override
    public List<Float> getDataStatisticsFromRedis() {
        DimensionDetailResultBean dimensionDetailResultData = redisMapper.getDimensionDetailResultDataFromRedis();
        List<Float> dataStatistics = new ArrayList<>();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultData.getDataFileCompletenessResult() / dimensionDetailResultData.getExpectedTotalRecordAmount();
        dataStatistics.add(dataFileCompletenessResult);
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultData.getDataValueCompletenessResult() / dimensionDetailResultData.getTotalRecordAmountOfDataValueCompleteness();
        dataStatistics.add(dataValueCompletenessResult);

        // "数据引用一致性":
        float referentialConsistencyResult =  (float)dimensionDetailResultData.getReferentialConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfReferentialConsistency();
        dataStatistics.add(referentialConsistencyResult);

        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultData.getFormatConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfFormatConsistency();
        dataStatistics.add(formatConsistencyResult);

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultData.getDataRecordComplianceResult() / dimensionDetailResultData.getTotalRecordAmountOfDataRecordCompliance();
        dataStatistics.add(dataRecordComplianceResult);

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultData.getRangeAccuracyResult() / dimensionDetailResultData.getTotalRecordAmountOfRangeAccuracy();
        dataStatistics.add(rangeAccuracyResult);

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultData.getRecordUniquenessResult() / dimensionDetailResultData.getTotalRecordAmountOfRecordUniqueness();
        dataStatistics.add(recordUniquenessResult);

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultData.getTimeBasedTimelinessResult() / dimensionDetailResultData.getTotalRecordAmountOfTimeBasedTimeliness();
        dataStatistics.add(timeBasedTimelinessResult);

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultData.getDataNonVulnerabilityResult()/100;
        dataStatistics.add(dataNonVulnerabilityResult);

        return dataStatistics;
    }

    /**
     * @Author: jlzhuang
     * @Date:
     * @Description: 7特性的数
     * @Version 1.0.0
     */
    @Override
    public List<String> getSevenDataStatisticsFromRedis() {
        DimensionDetailResultBean dimensionDetailResultData = redisMapper.getDimensionDetailResultDataFromRedis();
        //格式化保留两位小数
        return formatSevenDetailDataStatistics(dimensionDetailResultData);
    }
    //未进行格式化的方法
    /*public List<Float> getSevenDataStatisticsFromRedis() {
        DimensionDetailResultBean dimensionDetailResultData = redisMapper.getDimensionDetailResultDataFromRedis();
        List<Float> dataStatistics = new ArrayList<>();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultData.getDataFileCompletenessResult() / dimensionDetailResultData.getExpectedTotalRecordAmount();
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultData.getDataValueCompletenessResult() / dimensionDetailResultData.getTotalRecordAmountOfDataValueCompleteness();
        //完备性
        dataStatistics.add((dataFileCompletenessResult+dataValueCompletenessResult)/2f);

        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionDetailResultData.getReferentialConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfReferentialConsistency();
        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultData.getFormatConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfFormatConsistency();
        //一致性
        dataStatistics.add((referentialConsistencyResult+formatConsistencyResult)/2f);

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultData.getDataRecordComplianceResult() / dimensionDetailResultData.getTotalRecordAmountOfDataRecordCompliance();
        dataStatistics.add(dataRecordComplianceResult);

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultData.getRangeAccuracyResult() / dimensionDetailResultData.getTotalRecordAmountOfRangeAccuracy();
        dataStatistics.add(rangeAccuracyResult);

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultData.getRecordUniquenessResult() / dimensionDetailResultData.getTotalRecordAmountOfRecordUniqueness();
        dataStatistics.add(recordUniquenessResult);

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultData.getTimeBasedTimelinessResult() / dimensionDetailResultData.getTotalRecordAmountOfTimeBasedTimeliness();
        dataStatistics.add(timeBasedTimelinessResult);

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultData.getDataNonVulnerabilityResult()/100;
        dataStatistics.add(dataNonVulnerabilityResult);

        return dataStatistics;
    }*/


    /**
    * @Author: jlzhuang
    * @Date:
    * @Description: 传入mysql和redis取出的DimensionDetailResultBean  然后进行格式化
    * @Version 1.0.0
    */
    public List<String> formatSevenDetailDataStatistics(DimensionDetailResultBean dimensionDetailResultData) {
        DecimalFormat df = new DecimalFormat("0.00");//定义小数保留两位不足用零填充
        List<String> dataStatistics = new ArrayList<>();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultData.getDataFileCompletenessResult() / dimensionDetailResultData.getExpectedTotalRecordAmount();
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultData.getDataValueCompletenessResult() / dimensionDetailResultData.getTotalRecordAmountOfDataValueCompleteness();
        //完备性
        dataStatistics.add( df.format((dataFileCompletenessResult+dataValueCompletenessResult)/2f));

        // "数据引用一致性":
        float referentialConsistencyResult =  (float)dimensionDetailResultData.getReferentialConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfReferentialConsistency();
        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultData.getFormatConsistencyResult() / dimensionDetailResultData.getTotalRecordAmountOfFormatConsistency();
        //一致性
        dataStatistics.add( df.format((referentialConsistencyResult+formatConsistencyResult)/2f));

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultData.getDataRecordComplianceResult() / dimensionDetailResultData.getTotalRecordAmountOfDataRecordCompliance();
        dataStatistics.add( df.format(dataRecordComplianceResult));

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultData.getRangeAccuracyResult() / dimensionDetailResultData.getTotalRecordAmountOfRangeAccuracy();
        dataStatistics.add( df.format(rangeAccuracyResult));

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultData.getRecordUniquenessResult() / dimensionDetailResultData.getTotalRecordAmountOfRecordUniqueness();
        dataStatistics.add( df.format(recordUniquenessResult));

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultData.getTimeBasedTimelinessResult() / dimensionDetailResultData.getTotalRecordAmountOfTimeBasedTimeliness();
        dataStatistics.add( df.format((timeBasedTimelinessResult)));

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultData.getDataNonVulnerabilityResult()/100;
        dataStatistics.add( df.format(dataNonVulnerabilityResult));

        return dataStatistics;
    }

}
