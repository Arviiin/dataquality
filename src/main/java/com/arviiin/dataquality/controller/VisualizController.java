package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.DimensionResultService;
import com.arviiin.dataquality.service.VisualizService;
import com.arviiin.dataquality.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VisualizController extends BaseController{

    @Autowired
    private VisualizService visualizService;

    @Autowired
    private DimensionResultService dimensionResultService;


    @Autowired
    private WeightService weightService;


    @GetMapping("/data/visualization")
    public Map<String,Object> dataStatistics() {
        Map<String, Object> map = new LinkedHashMap<>();
        List<String> categories = visualizService.getCategories();
        //先去redis里面找，如果没有再请求数据库
        List<Float> dataStatistics = visualizService.getDataStatisticsFromRedis();
        if (dataStatistics == null){
            //请求数据库
            //dataStatistics = visualizService.getDataStatistics();
            dataStatistics = visualizService.getDetailDataStatistics();

            //再把数据存入redis缓存
            //把dataStatistics组装成dimensionResultBean以后再存到redis中
            //DimensionResultBean dimensionResultBean = new DimensionResultBean();
            //dimensionService.saveDimensionResultDataToRedis(dataStatistics);
        }
        map.put("categories", categories);
        map.put("ds", dataStatistics);
        map.put("code", 200);
        return map;
    }

    @GetMapping("/chart/mixChart")
    public Map<String,Object> getMixChartData() {
        Map<String, Object> map = new LinkedHashMap<>();
        DimensionDetailResultBean dimensionDetailResultData = dimensionResultService.getDimensionDetailResultData();

        map.put("resultData", dimensionDetailResultData);
        map.put("code", 200);
        return map;
    }

    @GetMapping("/data/score")
    public Map<String,Object> getDimensionScoreData() {
        Map<String, Object> map = new LinkedHashMap<>();
        DimensionScore dimensionScore = dimensionResultService.getDimensionScore();
        /*传给后台这样的数据List+5个Map
        data: [
        { value: 320, name: 'Industries' },
        { value: 240, name: 'Technology' },
        { value: 149, name: 'Forex' },
        { value: 100, name: 'Gold' },
        { value: 59, name: 'Forecasts' }
         ]*/
        List<Map<String,Object>> arrayList = new ArrayList<>();
        Map<String, Object> hashMap1 = new LinkedHashMap<>();
        hashMap1.put("value",dimensionScore.getDataCompletenessScore());
        hashMap1.put("name","数据完备性");
        arrayList.add(hashMap1);

        Map<String, Object> hashMap2 = new LinkedHashMap<>();
        hashMap2.put("value",dimensionScore.getDataConsistencyScore());
        hashMap2.put("name","数据一致性");
        arrayList.add(hashMap2);

        Map<String, Object> hashMap3 = new LinkedHashMap<>();
        hashMap3.put("value",dimensionScore.getDataRecordComplianceScore());
        hashMap3.put("name","数据依从性");
        arrayList.add(hashMap3);

        Map<String, Object> hashMap4 = new LinkedHashMap<>();
        hashMap4.put("value",dimensionScore.getRangeAccuracyScore());
        hashMap4.put("name","数据准确性");
        arrayList.add(hashMap4);

        Map<String, Object> hashMap5 = new LinkedHashMap<>();
        hashMap5.put("value",dimensionScore.getRecordUniquenessScore());
        hashMap5.put("name","数据唯一性");
        arrayList.add(hashMap5);

        Map<String, Object> hashMap6 = new LinkedHashMap<>();
        hashMap6.put("value",dimensionScore.getTimeBasedTimelinessScore());
        hashMap6.put("name","数据现实性");
        arrayList.add(hashMap6);

        Map<String, Object> hashMap7 = new LinkedHashMap<>();
        hashMap7.put("value",dimensionScore.getDataNonVulnerabilityScore());
        hashMap7.put("name","数据保密性");
        arrayList.add(hashMap7);
        /*传给后台这样的数据List+5个Map
        data: [
        { value: 320, name: 'Industries' },
        { value: 240, name: 'Technology' },
        { value: 149, name: 'Forex' },
        { value: 100, name: 'Gold' },
        { value: 59, name: 'Forecasts' }
         ]*/
        //注意不要把它转成json再传，那样的话传的就是
       /* [  key和双引号了
        { "value": 320, "name": "Industries" },
        { value: 240, name: 'Technology' },
        { value: 149, name: 'Forex' },
        { value: 100, name: 'Gold' },
        { value: 59, name: 'Forecasts' }
         ]*/
        map.put("resultData", arrayList);
        map.put("code", 200);
        return map;
    }


    @GetMapping("/data/weight")
    public Map<String,Object> getDimensionWeightData() {
        Map<String, Object> map = new LinkedHashMap<>();
        WeightBean weightResult = weightService.getWeightResult();
        /*传给后台这样的数据List+5个Map
        data: [
        { value: 320, name: 'Industries' },
        { value: 240, name: 'Technology' },
        { value: 149, name: 'Forex' },
        { value: 100, name: 'Gold' },
        { value: 59, name: 'Forecasts' }
         ]*/
        List<Map<String,Object>> arrayList = new ArrayList<>();
        Map<String, Object> hashMap1 = new LinkedHashMap<>();
        hashMap1.put("value",weightResult.getCompleteness());
        hashMap1.put("name","数据完备性");
        arrayList.add(hashMap1);

        Map<String, Object> hashMap2 = new LinkedHashMap<>();
        hashMap2.put("value",weightResult.getConsistency());
        hashMap2.put("name","数据一致性");
        arrayList.add(hashMap2);

        Map<String, Object> hashMap3 = new LinkedHashMap<>();
        hashMap3.put("value",weightResult.getCompliance());
        hashMap3.put("name","数据依从性");
        arrayList.add(hashMap3);

        Map<String, Object> hashMap4 = new LinkedHashMap<>();
        hashMap4.put("value",weightResult.getAccuracy());
        hashMap4.put("name","数据准确性");
        arrayList.add(hashMap4);

        Map<String, Object> hashMap5 = new LinkedHashMap<>();
        hashMap5.put("value",weightResult.getUniqueness());
        hashMap5.put("name","数据唯一性");
        arrayList.add(hashMap5);

        Map<String, Object> hashMap6 = new LinkedHashMap<>();
        hashMap6.put("value",weightResult.getTimeliness());
        hashMap6.put("name","数据现实性");
        arrayList.add(hashMap6);

        Map<String, Object> hashMap7 = new LinkedHashMap<>();
        hashMap7.put("value",weightResult.getVulnerability());
        hashMap7.put("name","数据保密性");
        arrayList.add(hashMap7);
        /*传给后台这样的数据List+5个Map
        data: [
        { value: 320, name: 'Industries' },
        { value: 240, name: 'Technology' },
        { value: 149, name: 'Forex' },
        { value: 100, name: 'Gold' },
        { value: 59, name: 'Forecasts' }
         ]*/
        //注意不要把它转成json再传，那样的话传的就是
       /* [  key和双引号了
        { "value": 320, "name": "Industries" },
        { value: 240, name: 'Technology' },
        { value: 149, name: 'Forex' },
        { value: 100, name: 'Gold' },
        { value: 59, name: 'Forecasts' }
         ]*/
        map.put("resultData", arrayList);
        map.put("code", 200);
        return map;
    }

}
