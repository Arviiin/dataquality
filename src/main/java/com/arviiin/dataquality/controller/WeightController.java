package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.DataQualityCalculationService;
import com.arviiin.dataquality.service.DimensionResultService;
import com.arviiin.dataquality.service.WeightService;
import com.arviiin.dataquality.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class WeightController extends BaseController{

    @Autowired
    private WeightService weightService;

    @Autowired
    private DimensionResultService dimensionResultService;

    @Autowired
    private DataQualityCalculationService dataQualityCalculationService;

    @Autowired
    private RedisMapper redisMapper;

    /**
     * 接收前端传来的权重相关的数据
     * @param
     * @return
     */
    @PostMapping(value = "data/weight")
    public ResponseEntity<JsonResult> getWeightResult (@RequestBody String json){
        JsonResult r = new JsonResult();
        try {
            //这里对权重是否使用默认进行判断，为空则使用默认
            WeightBean weightResult = JsonUtils.jsonToPojo(json,WeightBean.class);
            logger.info(weightResult.toString());
            if (weightResult == null || weightResult.getCompleteness() == null || "".equals(weightResult.getCompleteness())){
                //拿默认权重
                weightResult = weightService.getDefaultWeightResult();
            }
            //从redis里面拿存进redis的详细数据
            DimensionDetailResultBean dimensionDetailResultBean = redisMapper.getDimensionDetailResultDataFromRedis();
            if (dimensionDetailResultBean == null) {
                //redis里面没有，我们再去数据库里面拿
                //DimensionResultBean dimensionResultData = dimensionResultService.getDimensionResultData();
                dimensionDetailResultBean = dimensionResultService.getDimensionDetailResultData();
            }
            //对结果最后进行加权计算
            //Double calculationResult = dataQualityCalculationService.dimensionWeightCalculation(dimensionResultData, defaultWeightResult);
            Double calculationResult = dataQualityCalculationService.dimensionDetailWeightCalculation(dimensionDetailResultBean, weightResult);

            System.out.println(calculationResult + "最后得分");
            //System.out.println(defaultWeightResult);
            System.out.println(dimensionDetailResultBean.toString());

            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
