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

import java.text.DecimalFormat;
import java.util.*;

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
        DecimalFormat df = new DecimalFormat("0.00%");//定义小数转百分数
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultBean.getDataFileCompletenessResult() / dimensionDetailResultBean.getExpectedTotalRecordAmount();
        logger.info( "数据文件完备性"+dataFileCompletenessResult);
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultBean.getDataValueCompletenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataValueCompleteness();
        logger.info("数据值完备性"+ dataValueCompletenessResult);
        //hashMap.put("Completeness",new DecimalFormat("0.00").format((dataFileCompletenessResult+dataValueCompletenessResult)/2f));
        hashMap.put("Completeness",df.format((dataFileCompletenessResult+dataValueCompletenessResult)/2));


        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionDetailResultBean.getReferentialConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfReferentialConsistency();
        logger.info("数据引用一致性"+ referentialConsistencyResult);
        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultBean.getFormatConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfFormatConsistency();
        logger.info( "数据格式一致性"+formatConsistencyResult);
        hashMap.put("Consistency",df.format((referentialConsistencyResult+formatConsistencyResult)/2));

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultBean.getDataRecordComplianceResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataRecordCompliance();
        logger.info( "数据记录依从性"+dataRecordComplianceResult);
        hashMap.put("Compliance",df.format(dataRecordComplianceResult));

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultBean.getRangeAccuracyResult() / dimensionDetailResultBean.getTotalRecordAmountOfRangeAccuracy();
        logger.info("数据范围准确性"+ rangeAccuracyResult);
        hashMap.put("Accuracy",df.format(rangeAccuracyResult));

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultBean.getRecordUniquenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfRecordUniqueness();
        logger.info( "数据记录唯一性"+recordUniquenessResult);
        hashMap.put("Uniqueness",df.format(recordUniquenessResult));

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultBean.getTimeBasedTimelinessResult() / dimensionDetailResultBean.getTotalRecordAmountOfTimeBasedTimeliness();
        logger.info("基于时间段的时效性"+ timeBasedTimelinessResult);
        hashMap.put("Timeliness",df.format(timeBasedTimelinessResult));

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultBean.getDataNonVulnerabilityResult()/100;
        logger.info("数据非脆弱性"+dataNonVulnerabilityResult+"");
        hashMap.put("Confidentiality",df.format(dataNonVulnerabilityResult));

        return hashMap;
    }

    /**
     * 计算每个指标的初始比值得分,保留两位小数，除了权重3位小数，其他最多保留两位小数
     * @param dimensionDetailResultBean
     * @return
     */
    @Override
    public Map<String, Object> getDimensionResultRatioScore(DimensionDetailResultBean dimensionDetailResultBean) {
        Map<String, Object> hashMap = new HashMap<>();
        DecimalFormat df = new DecimalFormat("0.00");//定义小数保留两位不足用零填充
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultBean.getDataFileCompletenessResult() / dimensionDetailResultBean.getExpectedTotalRecordAmount();
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultBean.getDataValueCompletenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataValueCompleteness();
        hashMap.put("Completeness",df.format((dataFileCompletenessResult+dataValueCompletenessResult)/2*100));

        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionDetailResultBean.getReferentialConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfReferentialConsistency();
        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultBean.getFormatConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfFormatConsistency();
        hashMap.put("Consistency",df.format((referentialConsistencyResult+formatConsistencyResult)/2*100));

        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultBean.getDataRecordComplianceResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataRecordCompliance();
        hashMap.put("Compliance",df.format(dataRecordComplianceResult*100));

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultBean.getRangeAccuracyResult() / dimensionDetailResultBean.getTotalRecordAmountOfRangeAccuracy();
        hashMap.put("Accuracy",df.format(rangeAccuracyResult*100));

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultBean.getRecordUniquenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfRecordUniqueness();
        hashMap.put("Uniqueness",df.format(recordUniquenessResult*100));

        // "基于时间段的时效性"现实性:
        float timeBasedTimelinessResult = (float)dimensionDetailResultBean.getTimeBasedTimelinessResult() / dimensionDetailResultBean.getTotalRecordAmountOfTimeBasedTimeliness();
        hashMap.put("Timeliness",df.format(timeBasedTimelinessResult*100));

        // "数据非脆弱性"保密性:
        float dataNonVulnerabilityResult = (float)dimensionDetailResultBean.getDataNonVulnerabilityResult()/100;
        hashMap.put("Confidentiality",df.format(dataNonVulnerabilityResult*100));

        return hashMap;
    }

    /**
    * @Author: jlzhuang
    * @Date:
    * @Description: 
    * @Version 1.0.0
    */
    @Override
    public DimensionScore getDimensionScore() {
        return dimensionResultMapper.getDimensionScore();
    }

    /**
    * @Author: jlzhuang
    * @Date:
    * @Description: 保留两位小数
    * @Version 1.0.0
    */
    @Override
    public Map<String,String> formatDimensionScore(DimensionScore dimensionScore) {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, String> dataMap = new HashMap<>();

//        return new DimensionScore(dataFileCompletenessScore,dataValueCompletenessScore,dataCompletenessScore,
//                referentialConsistencyScore,formatConsistencyScore,dataConsistencyScore,
//                dataRecordComplianceScore,rangeAccuracyScore,recordUniquenessScore,
//                timeBasedTimelinessScore,dataNonVulnerabilityScore);
        dataMap.put("dataFileCompletenessScore",df.format(dimensionScore.getDataFileCompletenessScore()));
        dataMap.put("dataValueCompletenessScore",df.format(dimensionScore.getDataValueCompletenessScore()));
        dataMap.put("dataCompletenessScore",df.format(dimensionScore.getDataCompletenessScore()));
;
        dataMap.put("referentialConsistencyScore",df.format(dimensionScore.getReferentialConsistencyScore()));
        dataMap.put("formatConsistencyScore",df.format(dimensionScore.getFormatConsistencyScore()));
        dataMap.put("dataConsistencyScore",df.format(dimensionScore.getDataConsistencyScore()));
;
        dataMap.put("dataRecordComplianceScore",df.format(dimensionScore.getDataRecordComplianceScore()));
        dataMap.put("rangeAccuracyScore",df.format(dimensionScore.getRangeAccuracyScore()));
        dataMap.put("recordUniquenessScore",df.format(dimensionScore.getRecordUniquenessScore()));
        dataMap.put("timeBasedTimelinessScore",df.format(dimensionScore.getTimeBasedTimelinessScore()));
        dataMap.put("dataNonVulnerabilityScore",df.format(dimensionScore.getDataNonVulnerabilityScore()));
;
        dataMap.put("totalDataQualityScore",df.format(dimensionScore.getTotalDataQualityScore()));

        return dataMap;
    }

    /**
     * 拿到所有指标中合格率最小的
     * @param dimensionDetailResultBean
     * @return
     */
    @Override
    public String getDimensionResultMinRatio(DimensionDetailResultBean dimensionDetailResultBean) {

        Map<String, Float> hashMap = new HashMap<>();
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultBean.getDataFileCompletenessResult() / dimensionDetailResultBean.getExpectedTotalRecordAmount();
        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultBean.getDataValueCompletenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataValueCompleteness();
        hashMap.put("完备性",(dataFileCompletenessResult+dataValueCompletenessResult)/2);


        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionDetailResultBean.getReferentialConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfReferentialConsistency();
        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultBean.getFormatConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfFormatConsistency();
        hashMap.put("一致性",(referentialConsistencyResult+formatConsistencyResult)/2);


        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultBean.getDataRecordComplianceResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataRecordCompliance();
        hashMap.put("依从性",dataRecordComplianceResult);

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultBean.getRangeAccuracyResult() / dimensionDetailResultBean.getTotalRecordAmountOfRangeAccuracy();
        hashMap.put("准确性",rangeAccuracyResult);

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultBean.getRecordUniquenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfRecordUniqueness();
        hashMap.put("唯一性",recordUniquenessResult);

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultBean.getTimeBasedTimelinessResult() / dimensionDetailResultBean.getTotalRecordAmountOfTimeBasedTimeliness();
        hashMap.put("现实性",timeBasedTimelinessResult);

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultBean.getDataNonVulnerabilityResult()/100;
        hashMap.put("保密性",dataNonVulnerabilityResult);


        //1、按顺序保存map中的元素，使用LinkedList类型
        List<Map.Entry<String, Float>> keyList = new LinkedList<>(hashMap.entrySet());
        //2、按照自定义的规则排序  从小到大
        Collections.sort(keyList, new Comparator<Map.Entry<String, Float>>() {
            @Override
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                if(o2.getValue().compareTo(o1.getValue())>0){
                    return -1;
                }else if(o2.getValue().compareTo(o1.getValue())<0){
                    return 1;
                }  else {
                    return 0;
                }
            }

        });
        //3、将LinkedList按照排序好的结果，取第一个最小的
        Map.Entry<String, Float> stringFloatEntry = keyList.get(0);
        DecimalFormat df = new DecimalFormat("0.00%");//定义小数转百分数
        return stringFloatEntry.getKey()+"，其值为"+df.format(stringFloatEntry.getValue());
    }

    /**
    * @Author: jlzhuang
    * @Date:
    * @Description: 评价等级分为优秀、良好、合格、不合格四种   优秀>=90    良好： 89-75   合格74-60   不合格 <=59
    * @Version 1.0.0
    */
    @Override
    public String getDimensionEvaluationLevel(DimensionScore dimensionScore) {
        Double totalDataQualityScore = dimensionScore.getTotalDataQualityScore();
        if (totalDataQualityScore >= 90.000d){
            return "优秀";
        }else if(totalDataQualityScore >= 75.000d && totalDataQualityScore <= 89.000d){
            return "良好";
        }else if (totalDataQualityScore >= 60.000d && totalDataQualityScore <= 74.000d){
            return "合格";
        }
        return "不合格";
    }

}
