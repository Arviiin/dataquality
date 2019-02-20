package com.arviiin.dataquality.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /*public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }*/

    /**
     * 检测邮箱地址是否合法
     *
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
//        Pattern.matches();//看源码知道，多次调用会重复调用compile
        /*public static boolean matches(String regex, CharSequence input) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(input);
            return m.matches();
        }*/
        //"ddd".matches()//同样适用string的matches也会重复调用
       /* public boolean matches(String regex) {
            return Pattern.matches(regex, this);
        }*/
        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
//        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /*String regEx="[^省]+省[^市]+市+[^区]+区";  
    Pattern p = Pattern.compile(regEx);   
    Matcher m = p.matcher(city);
    while(m.find()){
        System.out.println(m.group());
    }*/

    /**
     * 判断地址是否包含关键的省市区
     * String regex2="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
     * String regex1="(?<province>[^省]+省|.+自治区)(?<city>[^自治州]+自治州|[^市]+市|[^盟]+盟|[^地区]+地区|.+区划)(?<county>[^市]+市|[^县]+县|[^旗]+旗|.+区)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
     * String regEx="[^省]+省[^市]+市+[^区]+区";
     * String reg = "(.*省)?.*市.*区";//(.*省)?.*市.*(区|县)   ?代表可有可无
     * String regex="((?<province>[^省]+省|.+自治区)|上海|北京|天津|重庆)(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区|.+市|.+镇)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
     * @param address
     * @return
     */
    public static boolean isAddress(String address){
        if (null == address || "".equals(address)) return false;
        String regex = ".*(省|自治区|上海|北京|天津|重庆).*(市|自治州).*(区|县|市|旗)(.*(镇|乡|街道))?";
        Pattern p = Pattern.compile(regex);
        Matcher m=p.matcher(address);
        return m.find();
        //return m.matches();
    }

}