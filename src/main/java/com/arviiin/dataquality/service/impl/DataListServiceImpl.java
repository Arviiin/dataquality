package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapperTwo.DataListTwoMapper;
import com.arviiin.dataquality.service.DataListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @Author: jlzhuang
* @Date:
* @Description: 使用第二个数据源
* @Version 1.0.0
*/
@Service
public class DataListServiceImpl implements DataListService {
    @Autowired
    private DataListTwoMapper dataListTwoMapper;

    @Override
    public List<Map<String, Object>> getDataListByTableNameWithMap(String tablename) {
        return dataListTwoMapper.getDataListByTableNameWithMap(tablename);
    }

    @Override
    public List<String> getAllTableNameByDbName(String dbname) {
        return dataListTwoMapper.getAllTableNameByDbName(dbname);
    }
}
