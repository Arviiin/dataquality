package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.DataQualityCalculationService;
import com.arviiin.dataquality.service.DimensionResultService;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DataQualityCalculationServiceImpl implements DataQualityCalculationService {

    private static Logger logger = LoggerFactory.getLogger(DataQualityCalculationServiceImpl.class);

    @Autowired
    private DimensionResultService dimensionResultService;

    @Override
    public Double dimensionWeightCalculation(DimensionResultBean dimensionResultData, WeightBean weightBean) {

        int totalRecordAmount = dimensionResultData.getTotalRecordAmount();

        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionResultData.getDataFileCompleteness() / dimensionResultData.getExpectedTotalRecordAmount();
        logger.info( "数据文件完备性"+dataFileCompletenessResult);

        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionResultData.getDataValueCompleteness() / totalRecordAmount;
        logger.info("数据值完备性"+ dataValueCompletenessResult);


        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionResultData.getReferentialConsistency() / totalRecordAmount;
        logger.info("数据引用一致性"+ referentialConsistencyResult);


        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionResultData.getFormatConsistency() / totalRecordAmount;
        logger.info( "数据格式一致性"+formatConsistencyResult);


        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionResultData.getDataRecordCompliance() / totalRecordAmount;
        logger.info( "数据记录依从性"+dataRecordComplianceResult);

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionResultData.getRangeAccuracy() / totalRecordAmount;
        logger.info("数据范围准确性"+ rangeAccuracyResult);

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionResultData.getRecordUniqueness() / totalRecordAmount;
        logger.info( "数据记录唯一性"+recordUniquenessResult);

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionResultData.getTimeBasedTimeliness() / totalRecordAmount;
        logger.info("基于时间段的时效性"+ timeBasedTimelinessResult);

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionResultData.getDataNonVulnerability()/100;
        logger.info("数据非脆弱性"+dataNonVulnerabilityResult+"");

        double result = (dataFileCompletenessResult+dataValueCompletenessResult)/2.0 * weightBean.getCompleteness()  +
                (referentialConsistencyResult+formatConsistencyResult)/2.0 * weightBean.getConsistency() +
                dataRecordComplianceResult * weightBean.getCompliance() +
                rangeAccuracyResult * weightBean.getAccuracy() +
                recordUniquenessResult * weightBean.getUniqueness() +
                timeBasedTimelinessResult * weightBean.getTimeliness() +
                dataNonVulnerabilityResult * weightBean.getVulnerability();
        return result;
    }

    @Override
    public Double dimensionDetailWeightCalculation(DimensionDetailResultBean dimensionDetailResultBean, WeightBean weightBean) {
        // "数据文件完备性":
        float dataFileCompletenessResult = (float)dimensionDetailResultBean.getDataFileCompletenessResult() / dimensionDetailResultBean.getExpectedTotalRecordAmount();
        logger.info( "数据文件完备性"+dataFileCompletenessResult);

        // "数据值完备性":
        float dataValueCompletenessResult = (float)dimensionDetailResultBean.getDataValueCompletenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataValueCompleteness();
        logger.info("数据值完备性"+ dataValueCompletenessResult);


        // "数据引用一致性":
        float referentialConsistencyResult = (float)dimensionDetailResultBean.getReferentialConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfReferentialConsistency();
        logger.info("数据引用一致性"+ referentialConsistencyResult);


        // "数据格式一致性":
        float formatConsistencyResult = (float)dimensionDetailResultBean.getFormatConsistencyResult() / dimensionDetailResultBean.getTotalRecordAmountOfFormatConsistency();
        logger.info( "数据格式一致性"+formatConsistencyResult);


        // "数据记录依从性":
        float dataRecordComplianceResult = (float)dimensionDetailResultBean.getDataRecordComplianceResult() / dimensionDetailResultBean.getTotalRecordAmountOfDataRecordCompliance();
        logger.info( "数据记录依从性"+dataRecordComplianceResult);

        // "数据范围准确性":
        float rangeAccuracyResult = (float)dimensionDetailResultBean.getRangeAccuracyResult() / dimensionDetailResultBean.getTotalRecordAmountOfRangeAccuracy();
        logger.info("数据范围准确性"+ rangeAccuracyResult);

        // "数据记录唯一性":
        float recordUniquenessResult = (float)dimensionDetailResultBean.getRecordUniquenessResult() / dimensionDetailResultBean.getTotalRecordAmountOfRecordUniqueness();
        logger.info( "数据记录唯一性"+recordUniquenessResult);

        // "基于时间段的时效性":
        float timeBasedTimelinessResult = (float)dimensionDetailResultBean.getTimeBasedTimelinessResult() / dimensionDetailResultBean.getTotalRecordAmountOfTimeBasedTimeliness();
        logger.info("基于时间段的时效性"+ timeBasedTimelinessResult);

        // "数据非脆弱性":
        float dataNonVulnerabilityResult = (float)dimensionDetailResultBean.getDataNonVulnerabilityResult()/100;
        logger.info("数据非脆弱性"+dataNonVulnerabilityResult+"");

        double dataFileCompletenessScore = (dataFileCompletenessResult) / 2.0 * weightBean.getCompleteness() * 100;
        double dataValueCompletenessScore = (dataValueCompletenessResult) / 2.0 * weightBean.getCompleteness() * 100;
        double dataCompletenessScore = (dataFileCompletenessResult + dataValueCompletenessResult) / 2.0 * weightBean.getCompleteness() * 100;

        double referentialConsistencyScore = (referentialConsistencyResult) / 2.0 * weightBean.getConsistency() * 100;
        double formatConsistencyScore = (formatConsistencyResult) / 2.0 * weightBean.getConsistency() * 100;
        double dataConsistencyScore = (referentialConsistencyResult + formatConsistencyResult) / 2.0 * weightBean.getConsistency() * 100;

        double dataRecordComplianceScore = dataRecordComplianceResult * weightBean.getCompliance() * 100;
        double rangeAccuracyScore = rangeAccuracyResult * weightBean.getAccuracy() * 100;
        double recordUniquenessScore = recordUniquenessResult * weightBean.getUniqueness() * 100;
        double timeBasedTimelinessScore = timeBasedTimelinessResult * weightBean.getTimeliness() * 100;
        double dataNonVulnerabilityScore = dataNonVulnerabilityResult * weightBean.getVulnerability() * 100;

        DimensionScore dimensionScore = new DimensionScore(dataFileCompletenessScore,dataValueCompletenessScore,dataCompletenessScore,
                                                           referentialConsistencyScore,formatConsistencyScore,dataConsistencyScore,
                                                           dataRecordComplianceScore,rangeAccuracyScore,recordUniquenessScore,
                                                           timeBasedTimelinessScore,dataNonVulnerabilityScore);
        double result = dataCompletenessScore + dataConsistencyScore + dataRecordComplianceScore + rangeAccuracyScore +
                recordUniquenessScore + timeBasedTimelinessScore + dataNonVulnerabilityScore;
        dimensionScore.setTotalDataQualityScore(result);
        dimensionScore.setCreatetime(new Timestamp(System.currentTimeMillis()));
        dimensionScore.setUpdatetime(new Timestamp(System.currentTimeMillis()));

        //存入数据库
        Integer returnNum = dimensionResultService.saveDimensionScore(dimensionScore);
        //logger.info("returnNum: "+returnNum);
        if (returnNum != 1){
            logger.error("the last score fail to save to mysql!!!");
            throw new RuntimeSqlException("there must have some error!");
        }
        //存入redis
        dimensionResultService.saveDimensionScoreToRedis(dimensionScore);
        /*double result1 = (dataFileCompletenessResult+dataValueCompletenessResult)/2.0 * weightBean.getCompleteness()  +
                (referentialConsistencyResult+formatConsistencyResult)/2.0 * weightBean.getConsistency() +
                dataRecordComplianceResult * weightBean.getCompliance() +
                rangeAccuracyResult * weightBean.getAccuracy() +
                recordUniquenessResult * weightBean.getUniqueness() +
                timeBasedTimelinessResult * weightBean.getTimeliness() +
                dataNonVulnerabilityResult * weightBean.getVulnerability();*/

        return result;
    }

}
