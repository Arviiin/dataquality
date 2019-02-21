package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.DataQualityCalculationService;
import com.arviiin.dataquality.service.DimensionResultService;
import com.arviiin.dataquality.service.WeightService;
import com.arviiin.dataquality.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class WeightController {

    private static Logger logger = LoggerFactory.getLogger(WeightController.class);

    @Autowired
    private WeightService weightService;

    @Autowired
    private DimensionResultService dimensionResultService;

    @Autowired
    private DataQualityCalculationService dataQualityCalculationService;


    /**
     * 接收前端传来的权重相关的数据
     * @param
     * @return
     */
    @PostMapping(value = "data/weight")
    public ResponseEntity<JsonResult> getWeightResult (@RequestBody String json){
        JsonResult r = new JsonResult();
        WeightBean receiveWeightResult = JsonUtils.jsonToPojo(json,WeightBean.class);

        try {
            if (receiveWeightResult == null || receiveWeightResult.getCompleteness() == null || "".equals(receiveWeightResult.getCompleteness())){
                WeightBean defaultWeightResult = weightService.getDefaultWeightResult();
                DimensionResultBean dimensionResultData = dimensionResultService.getDimensionResultData();

                //对结果最后进行加权计算
                Double calculationResult = dataQualityCalculationService.dimensionWeightCalculation(dimensionResultData, defaultWeightResult);

                System.out.println(calculationResult + "最后得分");
                //System.out.println(defaultWeightResult);
                logger.info(dimensionResultData.toString());
                System.out.println(dimensionResultData.toString());
            }else{
                DimensionResultBean dimensionResultData = dimensionResultService.getDimensionResultData();
                //对结果最后进行加权计算
                Double calculationResult = dataQualityCalculationService.dimensionWeightCalculation(dimensionResultData,receiveWeightResult);
                System.out.println(calculationResult + "最后得分");
                logger.info(dimensionResultData.toString());
                //System.out.println(weightBean.toString());
                System.out.println(dimensionResultData.toString());
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
