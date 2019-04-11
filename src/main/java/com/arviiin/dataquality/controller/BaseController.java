package com.arviiin.dataquality.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

/**
 * Controller的基类，所有Controller都应该继承该类
 */
public abstract class BaseController{
	public final static String SUCCESS_CODE = "0";
	public final  static String  FAIL_CODE = "1";
	public final  static String  FAIL_STRING = "fail";
	public final  static String  OK = "ok";
	public final  static String  ERROR_STRING = "error";
	public final static String SUCCESS_STRING = "success";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ModelMap ModelMapResponse(String status,Object data,String msg){
		ModelMap modelMap=new ModelMap();
		modelMap.put("status", status);//private HttpStatus status;
		modelMap.put("data", data);
		modelMap.put("msg", msg);
		return modelMap;
	}

}