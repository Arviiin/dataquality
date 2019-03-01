package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.service.DimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class DimensionSingleThreadController extends BaseController{

    @Autowired
    private DimensionService dimensionService;

    /**
     * 接收前端传来的指标维度相关的数据
     * @param dimensionBeanList
     * @return
     */
    @PostMapping(value = "data/dimension")
    public ResponseEntity<JsonResult> getDimensionIndexResult (@RequestBody List<DimensionBean> dimensionBeanList){
        JsonResult r = new JsonResult();
        DimensionResultBean dimensionResultBean = new DimensionResultBean();
        try {
            int totalRecordAmount = -1;
            for (DimensionBean dimensionBean : dimensionBeanList) {
                switch (dimensionBean.getDimensionname()){
                    case "数据文件完备性":
                        int expectedTotalRecordAmount = dimensionService.getExpectedTotalRecordAmount(dimensionBean);
                        dimensionResultBean.setExpectedTotalRecordAmount(expectedTotalRecordAmount);
                        int dataFileCompletenessResult = dimensionService.getDataFileCompletenessResult(dimensionBean);
                        dimensionResultBean.setDataFileCompleteness(dataFileCompletenessResult);
//                        logger.info(expectedTotalRecordAmount+"");
//                        logger.info(dataFileCompletenessResult+"");
                        logger.info( "数据文件完备性"+(float)dataFileCompletenessResult/expectedTotalRecordAmount+"");
                        break;
                    case "数据值完备性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int dataValueCompletenessResult = dimensionService.getDataValueCompletenessResult(dimensionBean);
                        dimensionResultBean.setDataValueCompleteness(dataValueCompletenessResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(dataValueCompletenessResult+"");
                        logger.info("数据值完备性"+ (float)dataValueCompletenessResult/totalRecordAmount+"");
                        break;
                    case "数据引用一致性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int referentialConsistencyResult = dimensionService.getReferentialConsistencyResult(dimensionBean);
                        dimensionResultBean.setReferentialConsistency(referentialConsistencyResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(referentialConsistencyResult+"");
                        logger.info("数据引用一致性"+ (1 - (float)referentialConsistencyResult/totalRecordAmount)+"");
                        break;

                    case "数据格式一致性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int formatConsistencyResult = dimensionService.getFormatConsistencyResult(dimensionBean);
                        dimensionResultBean.setFormatConsistency(formatConsistencyResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(formatConsistencyResult+"");
                        logger.info( "数据格式一致性"+(float)formatConsistencyResult/totalRecordAmount+"");
                        break;
                    case "数据记录依从性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int dataRecordComplianceResult = dimensionService.getDataRecordComplianceResult(dimensionBean);
                        dimensionResultBean.setDataRecordCompliance(dataRecordComplianceResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(dataRecordComplianceResult+"");
                        logger.info( "数据记录依从性"+(float)dataRecordComplianceResult/totalRecordAmount+"");
                        break;
                    case "数据范围准确性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int rangeAccuracyResult = dimensionService.getRangeAccuracyResult(dimensionBean);
                        dimensionResultBean.setRangeAccuracy(rangeAccuracyResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(rangeAccuracyResult+"");
                        logger.info("数据范围准确性"+ (float)rangeAccuracyResult/totalRecordAmount+"");
                        break;
                    case "数据记录唯一性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int recordUniquenessResult = dimensionService.getRecordUniquenessResult(dimensionBean);
                        dimensionResultBean.setRecordUniqueness(recordUniquenessResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(recordUniquenessResult+"");
                        logger.info( "数据记录唯一性"+(float)recordUniquenessResult/totalRecordAmount+"");
                        break;
                    case "基于时间段的时效性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int timeBasedTimelinessResult = dimensionService.getTimeBasedTimelinessResult(dimensionBean);
                        dimensionResultBean.setTimeBasedTimeliness(timeBasedTimelinessResult);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(timeBasedTimelinessResult+"");
                        logger.info("基于时间段的时效性"+ (float)timeBasedTimelinessResult/totalRecordAmount+"");
                        break;
                    case "数据非脆弱性":
                        int dataNonVulnerabilityResult  = dimensionService.getDataNonVulnerabilityResult(dimensionBean);
                        dimensionResultBean.setDataNonVulnerability(dataNonVulnerabilityResult);
                        logger.info("数据非脆弱性"+dataNonVulnerabilityResult+"");
                        break;
                    default:
                        logger.error("数据错误，未找到指标名为:"+dimensionBean.getDimensionname()+"的指标");
                        break;
                }

            }
            dimensionResultBean.setTotalRecordAmount(totalRecordAmount);
            //存入mysql数据库
            dimensionService.saveDimensionResultData(dimensionResultBean);
            //存入redis
            dimensionService.saveDimensionResultDataToRedis(dimensionResultBean);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

}
/*
[
        {
            "columnname": "id",
            "dimensionname": "数据文件完备性",
            "rule": "string",
            "tablename": "user"
        },
        {
            "columnname": "enabled",
            "dimensionname": "数据值完备性",
            "rule": "string",
            "tablename": "user"
        },
        {
            "columnname": "rid:id",
            "dimensionname": "数据引用一致性",
            "rule": "string",
            "tablename": "roles_user:roles"
        },
        {
            "columnname": "email",
            "dimensionname": "数据格式一致性",
            "rule": "邮箱规则",
            "tablename": "user"
        },
        {
            "columnname": "testaddr",
            "dimensionname": "数据记录依从性",
            "rule": "string",
            "tablename": "user"
        },
        {
            "columnname": "testrange",
            "dimensionname": "数据范围准确性",
            "rule": "0:20",
            "tablename": "user"
        },
        {
            "columnname": "company",
            "dimensionname": "数据记录唯一性",
            "rule": "string",
            "tablename": "user"
        },
        {
            "columnname": "createtime",
            "dimensionname": "基于时间段的时效性",
            "rule": "2018-04-01:2018-08-19",
            "tablename": "user"
        },
        {
            "columnname": "string",
            "dimensionname": "数据非脆弱性",
            "rule": "明文",
            "tablename": "string"
        }
        ]
        */
