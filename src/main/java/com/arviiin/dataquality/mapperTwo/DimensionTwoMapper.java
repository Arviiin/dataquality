package com.arviiin.dataquality.mapperTwo;

import com.arviiin.dataquality.model.DimensionBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 最后均是取 满足条件/总的
 */
public interface DimensionTwoMapper {

    Integer getTotalRecordAmount(@Param("tablename") String tablename);

    Integer getExpectedTotalRecordAmount(@Param("dimensionBean") DimensionBean dimensionBean);

    //完备性 = 非空的数量 / 所有值的数量
    Integer getDataFileCompletenessResult(@Param("dimensionBean") DimensionBean dimensionBean);
    Integer getDataValueCompletenessResult(@Param("dimensionBean") DimensionBean dimensionBean);

    //一致性 = 一致性值的数量 / 所有值的数量
    Integer getReferentialConsistencyResult(@Param("referenceTable") String referenceTable,
                                            @Param("referenceFiled") String referenceFiled,
                                            @Param("beReferenceFiled") String beReferenceFiled,
                                            @Param("beReferenceTable") String beReferenceTable);
    /**
     * 考虑到参照引用的可能不是表中的，而是几个固定字段，我们可以填写相应的字段范围
     * 这时候就不能用上面的方法了。
     * @param dimensionBean
     * @return
     */
    List<String> getReferentialConsistencyResultWithInput(@Param("dimensionBean") DimensionBean dimensionBean);
    List<String> getFormatConsistencyResult(@Param("dimensionBean") DimensionBean dimensionBean);

    //依从性 = 符合依从性规则值的数量 / 所有值的数量
    List<String> getDataRecordComplianceResult(@Param("dimensionBean") DimensionBean dimensionBean);

    //准确性 = 正确值的数量 / 所有值的数量
    Integer getRangeAccuracyResult(@Param("dimensionBean") DimensionBean dimensionBean,
                                   @Param("begin") String begin,
                                   @Param("end") String end);

    //唯一性 = 1 - （重复值的数量 / 所有值的数量）  实际操作中还是直接取不重复的数量
    Integer getRecordUniquenessResult(@Param("dimensionBean") DimensionBean dimensionBean);

    //现实性 = 有效时间内值的数量 / 所有值的数量
    Integer getTimeBasedTimelinessResult(@Param("dimensionBean") DimensionBean dimensionBean,
                                         @Param("beginTime") String beginTime,
                                         @Param("endTime") String endTime);


    /*void saveDimensionResultData(@Param("dimensionResultBean") DimensionResultBean dimensionResultBean);


    Integer saveDimensionDetailResultData(@Param("dimensionDetailResultBean") DimensionDetailResultBean dimensionDetailResultBean);*/
}
