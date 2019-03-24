package com.arviiin.dataquality.Mapper2;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface EmpTestMapper {
  @Select("select * from pay_detail")
  List<Map<String,String>> getAllEmp ();
}