package com.arviiin.dataquality.mapper;

import com.arviiin.dataquality.model.WeightBean;
import org.apache.ibatis.annotations.Mapper;

// @Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中   @MapperScan("com.sstl.sharebike.mapper")
@Mapper
public interface WeightMapper {

    WeightBean getDefaultWeightResult();

    //这里打算去redis里面拿指标的得分，然后进行和权重进行计算。


}
