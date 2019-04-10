package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.*;
import com.arviiin.dataquality.service.DimensionResultService;
import com.arviiin.dataquality.service.EvaluationRelatedService;
import com.arviiin.dataquality.service.UserService;
import com.arviiin.dataquality.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
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

    @Autowired
    private UserService userService;

    //从配置文件里面取发送邮件的地址
    @Value("${spring.mail.username}")
    private String fromEmail;

    @RequestMapping(value = "/data/evaluation_init", method = RequestMethod.POST)//讲道理更新应该用put,但前台用put过于麻烦，就用post了
    public ResponseEntity<JsonResult> saveEvaluationInitData (@RequestParam("username") String username,
                                                              @RequestParam("email") String email,
                                                              @RequestParam("evaluation_name") String evaluationName,
                                                              @RequestParam("evaluation_remark") String evaluationRemark){
        JsonResult r = new JsonResult();//不管是地址栏里的参数，还是表单里面的参数都可以用@RequestParam取得
        try {
            int ret;
            if ("".equals(email) || email == null){//如果不输入也就是空的情况，默认取当前用户的邮箱
                User user = userService.getUserByName(username);
                String userEmail = user.getEmail();
                ret = evaluationRelatedService.saveEvaluationInitData(username,fromEmail,userEmail,evaluationName,evaluationRemark);
            }else {//如果输入了，因为前台已经校验过格式，所以直接调用
                ret = evaluationRelatedService.saveEvaluationInitData(username,fromEmail,email,evaluationName,evaluationRemark);
            }
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
            //获取数据库中的初始设置里的数据  这里注意若用户之前没初始化设置，则会报错。
            Map<String, Object> evaluationInitMap = evaluationRelatedService.getEvaluationInitData(username);
            dataMap.put("evaluationName",evaluationInitMap.get("evaluation_name"));
            dataMap.put("evaluationRemark",evaluationInitMap.get("evaluation_remark"));
            dataMap.put("evaluationRemarkUsername",username);

            //权重放进去
            WeightBean weightResultBean = weightService.getWeightResult();//取出权重
            Map<String,String> weightResult = weightService.formatWeightResultBean(weightResultBean);//取出权重
            dataMap.put("weightResult",weightResult);
            //合格的数量，和总数进去。
            //先从redis里面拿存进redis的详细数据
            DimensionDetailResultBean dimensionDetailResultBean = redisMapper.getDimensionDetailResultDataFromRedis();
            if (dimensionDetailResultBean == null) {
                //redis里面没有，我们再去数据库里面拿
                //DimensionResultBean dimensionResultData = dimensionResultService.getDimensionResultData();
                dimensionDetailResultBean = dimensionResultService.getDimensionDetailResultData();
            }
            dataMap.put("dimensionDetailResultBean",dimensionDetailResultBean);

            //拿时间凑编号
            Timestamp updatetime = dimensionDetailResultBean.getUpdatetime();
            //2019-03-01 10:42:49.362
            String substring = updatetime.toString().replaceAll("-", "").replace(" ","").replace(".","").replaceAll(":","");//20190301
            dataMap.put("evaluationNumber","SJZLPJ"+substring);

            //拿到良率（老师建议不要这个，改成得分）
            /*Map<String,Object>  dimensionResultRatioBean = dimensionResultService.getDimensionResultRatio(dimensionDetailResultBean);
            dataMap.put("dimensionResultRatioBean",dimensionResultRatioBean);*/
            //拿到指标的初始得分
            Map<String,Object>  dimensionResultRatioScoreBean = dimensionResultService.getDimensionResultRatioScore(dimensionDetailResultBean);
            dataMap.put("dimensionResultRatioBean",dimensionResultRatioScoreBean);//注意前面是dimensionResultRatioBean没有score


            //拿到分值并把把分值格式化为保留后两位小数的字符串
            DimensionScore dimensionScoreResult = dimensionResultService.getDimensionScore();
            Map<String,String> dimensionScore = dimensionResultService.formatDimensionScore(dimensionScoreResult);
            dataMap.put("dimensionScore",dimensionScore);

            //给出建议
            //拿到评估等级
            String evaluationLevel = dimensionResultService.getDimensionEvaluationLevel(dimensionScoreResult);
            //拿到最差的质量维度及其比值
            String minRatio = dimensionResultService.getDimensionResultMinRatio(dimensionDetailResultBean);
            dataMap.put("suggestion","数据质量评价结果为："+evaluationLevel+"。良率中最差的质量维度为："+minRatio+
                    "。建议仔细分析此质量维度差的原因。");
            r.setResult(dataMap);
            r.setStatus(OK);

        } catch (Exception e) {
            //r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            r.setResult("评估人名为空请进行初始化设置");
            e.printStackTrace();
            logger.error("评估人名为空请进行初始化设置");
        }
        return ResponseEntity.ok(r);
    }
}
