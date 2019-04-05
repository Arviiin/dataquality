package com.arviiin.dataquality.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/3/19
 * @Version 1.0.0
 */
@Mapper
public interface EvaluationRelatedMapper {

    int saveEvaluationInitData(@Param("username") String username, @Param("evaluationName") String evaluationName,
                               @Param("evaluationRemark") String evaluationRemark,
                               @Param("createtime") Timestamp createtime,
                               @Param("updatetime") Timestamp updatetime);
    //接口参数只有一个，不管接口参数名是什么，这个时候#{xxx}没有限制，可以是0，param1，也可以是aaa,bbb。可以不加注解。
    // 当接口参数大于一个的时候，mybatis的参数集就是上边说的默认值[0, 1, param1, param2]，如果你不用默认值，就需要加上@Param注解起别名。一旦加了注解
    Map<String,Object> getEvaluationInitData(String username);

    Map<String,Object> getLatestEvaluationInitData();

    int save4ArgsEvaluationInitData(@Param("username") String username,
                                    @Param("email") String email,
                                    @Param("evaluationName") String evaluationName,
                                    @Param("evaluationRemark") String evaluationRemark,
                                    @Param("createtime") Timestamp createtime,
                                    @Param("updatetime") Timestamp updatetime);
}
