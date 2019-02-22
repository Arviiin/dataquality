package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.config.RedisConstants;
import com.arviiin.dataquality.config.StateParameter;
import com.arviiin.dataquality.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 
/**
 * @ClassName: RedisController
 */
@Controller
@RequestMapping(value="/redis")
public class RedisController extends BaseController {

    @Autowired
    RedisUtil redisUtil;
 
    /**
     * @return: org.springframework.ui.ModelMap
     * @Description: 执行redis写/读/生命周期
     */
    @RequestMapping(value = "getRedis",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getRedis(){
        redisUtil.set("20182018","这是一条测试数据", RedisConstants.datebase1);
        Long resExpire = redisUtil.expire("20182018", 60, RedisConstants.datebase1);//设置key过期时间
        logger.info("resExpire="+resExpire);
        String res = redisUtil.get("20182018", RedisConstants.datebase1);
        return getModelMap(StateParameter.SUCCESS, res, "执行成功");
    }
 
}
