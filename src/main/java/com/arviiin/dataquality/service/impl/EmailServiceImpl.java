package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.EmailMapper;
import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import com.arviiin.dataquality.model.WeightBean;
import com.arviiin.dataquality.service.DimensionResultService;
import com.arviiin.dataquality.service.EmailService;
import com.arviiin.dataquality.service.EvaluationRelatedService;
import com.arviiin.dataquality.service.WeightService;
import com.arviiin.dataquality.util.EmailRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    ExecutorService executorService;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private EvaluationRelatedService evaluationRelatedService;

    @Autowired
    private WeightService weightService;

    @Autowired
    private DimensionResultService dimensionResultService;

    @Autowired
    private RedisMapper redisMapper;

    @Autowired
    private EmailMapper emailMapper;

    //从配置文件里面取发送邮件的地址
    @Value("${spring.mail.username}")
    private String fromEmail;

    private String fromName = "数值质量量化分析平台";

    private String toEmail;

    @Override
    public String sendEmail() {
        try {
            //创建map用于装备数据
            Map<String, Object> dataMap = new HashMap<>();
            //获取数据库中的初始设置里的数据  这里注意若用户之前没初始化设置，则会报错。
            Map<String, Object> evaluationInitMap = evaluationRelatedService.getLatestEvaluationInitData();
            dataMap.put("evaluationName",evaluationInitMap.get("evaluation_name"));
            dataMap.put("evaluationRemark",evaluationInitMap.get("evaluation_remark"));
            dataMap.put("evaluationRemarkUsername",evaluationInitMap.get("evaluation_username"));
            toEmail = (String) evaluationInitMap.get("email");
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


            //执行子线程发送邮件
            executorService.execute(new EmailRunnable(fromEmail,toEmail,dataMap, javaMailSender, templateEngine));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @Override
    public void saveRecordOfSendEmail(Map<String, Object> latestEvaluationInitData) {
        toEmail = (String) latestEvaluationInitData.get("email");
        String toName = (String) latestEvaluationInitData.get("evaluation_username");
        Timestamp createtime = new Timestamp(System.currentTimeMillis());
        Timestamp updatetime = new Timestamp(System.currentTimeMillis());
        emailMapper.saveRecordOfSendEmail(fromName,fromEmail,toName,toEmail,createtime,updatetime);
    }

    @Override
    public List<Map<String, Object>> getRecordOfSendEmail() {
        return emailMapper.getRecordOfSendEmail();
    }
}
