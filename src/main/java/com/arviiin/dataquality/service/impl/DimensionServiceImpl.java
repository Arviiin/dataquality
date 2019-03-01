package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionMapper;
import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.model.DimensionBean;
import com.arviiin.dataquality.model.DimensionDetailResultBean;
import com.arviiin.dataquality.model.DimensionResultBean;
import com.arviiin.dataquality.service.DimensionService;
import com.arviiin.dataquality.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DimensionServiceImpl implements DimensionService {

    private static Logger logger = LoggerFactory.getLogger(DimensionServiceImpl.class);

    @Autowired
    private DimensionMapper dimensionMapper;

    @Autowired
    private RedisMapper redisMapper;

    @Override
    public Integer getExpectedTotalRecordAmount(DimensionBean dimensionBean) {
        return dimensionMapper.getExpectedTotalRecordAmount(dimensionBean);
    }

    @Override
    public Integer getTotalRecordAmount(DimensionBean dimensionBean) {
        //这里暂时需要分两种情况，一种是当是引用的时候，这时候"tablename": "roles_user:roles   一种是其他，
        if (dimensionBean.getTablename().contains(":")){
            String[] split = dimensionBean.getTablename().split(":");
            String referenceTable = split[0];//引用表
            return dimensionMapper.getTotalRecordAmount(referenceTable);
        }
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
     *
     *     若不是引用表中数据，而是自己定义的，
     * 		如，一表字段只允许填铂金会员，黄金会员，
     * 		白银会员。此时规则里要填上相应引用字段以、分割
     * @param dimensionBean
     * @return
     */
    @Override
    public Integer getReferentialConsistencyResult(DimensionBean dimensionBean) {
        if (dimensionBean.getTablename().contains(":") && dimensionBean.getColumnname().contains(":")){
            /*
                SELECT count(*)  FROM roles_user
                WHERE rid not in (SELECT id FROM roles)
            */
            String[] split = dimensionBean.getTablename().split(":");
            String referenceTable = split[0];//引用表
            String beReferenceTable = split[1];//被引用表

            String[] split1 = dimensionBean.getColumnname().split(":");
            String referenceFiled = split1[0];//引用字段
            String beReferenceFiled = split1[1];//被引用字段
            return dimensionMapper.getReferentialConsistencyResult(referenceTable, referenceFiled,beReferenceFiled,beReferenceTable);
        }else {
            String rule = dimensionBean.getRule();
            String[] split = rule.split("、");
            HashSet<String> strings = new HashSet<>();
            for (String s : split) {
                strings.add(s);
            }
            List<String> referentialConsistencyResultWithInput = dimensionMapper.getReferentialConsistencyResultWithInput(dimensionBean);
            int cnt = 0;
            for (String s : referentialConsistencyResultWithInput) {
                if (strings.contains(s))
                    cnt++;
            }
            return cnt;
        }
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
            case "邮编规则":
                for (String s : formatConsistencyResult) {
                    if (CommonUtils.isPostCode(s))
                        cnt++;
                }
                break;
            case "电话规则":
                for (String s : formatConsistencyResult) {
                    if (CommonUtils.isMobile(s))
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
        String regex = ".*(省|自治区|上海|北京|天津|重庆).*(市|自治州).*(区|县|市|旗)(.*(镇|乡|街道))?";
        Pattern p = Pattern.compile(regex);//如果放在方法里，频繁的new对象很可怕！占用太多资源，甚至OOM
        int cnt = 0;
        for (String s : dataRecordComplianceResult) {
            //if (CommonUtils.isAddress(s))
            if (CommonUtils.isAddress(s,p))
                cnt++;
        }
        return cnt;
    }

    @Override
    public Integer getRangeAccuracyResult(DimensionBean dimensionBean) {
        String rule = dimensionBean.getRule();
        String[] split = rule.split(":");
        String begin = split[0];//开始范围
        String end = split[1];//结束范围
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

    /**
     * 注册时候
     * user.setSalt(UUID.randomUUID().toString().substring(0,5));
     * user.setPassword(WendaUtil.MD5(password+user.getSalt()));
     *
     * 登录时候
     * if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
     * @param dimensionBean
     * @return
     */
    @Override
    public Integer getDataNonVulnerabilityResult(DimensionBean dimensionBean) {
        String type = dimensionBean.getRule();
        int dataNonVulnerability;
        if ("明文".equals(type))
            dataNonVulnerability = 50;
        else if ("加盐".equals(type))
            dataNonVulnerability = 70;
        else if ("MD5等类型".equals(type))
            dataNonVulnerability = 80;
        else if ("组合复杂".equals(type))
            dataNonVulnerability = 90;
        else
            dataNonVulnerability = 60;
        return dataNonVulnerability;
    }

    @Override
    public void saveDimensionResultData(DimensionResultBean dimensionResultBean) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dimensionResultBean.setCreatetime(timestamp);
        dimensionMapper.saveDimensionResultData(dimensionResultBean);
    }

    @Override
    public void saveDimensionResultDataToRedis(DimensionResultBean dimensionResultBean) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dimensionResultBean.setCreatetime(timestamp);
        redisMapper.saveDimensionResultDataToRedis(dimensionResultBean);

    }

    @Override
    public void saveDimensionDetailResultData(DimensionDetailResultBean dimensionDetailResultBean) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dimensionDetailResultBean.setCreatetime(timestamp);
        dimensionDetailResultBean.setUpdatetime(timestamp);
        Integer integer = dimensionMapper.saveDimensionDetailResultData(dimensionDetailResultBean);
        if (integer <= 0){
            logger.error("维度信息未能正确传入mysql");
        }
    }

    @Override
    public void saveDimensionDetailResultDataToRedis(DimensionDetailResultBean dimensionDetailResultBean) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dimensionDetailResultBean.setCreatetime(timestamp);
        dimensionDetailResultBean.setUpdatetime(timestamp);
        redisMapper.saveDimensionDetailResultDataToRedis(dimensionDetailResultBean);
    }
}
