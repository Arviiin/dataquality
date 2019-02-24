package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.service.DimensionService;
import com.arviiin.dataquality.service.VisualizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VisualizController {

    @Autowired
    private VisualizService visualizService;

    @Autowired
    private DimensionService dimensionService;


    @GetMapping("/data/visualization")
    public Map<String,Object> dataStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> categories = visualizService.getCategories();
        //先去redis里面找，如果没有再请求数据库
        List<Float> dataStatistics = visualizService.getDataStatisticsFromRedis();
        if (dataStatistics == null){
            //请求数据库
            dataStatistics = visualizService.getDataStatistics();
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


    /*@GetMapping(value = "/data/visualization")
    public ResponseEntity<JsonResult> getVisualizationResult (){
        JsonResult r = new JsonResult();
        Map<String, Object> map = new HashMap<>();
        try {
            List<String> categories = visualizService.getCategories();
            List<Integer> dataStatistics = visualizService.getDataStatistics();
            map.put("categories", categories);
            map.put("ds", dataStatistics);
            //return map;
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }*/
}
