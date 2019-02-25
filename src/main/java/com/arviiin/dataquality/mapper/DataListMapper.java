package com.arviiin.dataquality.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataListMapper {

    List<Map<String,Object>> getDataListByTableNameWithMap(@Param("tablename") String tablename);

    List<String> getAllTableNameByDbName(@Param("dbname") String dbname);

}
