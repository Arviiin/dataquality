package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.UserPojo;

import java.util.List;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/2/25
 * @Version 1.0.0
 */

public interface DataListService {
    List<UserPojo> getDataListByTableName(String tablename);
}
