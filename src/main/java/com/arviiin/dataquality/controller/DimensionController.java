package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.service.DimensionService;
import com.arviiin.dataquality.util.DimensionMultiThreadRunnableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        DimensionResultBean dimensionResultBean = new DimensionResultBean();
        int totalRecordAmount = -1;
        try {
            //创建按需增加的线程池
            ExecutorService exec = Executors.newFixedThreadPool(9);
            //存放创建的线程  多个不想一个一个提交，可以采用 invokeAll一并提交，但是会同步等待这些任务 会阻塞等待futureResult获取到所有异步执行的结果才会执行
            //List<Callable<String>> tasks = new ArrayList<>();
            //存放线程运行的结果
            //ArrayList<Future<String>> results = new ArrayList<Future<String>>();
            //创建主线程等待子线程的数目
            CountDownLatch endSigle = new CountDownLatch(9);
            for (int i = 0; i < dimensionBeanList.size(); i++) {
                exec.submit(new DimensionMultiThreadRunnableImpl(totalRecordAmount, endSigle,dimensionBeanList.get(i),dimensionResultBean));

                //Callable<String> dimensionMultiThread = new DimensionMultiThread(totalRecordAmount, endSigle,dimensionBeanList.get(i),dimensionResultBean);
                //tasks.add(dimensionMultiThread);

                //Callable<String> newMultiThread = new NewMultiThread(i, endSigle);
                //tasks.add(newMultiThread);

                //Future<String> futureResult =  exec.submit(new DimensionMultiThread(i,endSigle,dimensionBeanList.get(i)));
                //results.add(futureResult);
            }
            //List<Future<String>> futureResult = exec.invokeAll(tasks);//有返回值的多线程

            //wait until all sub-thread are finished
            endSigle.await();
            //we want to check whether main thread is waiting for sub-thread
            System.out.println("this is main threads");

            /*if(futureResult!=null&&futureResult.size()>0){
                for (Future<String> future : futureResult){
                    System.out.println(future.get());
                    results.add(future);
                }
            }*/

            //存入mysql数据库
            dimensionService.saveDimensionResultData(dimensionResultBean);
            //存入redis
            dimensionService.saveDimensionResultDataToRedis(dimensionResultBean);
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
