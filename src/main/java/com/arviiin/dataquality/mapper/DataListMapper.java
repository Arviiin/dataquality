package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataListMapper {


    List<UserPojo> getDataListByTableName(@Param("tablename") String tablename);
}
