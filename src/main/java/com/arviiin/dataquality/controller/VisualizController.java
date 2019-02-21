package com.arviiin.dataquality.controller;

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


    @GetMapping("/data/visualization")
    public Map<String,Object> dataStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> categories = visualizService.getCategories();
        List<Float> dataStatistics = visualizService.getDataStatistics();
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
