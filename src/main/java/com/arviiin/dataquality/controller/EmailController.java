package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.service.EmailService;
import com.arviiin.dataquality.service.EvaluationRelatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/4/5
 * @Version 1.0.0
 */
@RestController
public class EmailController extends BaseController{

    @Autowired
    private EmailService emailService;

    @Autowired
    private EvaluationRelatedService evaluationRelatedService;

    @GetMapping("/data/get_send_email_info")
    public ResponseEntity<JsonResult> getSendEmailInfo (/*@RequestParam("username") String username*/){
        JsonResult r = new JsonResult();
        try {
            Map<String, Object> latestEvaluationInitData = evaluationRelatedService.getLatestEvaluationInitData();
            r.setStatus(OK);
            r.setResult(latestEvaluationInitData);
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    @PostMapping("/data/send_email")
    public ResponseEntity<JsonResult> sendEmail (){
        JsonResult r = new JsonResult();
        try {
            emailService.sendEmail();
            r.setStatus(OK);
            r.setResult(1);
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    @GetMapping("/data/get_send_email_record")
    public ResponseEntity<JsonResult> getSendEmailRecord (){
        JsonResult r = new JsonResult();
        try {
            List<Map<String, Object>> sendEmailRecord = emailService.getRecordOfSendEmail();
            r.setStatus(OK);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Map<String, Object> stringObjectMap : sendEmailRecord) {
                stringObjectMap.put("sendtime",simpleDateFormat.format(stringObjectMap.get("sendtime")));
            }
            r.setResult(sendEmailRecord);
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
