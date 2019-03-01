package com.arviiin.dataquality.model;

public enum DimensionTypeEnum {

    //枚举类的实例对象必须在最前面先定义，而且必须每个实例对象都必须维护上chinese成员变量
    DATA_FILE_COMPLETENESS("数据文件完备性"),

    DATA_VALUE_COMPLETENESS("数据值完备性"),

    REFERENTIAL_CONSISTENCY("数据引用一致性"),

    FORMAT_CONSISTENCY("数据格式一致性"),

    DATA_RECORD_COMPLIANCE("数据记录依从性"),

    RANGE_ACCURACY("数据范围准确性"),

    RECORD_UNIQUENESS("数据记录唯一性"),

    TIME_BASED_TIMELINESS("基于时间段的时效性"),

    DATA_NON_VULNERABILITY("数据非脆弱性");

    /*SPRING("春天"),
    SUMMER("夏天"),
    AUMUTN("秋天"),
    WINTER("冬天");*/

    String name;

    //枚举类型的构造函数默认为private，因为枚举类型的初始化要在当前枚举类中完成。
    DimensionTypeEnum (String name){
        this.name= name;
    }

    public String getName(){
        return name;
    }

}
