package com.arviiin.dataquality.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
* @Author: jlzhuang
* @Date:
* @Description: 
* @Version 1.0.0
*/
@Deprecated//弃用
public interface DataListMapper {

    List<Map<String,Object>> getDataListByTableNameWithMap(@Param("tablename") String tablename);

    List<String> getAllTableNameByDbName(@Param("dbname") String dbname);

}
