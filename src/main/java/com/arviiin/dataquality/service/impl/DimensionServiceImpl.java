package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.DimensionMapper;
import com.arviiin.dataquality.mapper.RedisMapper;
import com.arviiin.dataquality.mapperTwo.DimensionTwoMapper;
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
    private DimensionMapper dimensionOneMapper;

    @Autowired
    private DimensionTwoMapper dimensionTwoMapper;

    @Autowired
    private RedisMapper redisMapper;

    @Override
    public Integer getExpectedTotalRecordAmount(DimensionBean dimensionBean) {
        return dimensionTwoMapper.getExpectedTotalRecordAmount(dimensionBean);
    }

    /**
     * 所有质量维度指标的总数都是用这个方法计算
     */
    @Override
    public Integer getTotalRecordAmount(DimensionBean dimensionBean) {
        //这里暂时需要分两种情况，一种是当是引用的时候，这时候"tablename": "roles_user:roles   一种是其他，
        if (dimensionBean.getTablename().contains(":")){
            String[] split = dimensionBean.getTablename().split(":");
            String referenceTable = split[0];//引用表
            return dimensionTwoMapper.getTotalRecordAmount(referenceTable);
        }
        return dimensionTwoMapper.getTotalRecordAmount(dimensionBean.getTablename());
    }

    @Override
    public Integer getDataFileCompletenessResult(DimensionBean dimensionBean) {
        return dimensionTwoMapper.getDataFileCompletenessResult(dimensionBean);
    }

    @Override
    public Integer getDataValueCompletenessResult(DimensionBean dimensionBean) {
        return dimensionTwoMapper.getDataValueCompletenessResult(dimensionBean);
    }

    /**
     * "dimensionname": "引用一致性",
     * "rule": "string",
     * "tablename": "roles_user:roles"
     *
     * 若不是引用表中数据，而是自己定义的，
     * 如某一表字段只允许填：砖石会员、铂金会员、黄金会员、白银会员、青铜会员。
     * 此时规则里要填上相应引用字段以、分割
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
            return dimensionTwoMapper.getReferentialConsistencyResult(referenceTable, referenceFiled,beReferenceFiled,beReferenceTable);
        }else {
            //考虑到参照引用的可能不是表中的，而是几个固定字段，我们可以填写相应的字段范围
            String rule = dimensionBean.getRule();
            String[] split = rule.split("、");
            HashSet<String> strings = new HashSet<>();
            for (String s : split) {
                strings.add(s);
            }
            List<String> referentialConsistencyResultWithInput = dimensionTwoMapper.getReferentialConsistencyResultWithInput(dimensionBean);
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
        List<String> formatConsistencyResult = dimensionTwoMapper.getFormatConsistencyResult(dimensionBean);
        //这里我先判断邮箱规则
        int cnt = 0;
        switch (dimensionBean.getRule()){
            case "邮箱规则":
                Pattern emailPattern = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
                for (String s : formatConsistencyResult) {
                    if (CommonUtils.isEmail(s,emailPattern))
                        cnt++;
                }
                break;
            case "邮编规则":
                String reg = "[1-9]\\d{5}";
                Pattern postCodePattern = Pattern.compile(reg);
                for (String s : formatConsistencyResult) {
                    if (CommonUtils.isPostCode(s,postCodePattern))
                        cnt++;
                }
                break;
            case "电话规则":
                String regex = "(\\+\\d+)?1[3458]\\d{9}$";
                Pattern pattern = Pattern.compile(regex);
                for (String s : formatConsistencyResult) {
                    if (CommonUtils.isMobile(s,pattern))
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
        List<String> dataRecordComplianceResult = dimensionTwoMapper.getDataRecordComplianceResult(dimensionBean);
        //家庭住址
        String regex = ".*(省|自治区|上海|北京|天津|重庆).*(市|自治州).*(区|县|市|旗)(.*(镇|乡|街道))?";
        Pattern p = Pattern.compile(regex);//如果放在方法里，频繁的创建对象很可怕！占用太多资源
        int cnt = 0;
        for (String s : dataRecordComplianceResult) {
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
        return dimensionTwoMapper.getRangeAccuracyResult(dimensionBean, begin, end);
    }

    @Override
    public Integer getRecordUniquenessResult(DimensionBean dimensionBean) {
        return dimensionTwoMapper.getRecordUniquenessResult(dimensionBean);
    }

    @Override
    public Integer getTimeBasedTimelinessResult(DimensionBean dimensionBean) {
        String rule = dimensionBean.getRule();
        String[] split = rule.split(":");
        String beginTime = split[0];
        String endTime = split[1];                                          //左包含，右不包含
        return dimensionTwoMapper.getTimeBasedTimelinessResult(dimensionBean, beginTime, endTime);
    }

    /**
     * 注册时候
     * user.setSalt(UUID.randomUUID().toString().substring(0,5));
     * user.setPassword(WendaUtil.MD5(password+user.getSalt()));
     * 保密性 = 数据加密的强度 / 100。
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


    /*******************  分割线：下面开始是保存到数据库或者redis里面的 使用的是dimensionOneMapper  *******************/


    @Override
    public void saveDimensionResultData(DimensionResultBean dimensionResultBean) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dimensionResultBean.setCreatetime(timestamp);
        dimensionResultBean.setUpdatetime(timestamp);
        dimensionOneMapper.saveDimensionResultData(dimensionResultBean);
    }

    @Override
    public void saveDimensionResultDataToRedis(DimensionResultBean dimensionResultBean) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dimensionResultBean.setCreatetime(timestamp);
        dimensionResultBean.setUpdatetime(timestamp);
        redisMapper.saveDimensionResultDataToRedis(dimensionResultBean);
    }

    @Override
    public void saveDimensionDetailResultData(DimensionDetailResultBean dimensionDetailResultBean) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dimensionDetailResultBean.setCreatetime(timestamp);
        dimensionDetailResultBean.setUpdatetime(timestamp);
        Integer integer = dimensionOneMapper.saveDimensionDetailResultData(dimensionDetailResultBean);
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
