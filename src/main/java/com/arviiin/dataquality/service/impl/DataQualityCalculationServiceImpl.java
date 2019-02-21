package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.DataQualityCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataQualityCalculationServiceImpl implements DataQualityCalculationService {

    private static Logger logger = LoggerFactory.getLogger(DataQualityCalculationServiceImpl.class);

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
        float referentialConsistencyResult = 1 - (float)dimensionResultData.getReferentialConsistency() / totalRecordAmount;
        logger.info("数据引用一致性"+ (1 - referentialConsistencyResult));


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

}
