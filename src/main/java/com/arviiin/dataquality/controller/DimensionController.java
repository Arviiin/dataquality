package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.service.DimensionService;
import com.arviiin.dataquality.util.BeanUtils;
import com.arviiin.dataquality.util.DimensionMultiThreadCallableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class DimensionController extends BaseController{

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
            //创建数目固定的线程池
            ExecutorService exec = Executors.newFixedThreadPool(9);
            //存放创建的线程  多个不想一个一个提交，可以采用 invokeAll一并提交，但是会同步等待这些任务 会阻塞等待futureResult获取到所有异步执行的结果才会执行
            List<Callable<Map<String,Object>>> tasks = new ArrayList<>();
            //存放线程运行的结果
            //List<Future<Map<String,Integer>>> futureResults = new ArrayList<Future<Map<String,Integer>>>();//若是一个一个的提交则需要这个
            //创建主线程等待子线程的数目
            CountDownLatch endSigle = new CountDownLatch(9);
            for (int i = 0; i < dimensionBeanList.size(); i++) {
                //无返回值的情况
                //exec.submit(new DimensionMultiThreadRunnableImpl(totalRecordAmount, endSigle,dimensionBeanList.get(i),dimensionResultBean));

                Callable<Map<String,Object>> dimensionMultiThread = new DimensionMultiThreadCallableImpl(endSigle,dimensionBeanList.get(i));
                tasks.add(dimensionMultiThread);

//                Future<Map<String,Integer>> futureResult =  exec.submit(new DimensionMultiThreadCallableImpl(endSigle,dimensionBeanList.get(i)));//创建的同时提交线程
//                futureResults.add(futureResult);
            }
            List<Future<Map<String,Object>>> futureResults = exec.invokeAll(tasks);//有返回值的多线程，且同时提交多个线程
            //wait until all sub-thread are finished
            endSigle.await();
            //we want to check whether main thread is waiting for sub-thread
            System.out.println("this is main threads");

            Map<String, Object> resultMap = new HashMap<>();
            if(futureResults!=null&&futureResults.size()>0){
                for (Future<Map<String,Object>> future : futureResults){
                    Map<String, Object> futureMap = future.get();
                    resultMap.putAll(futureMap);
                }
            }
            DimensionDetailResultBean dimensionDetailResultBean = BeanUtils.mapToBean(resultMap, DimensionDetailResultBean.class);

            //存入mysql数据库
            dimensionService.saveDimensionDetailResultData(dimensionDetailResultBean);
            //存入redis
            dimensionService.saveDimensionDetailResultDataToRedis(dimensionDetailResultBean);
            r.setStatus(OK);
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
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
