package com.arviiin.dataquality.service;

import java.util.List;
import java.util.Map;

public interface EmailService {

    String sendEmail();

    void saveRecordOfSendEmail(Map<String,Object> latestEvaluationInitData);

    List<Map<String,Object>> getRecordOfSendEmail();
}
