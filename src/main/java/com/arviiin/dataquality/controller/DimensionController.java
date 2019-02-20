package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.service.DimensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class DimensionController {
    private static Logger logger = LoggerFactory.getLogger(DimensionController.class);

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
        try {
            int totalRecordAmount = -1;
            for (DimensionBean dimensionBean : dimensionBeanList) {
                switch (dimensionBean.getDimensionname()){
                    case "数据文件完备性":
                        int expectedTotalRecordAmount = dimensionService.getExpectedTotalRecordAmount(dimensionBean);
                        int dataFileCompletenessResult = dimensionService.getDataFileCompletenessResult(dimensionBean);
//                        logger.info(expectedTotalRecordAmount+"");
//                        logger.info(dataFileCompletenessResult+"");
                        logger.info( (float)dataFileCompletenessResult/expectedTotalRecordAmount+"");
                        break;
                    case "数据值完备性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int dataValueCompletenessResult = dimensionService.getDataValueCompletenessResult(dimensionBean);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(dataValueCompletenessResult+"");
                        logger.info( (float)dataValueCompletenessResult/totalRecordAmount+"");
                        break;
                    case "数据引用一致性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int referentialConsistencyResult = dimensionService.getReferentialConsistencyResult(dimensionBean);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(referentialConsistencyResult+"");
                        logger.info( (1 - (float)referentialConsistencyResult/totalRecordAmount)+"");
                        break;

                    case "数据格式一致性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        Integer formatConsistencyResult = dimensionService.getFormatConsistencyResult(dimensionBean);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(formatConsistencyResult+"");
                        logger.info( (float)formatConsistencyResult/totalRecordAmount+"");
                        break;
                    case "数据记录依从性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int dataRecordComplianceResult = dimensionService.getDataRecordComplianceResult(dimensionBean);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(dataRecordComplianceResult+"");
                        logger.info( (float)dataRecordComplianceResult/totalRecordAmount+"");
                        break;
                    case "数据范围准确性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int rangeAccuracyResult = dimensionService.getRangeAccuracyResult(dimensionBean);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(rangeAccuracyResult+"");
                        logger.info( (float)rangeAccuracyResult/totalRecordAmount+"");
                        break;
                    case "数据记录唯一性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int recordUniquenessResult = dimensionService.getRecordUniquenessResult(dimensionBean);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(recordUniquenessResult+"");
                        logger.info( (float)recordUniquenessResult/totalRecordAmount+"");
                        break;
                    case "基于时间段的时效性":
                        if (totalRecordAmount == -1)
                            totalRecordAmount = dimensionService.getTotalRecordAmount(dimensionBean);
                        int timeBasedTimelinessResult = dimensionService.getTimeBasedTimelinessResult(dimensionBean);
//                        logger.info(totalRecordAmount+"");
//                        logger.info(timeBasedTimelinessResult+"");
                        logger.info( (float)timeBasedTimelinessResult/totalRecordAmount+"");
                        break;
                    case "数据非脆弱性":
                        double dataNonVulnerabilityResult  = dimensionService.getDataNonVulnerabilityResult(dimensionBean);
                        logger.info(dataNonVulnerabilityResult+"");
                        break;
                    default:
                        logger.error("数据错误，未找到指标名为:"+dimensionBean.getDimensionname()+"的指标");
                        break;
                }

            }
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
