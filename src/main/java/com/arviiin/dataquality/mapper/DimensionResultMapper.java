package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

// @Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中   @MapperScan("com.sstl.sharebike.mapper")
@Mapper
public interface DimensionResultMapper {

    DimensionResultBean getDimensionResultData();

    DimensionDetailResultBean getDimensionDetailResultData();

    //不加@Param("dimensionScore") 就会报错 apache.ibatis.reflection.ReflectionException: There is no getter for property named 'dimensionScore' in 'class com.arviiin.dataquality.model.DimensionScore'
    Integer saveDimensionScore(@Param("dimensionScore") DimensionScore dimensionScore);

    DimensionScore getDimensionScore();
}
