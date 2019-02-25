package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.ColBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.model.UserPojo;
import com.arviiin.dataquality.service.DataListService;
import com.arviiin.dataquality.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/2/25
 * @Version 1.0.0
 */
@RestController
public class DataListController {

    private static Logger logger = LoggerFactory.getLogger(DataListController.class);

    @Autowired
    private DataListService dataListService;

    @PostMapping("/data/list")
    public ResponseEntity<JsonResult> getDataListByTableName (@RequestParam(value = "tablename") String tablename){
        JsonResult r = new JsonResult();
        try {
            List<UserPojo> dataList = dataListService.getDataListByTableName(tablename);

            Map<Object, Object> dataMap = new HashMap<>();
            List<Object> col = new ArrayList<>();

            Field[] declaredFields = UserPojo.class.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                ColBean colBean = new ColBean();
                colBean.setProp(declaredField.getName());
                colBean.setLable(declaredField.getName());
                col.add(colBean);
            }
            dataMap.put("clo",col);

            String tableData = JsonUtils.objectToJson(dataList);
            dataMap.put("tableData",tableData);

            System.out.println(dataMap);


            r.setResult(dataMap);
            r.setStatus("ok");
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
