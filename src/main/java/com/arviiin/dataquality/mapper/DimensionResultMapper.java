package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.model.DimensionScore;
import org.apache.ibatis.annotations.Param;

public interface DimensionResultMapper {

    DimensionResultBean getDimensionResultData();

    DimensionDetailResultBean getDimensionDetailResultData();

    //不加@Param("dimensionScore") 就会报错 apache.ibatis.reflection.ReflectionException: There is no getter for property named 'dimensionScore' in 'class com.arviiin.dataquality.model.DimensionScore'
    Integer saveDimensionScore(@Param("dimensionScore") DimensionScore dimensionScore);

    DimensionScore getDimensionScore();
}
