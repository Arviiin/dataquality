package com.arviiin.dataquality.service;

import java.util.List;

public interface VisualizService {

    List<String> getCategories();

    @Deprecated
    List<Float> getDataStatistics();

    /**
     * 九特性版本
     * @return
     */
    List<Float> getDetailDataStatistics();
    //List<Float> getSevenDetailDataStatistics();
    List<String> getSevenDetailDataStatistics();

    /**
     * 九特性版本
     * @return
     */
    List<Float> getDataStatisticsFromRedis();
    //List<Float> getSevenDataStatisticsFromRedis();
    List<String> getSevenDataStatisticsFromRedis();
}
