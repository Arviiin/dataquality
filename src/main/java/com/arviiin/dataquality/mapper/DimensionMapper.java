package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.DimensionBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// @Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中   @MapperScan("com.sstl.sharebike.mapper")
@Mapper
public interface DimensionMapper {


    Integer getExpectedTotalRecordAmount(@Param("dimensionBean") DimensionBean dimensionBean);

    //@Select("SELECT Count(*) FROM ${tablename}") 注意#{}和${}的区别
    Integer getTotalRecordAmount(@Param("tablename") String tablename);

    Integer getDataFileCompletenessResult(@Param("dimensionBean") DimensionBean dimensionBean);

    Integer getDataValueCompletenessResult(@Param("dimensionBean") DimensionBean dimensionBean);

    Integer getReferentialConsistencyResult(@Param("referenceTable") String referenceTable,
                                            @Param("referenceFiled") String referenceFiled,
                                            @Param("beReferenceFiled") String beReferenceFiled,
                                            @Param("beReferenceTable") String beReferenceTable);

    List<String> getFormatConsistencyResult(@Param("dimensionBean") DimensionBean dimensionBean);

    List<String> getDataRecordComplianceResult(@Param("dimensionBean") DimensionBean dimensionBean);

    Integer getRangeAccuracyResult(@Param("dimensionBean") DimensionBean dimensionBean,
                                   @Param("begin") String begin,
                                   @Param("end")String end);

    Integer getRecordUniquenessResult(@Param("dimensionBean") DimensionBean dimensionBean);

    Integer getTimeBasedTimelinessResult(@Param("dimensionBean") DimensionBean dimensionBean,
                                         @Param("beginTime") String beginTime,
                                         @Param("endTime")String endTime);


}
