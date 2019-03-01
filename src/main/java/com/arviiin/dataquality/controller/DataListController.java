package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.ColBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.service.DataListService;
import com.arviiin.dataquality.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/2/25
 * @Version 1.0.0
 */
@RestController
public class DataListController extends BaseController{

    @Autowired
    private DataListService dataListService;

    @PostMapping("/data/list")
    public ResponseEntity<JsonResult> getDataListByTableNameWithMap (@RequestParam(value = "tablename") String tablename){
        JsonResult r = new JsonResult();
        try {
            //创建map用于装备col和tableData
            Map<String, Object> dataMap = new HashMap<>();
            //获取数据库表中数据
            List<Map<String, Object>> dataMapList = dataListService.getDataListByTableNameWithMap(tablename);
            /* **************开始组装col*****************/
            List<Object> col = new ArrayList<>();
            //拿到第一行数据，数据库字段名与相应数值
            Map<String, Object> fieldObjectMap = dataMapList.get(0);
            //拿到第一行数据库字段名
            Set<String> filedSet = fieldObjectMap.keySet();
            for (String field : filedSet) {
                ColBean colBean = new ColBean();
                colBean.setProp(field);
                colBean.setLable(field);
                col.add(colBean);
            }
            dataMap.put("clo",col);
            /* **************开始组装tableData*****************/
            String tableData = JsonUtils.objectToJson(dataMapList);//不转成json，直接把dataMapList传到前台，别的没问题，就是时间乱码，因而还是建议转一下，变成13位的时间戳，交给前台处理
            dataMap.put("tableData",tableData);

            r.setResult(dataMap);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    @PostMapping("/data/listTable")
    public ResponseEntity<JsonResult> getAllTableNameByDbName (@RequestParam(value = "dbname") String dbname){
        JsonResult r = new JsonResult();
        try {
            //获取数据库表中数据
            List<String> allTableNameList = dataListService.getAllTableNameByDbName(dbname);

            r.setResult(allTableNameList);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
