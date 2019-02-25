package com.arviiin.dataquality.service;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/2/25
 * @Version 1.0.0
 */

public interface DataListService {

    List<Map<String,Object>> getDataListByTableNameWithMap(String tablename);

    List<String> getAllTableNameByDbName(String dbname);
}
