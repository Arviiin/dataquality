package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.DimensionResultService;
import com.arviiin.dataquality.service.EvaluationRelatedService;
import com.arviiin.dataquality.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class EvaluationRelatedController extends BaseController{
    @Autowired
    private EvaluationRelatedService evaluationRelatedService;

    @Autowired
    private WeightService weightService;

    @Autowired
    private DimensionResultService dimensionResultService;

    @Autowired
    private RedisMapper redisMapper;



    @RequestMapping(value = "/data/evaluation_init", method = RequestMethod.POST)//讲道理更新应该用put,但前台用put过于麻烦，就用post了
    public ResponseEntity<JsonResult> saveEvaluationInitData (@RequestParam("username") String username,
                                                      @RequestParam("evaluation_name") String evaluationName,
                                                      @RequestParam("evaluation_remark") String evaluationRemark){
        JsonResult r = new JsonResult();//不管是地址栏里的参数，还是表单里面的参数都可以用@RequestParam取得
        try {
            int ret = evaluationRelatedService.saveEvaluationInitData(username,evaluationName,evaluationRemark);
            //ret 1 为正常
            if (ret != 1) {
                r.setResult(ret);
                r.setStatus(FAIL_STRING);
            } else if (ret == 1){
                r.setResult(ret);
                r.setStatus(OK);
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    @PostMapping("/result/report")
    public ResponseEntity<JsonResult> getDataForEvaluationReport (@RequestParam("username") String username){
        JsonResult r = new JsonResult();
        try {
            //创建map用于装备数据
            Map<String, Object> dataMap = new HashMap<>();
            //获取数据库中的初始设置里的数据
            Map<String, Object> evaluationInitMap = evaluationRelatedService.getEvaluationInitData(username);
            dataMap.put("evaluationName",evaluationInitMap.get("evaluation_name"));
            dataMap.put("evaluationRemark",evaluationInitMap.get("evaluation_remark"));
            dataMap.put("evaluationRemarkUsername",username);

            //权重放进去
            WeightBean weightResult = weightService.getDefaultWeightResult();
            dataMap.put("weightResult",weightResult);
            //合格的数量，和总数和良率放进去。
            //先从redis里面拿存进redis的详细数据
            DimensionDetailResultBean dimensionDetailResultBean = redisMapper.getDimensionDetailResultDataFromRedis();
            if (dimensionDetailResultBean == null) {
                //redis里面没有，我们再去数据库里面拿
                //DimensionResultBean dimensionResultData = dimensionResultService.getDimensionResultData();
                dimensionDetailResultBean = dimensionResultService.getDimensionDetailResultData();
            }
            dataMap.put("weightResult",dimensionDetailResultBean);
            //拿到良率
            Map<String,Object>  dimensionResultRatioBean = dimensionResultService.getDimensionResultRatio(dimensionDetailResultBean);
            dataMap.put("dimensionResultRatioBean",dimensionResultRatioBean);

            //拿到分值
            DimensionScore dimensionScore = dimensionResultService.getDimensionScore();
            dataMap.put("dimensionScore",dimensionScore);
            //**************开始组装col****************
            List<Object> col = new ArrayList<>();
            //拿到第一行数据，数据库字段名与相应数值
            /*Map<String, Object> fieldObjectMap = dataMapList.get(0);
            //拿到第一行数据库字段名
            Set<String> filedSet = fieldObjectMap.keySet();
            for (String field : filedSet) {
                ColBean colBean = new ColBean();
                colBean.setProp(field);
                colBean.setLable(field);
                col.add(colBean);
            }
            dataMap.put("clo",col);
            //**************开始组装tableData****************

            String tableData = JsonUtils.objectToJson(dataMapList);//不转成json，直接把dataMapList传到前台，别的没问题，就是时间乱码，因而还是建议转一下，变成13位的时间戳，交给前台处理
            dataMap.put("tableData",tableData);*/

            r.setResult(dataMap);
            r.setStatus(OK);
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
