package com.arviiin.dataquality.service;

import java.util.List;

public interface VisualizService {

    List<String> getCategories();

    List<Float> getDataStatistics();

    List<Float> getDetailDataStatistics();

    List<Float> getDataStatisticsFromRedis();
}
