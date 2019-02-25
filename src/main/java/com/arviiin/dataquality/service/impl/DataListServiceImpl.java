package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DataListMapper;
import com.arviiin.dataquality.service.DataListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataListServiceImpl implements DataListService {
    @Autowired
    private DataListMapper dataListMapper;

    @Override
    public List<Map<String, Object>> getDataListByTableNameWithMap(String tablename) {
        return dataListMapper.getDataListByTableNameWithMap(tablename);
    }

    @Override
    public List<String> getAllTableNameByDbName(String dbname) {
        return dataListMapper.getAllTableNameByDbName(dbname);
    }
}
