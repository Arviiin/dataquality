package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DataListMapper;
import com.arviiin.dataquality.model.UserPojo;
import com.arviiin.dataquality.service.DataListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataListServiceImpl implements DataListService {
    @Autowired
    private DataListMapper dataListMapper;

    @Override
    public List<UserPojo> getDataListByTableName(String tablename) {
        System.out.println(tablename);
        List<UserPojo> dataListByTableName = dataListMapper.getDataListByTableName(tablename);
        return dataListByTableName;
    }
}
