package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionMapper;
import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.service.DimensionService;
import com.arviiin.dataquality.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DimensionServiceImpl implements DimensionService {

    @Autowired
    private DimensionMapper dimensionMapper;

    @Override
    public Integer getExpectedTotalRecordAmount(DimensionBean dimensionBean) {
        return dimensionMapper.getExpectedTotalRecordAmount(dimensionBean);
    }

    @Override
    public Integer getTotalRecordAmount(DimensionBean dimensionBean) {
        return dimensionMapper.getTotalRecordAmount(dimensionBean.getTablename());
    }

    @Override
    public Integer getDataFileCompletenessResult(DimensionBean dimensionBean) {
        return dimensionMapper.getDataFileCompletenessResult(dimensionBean);
    }

    @Override
    public Integer getDataValueCompletenessResult(DimensionBean dimensionBean) {
        return dimensionMapper.getDataValueCompletenessResult(dimensionBean);
    }

    /**
     *     "columnname": "rid:id",
     *     "dimensionname": "引用一致性",
     *     "rule": "string",
     *     "tablename": "roles_user:roles"
     * @param dimensionBean
     * @return
     */
    @Override
    public Integer getReferentialConsistencyResult(DimensionBean dimensionBean) {
        String[] split = dimensionBean.getTablename().split(":");
        String referenceTable = split[0];//引用表
        String beReferenceTable = split[1];//被引用表

        String[] split1 = dimensionBean.getColumnname().split(":");
        String referenceFiled = split1[0];//引用字段
        String beReferenceFiled = split1[1];//被引用字段
        /*
        SELECT count(*)  FROM roles_user
        WHERE rid not in (SELECT id FROM roles)
        */
        return dimensionMapper.getReferentialConsistencyResult(referenceTable, referenceFiled,beReferenceFiled,beReferenceTable);
    }

    @Override
    public Integer getFormatConsistencyResult(DimensionBean dimensionBean) {
        List<String> formatConsistencyResult = dimensionMapper.getFormatConsistencyResult(dimensionBean);
        //这里我先判断邮箱规则
        int cnt = 0;
        switch (dimensionBean.getRule()){
            case "邮箱规则":
                for (String s : formatConsistencyResult) {
                    if (CommonUtils.isEmail(s))
                        cnt++;
                }
                break;
            default:
                break;
        }
        return cnt;
    }

    @Override
    public Integer getDataRecordComplianceResult(DimensionBean dimensionBean) {
        List<String> dataRecordComplianceResult = dimensionMapper.getDataRecordComplianceResult(dimensionBean);
        //家庭住址
        int cnt = 0;
        for (String s : dataRecordComplianceResult) {
            if (CommonUtils.isAddress(s))
                cnt++;
        }
        return cnt;
    }

    @Override
    public Integer getRangeAccuracyResult(DimensionBean dimensionBean) {
        String rule = dimensionBean.getRule();
        String[] split = rule.split(":");
        String begin = split[0];
        String end = split[1];
        return dimensionMapper.getRangeAccuracyResult(dimensionBean, begin, end);
    }

    @Override
    public Integer getRecordUniquenessResult(DimensionBean dimensionBean) {
        return dimensionMapper.getRecordUniquenessResult(dimensionBean);
    }

    @Override
    public Integer getTimeBasedTimelinessResult(DimensionBean dimensionBean) {
        String rule = dimensionBean.getRule();
        String[] split = rule.split(":");
        String beginTime = split[0];
        String endTime = split[1];
        return dimensionMapper.getTimeBasedTimelinessResult(dimensionBean, beginTime, endTime);
    }

    @Override
    public Double getDataNonVulnerabilityResult(DimensionBean dimensionBean) {
        String type = dimensionBean.getRule();
        double dataNonVulnerability;
        if ("明文".equals(type))
            dataNonVulnerability = 0.5;
        else if ("加盐".equals(type))
            dataNonVulnerability = 0.7;
        else if ("MD5等类型".equals(type))
            dataNonVulnerability = 0.8;
        else if ("组合复杂".equals(type))
            dataNonVulnerability = 0.9;
        else
            dataNonVulnerability = 0.6;
        return dataNonVulnerability;
    }
}
