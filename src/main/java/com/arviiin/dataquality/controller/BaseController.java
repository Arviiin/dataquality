package com.arviiin.dataquality.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

public abstract class BaseController{
	public final static String SUCCESS="1";
	public final  static String  FAIL="0";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ModelMap ModelMapResponse(String status,Object data,String msg){
		ModelMap modelMap=new ModelMap();
		modelMap.put("status", status);//private HttpStatus status;
		modelMap.put("data", data);
		modelMap.put("msg", msg);
		return modelMap;
	}

}