package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.model.User;
import com.arviiin.dataquality.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录注册Controller
 */
@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class LoginRegController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/login_error")
    public ModelMap loginError() {
        return new ModelMap("error", "登录失败!");
    }

    @RequestMapping("/login_success")
    public ModelMap loginSuccess() {
        return new ModelMap("success", "登录成功!");
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     * <p>
     * 如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面
     *
     * @return
     */
    @RequestMapping("/login_page")
    public ModelMap loginPage() {
        return new ModelMap("error", "尚未登录，请登录!");
    }

    /**
     *
     *      0表示成功
     *      1表示用户名重复
     *      2表示失败
     * @param user
     * @param response
     * @return
     */
    @PostMapping("/reg")
    public JsonResult reg(@RequestBody User user, HttpServletResponse response) {//@RequestBody 用于将传入的参数转成bean

        Map<String, String> regMap = userService.register(user);
        if(regMap.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", regMap.get("ticket"));
            cookie.setPath("/");//将ticket下发到客户端
            response.addCookie(cookie);
            /*if(!StringUtils.isEmpty(next) && !"NO".equals(next)){
                return "redirect:"+next;
            }
            return "redirect:/";//跳到首页*/
        }/*else{
            map.put("msg", regMap.get("msg"));
            return "login.ftl";
        }*/
        //int result = userService.reg(user);
        int result =  Integer.parseInt(regMap.get("code"));
        if (result == 0) {
            //成功
            return new JsonResult("success", "注册成功!");
        } else if (result == 1) {
            return new JsonResult("error", "用户名重复，注册失败!");
        } else {
            //失败
            return new JsonResult("error", "注册失败!");
        }
    }

    /**
     * 登录Controller ，
     * @param username
     * @param password
     * @return
     */
    //@RequestMapping(path = {"/login/"} ,method = {RequestMethod.POST})
    //不管是地址栏里的参数，还是表单里面的参数都可以用@RequestParam取得
    @GetMapping("/login/{username}/{password}")
    //public String login(@RequestParam("username") String username, @RequestParam("password") String password){
    public String login(@PathVariable String username, @PathVariable String password,HttpServletResponse response){

        //0成功  1用户名不存在  2密码错误
        Map<String, String> regMap = userService.login(username, password);
        if(regMap.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", regMap.get("ticket"));
            cookie.setPath("/");//将ticket下发到客户端
            response.addCookie(cookie);
            /*if(!StringUtils.isEmpty(next) && !"NO".equals(next)){//踩坑记录：因为没加！调试几个小时
                return "redirect:"+next;
            }
            return "redirect:/";//跳到首页*/
        }/*else{
            map.put("msg", regMap.get("msg"));
            return "login.ftl";
        }*/
//        int loginResultCode = userService.login(username, password);
//        return loginResultCode+"";
        return regMap.get("code");
    }
}