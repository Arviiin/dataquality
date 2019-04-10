package com.arviiin.dataquality.mapper;

import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/4/7
 * @Version 1.0.0
 */
public interface EmailMapper {
    void saveRecordOfSendEmail(@Param("fromName") String fromName,
                               @Param("fromEmail") String fromEmail,
                               @Param("toName") String toName,
                               @Param("toEmail") String toEmail,
                               @Param("createtime") Timestamp createtime,
                               @Param("updatetime") Timestamp updatetime);

    List<Map<String,Object>> getRecordOfSendEmail();
}
