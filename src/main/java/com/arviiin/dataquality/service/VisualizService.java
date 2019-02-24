package com.arviiin.dataquality.service;

import java.util.List;

public interface VisualizService {

    /**
     * 获取最近七天的日期
     * @return
     */
    List<String> getCategories();

    /**
     * 获取最近七天的数据
     * @return
     */
    List<Float> getDataStatistics();

    List<Float> getDataStatisticsFromRedis();
}
