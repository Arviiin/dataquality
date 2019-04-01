package com.arviiin.dataquality.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /*public static boolean isEmail(String string) {
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    }*/
    /**
     * 检测邮箱地址是否合法
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
        //"ddd".matches()//上述同样适用string的matches也会重复调用
       /* public boolean matches(String regex) {
            return Pattern.matches(regex, this);
        }*/
        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
//        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isEmail(String email,Pattern p) {
        if (null == email || "".equals(email)) return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
//        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /**
     * 判断邮编是否合法
     * @param postCode
     * @return
     */
    public static boolean isPostCode(String postCode){
        if (null == postCode || "".equals(postCode)) return false;
        String reg = "[1-9]\\d{5}";
        Pattern p = Pattern.compile(reg);
        Matcher m=p.matcher(postCode);
        return m.matches();
    }
    public static boolean isPostCode(String postCode,Pattern p){
        if (null == postCode || "".equals(postCode)) return false;
        Matcher m=p.matcher(postCode);
        return m.matches();
    }


    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobile(String mobile){
        if (null == mobile || "".equals(mobile)) return false;
        String regex = "(\\+\\d+)?1[3458]\\d{9}$";
        return Pattern.matches(regex, mobile);
    }
    /*public static boolean matches(String regex, CharSequence input) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(input);
            return m.matches();
        }*/
    //慎用Pattern类的matches方法也即是上面这种调用方式，每次编译返回一个新的正则表达式，耗费资源
    //如果偶尔使用一次，可以直接使用，这里我们需要大量使用，因而在调用方法处编译，并把编译后的Pattern对象传进来
    public static boolean isMobile(String mobile,Pattern pattern){
        if (null == mobile || "".equals(mobile)) return false;
            Matcher m = pattern.matcher(mobile);
            return m.matches();
    }
    /**
     * 区号+座机号码+分机号码
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone){
        String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }

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
        //return m.matches();
        return m.find();
    }

    /**
     * 多次判断使用，效率高
     * @param address
     * @param p
     * @return
     */
    public static boolean isAddress(String address,Pattern p){
        if (null == address || "".equals(address)) return false;
        Matcher m = p.matcher(address);
//        return m.matches();//这个完全配置，太苛刻，
        return m.find();
    }

    public static String getUuid(){
        String uuid = UUID.randomUUID().toString(); //获取UUID并转化为String对象
        uuid = uuid.replace("-", "");
        return uuid;
    }

}