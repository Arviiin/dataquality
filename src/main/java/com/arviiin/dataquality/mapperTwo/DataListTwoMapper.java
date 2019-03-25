package com.arviiin.dataquality.mapperTwo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DataListTwoMapper {

    List<Map<String,Object>> getDataListByTableNameWithMap(@Param("tablename") String tablename);

    List<String> getAllTableNameByDbName(@Param("dbname") String dbname);

}
