package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/data/send_email")
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
}
