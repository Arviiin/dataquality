package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.RespBean;
import com.arviiin.dataquality.model.User;
import com.arviiin.dataquality.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录注册Controller
 */
@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class LoginRegController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login_error")
    public RespBean loginError() {
        return new RespBean("error", "登录失败!");
    }

    @RequestMapping("/login_success")
    public RespBean loginSuccess() {
        return new RespBean("success", "登录成功!");
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     * <p>
     * 如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面
     *
     * @return
     */
    @RequestMapping("/login_page")
    public RespBean loginPage() {
        return new RespBean("error", "尚未登录，请登录!");
    }

    @PostMapping("/reg")
    public RespBean reg(@RequestBody User user) {//@RequestBody 用于将传入的参数转成bean
        int result = userService.reg(user);
        if (result == 0) {
            //成功
            return new RespBean("success", "注册成功!");
        } else if (result == 1) {
            return new RespBean("error", "用户名重复，注册失败!");
        } else {
            //失败
            return new RespBean("error", "注册失败!");
        }
    }

    /**
     * 登录Controller ，
     * @param username
     * @param password
     * @return
     */
    //@RequestMapping(path = {"/login/"} ,method = {RequestMethod.POST})
    @GetMapping("/login/{username}/{password}")
    //public String login(@RequestParam("username") String username, @RequestParam("password") String password){
    public String login(@PathVariable String username, @PathVariable String password){
            //0成功  1用户名不存在  2密码错误
            int loginResultCode = userService.login(username, password);
            return loginResultCode+"";
    }
}