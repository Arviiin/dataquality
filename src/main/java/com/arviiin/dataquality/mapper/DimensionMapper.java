package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//大部分弃用
public interface DimensionMapper {

    Integer saveDimensionDetailResultData(@Param("dimensionDetailResultBean") DimensionDetailResultBean dimensionDetailResultBean);

    @Deprecated
    void saveDimensionResultData(@Param("dimensionResultBean") DimensionResultBean dimensionResultBean);
    @Deprecated
    Integer getExpectedTotalRecordAmount(@Param("dimensionBean") DimensionBean dimensionBean);
    @Deprecated
    Integer getTotalRecordAmount(@Param("tablename") String tablename);//@Select("SELECT Count(*) FROM ${tablename}") 注意#{}和${}的区别
    @Deprecated
    Integer getDataFileCompletenessResult(@Param("dimensionBean") DimensionBean dimensionBean);
    @Deprecated
    Integer getDataValueCompletenessResult(@Param("dimensionBean") DimensionBean dimensionBean);
    @Deprecated
    Integer getReferentialConsistencyResult(@Param("referenceTable") String referenceTable,
                                            @Param("referenceFiled") String referenceFiled,
                                            @Param("beReferenceFiled") String beReferenceFiled,
                                            @Param("beReferenceTable") String beReferenceTable);
    @Deprecated
    List<String> getReferentialConsistencyResultWithInput(@Param("dimensionBean") DimensionBean dimensionBean);
    @Deprecated
    List<String> getFormatConsistencyResult(@Param("dimensionBean") DimensionBean dimensionBean);
    @Deprecated
    List<String> getDataRecordComplianceResult(@Param("dimensionBean") DimensionBean dimensionBean);
    @Deprecated
    Integer getRangeAccuracyResult(@Param("dimensionBean") DimensionBean dimensionBean,
                                   @Param("begin") String begin,
                                   @Param("end")String end);
    @Deprecated
    Integer getRecordUniquenessResult(@Param("dimensionBean") DimensionBean dimensionBean);
    @Deprecated
    Integer getTimeBasedTimelinessResult(@Param("dimensionBean") DimensionBean dimensionBean,
                                         @Param("beginTime") String beginTime,
                                         @Param("endTime")String endTime);

}
