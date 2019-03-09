package com.arviiin.dataquality.controller;

import com.arviiin.dataquality.model.JsonResult;
import com.arviiin.dataquality.model.User;
import com.arviiin.dataquality.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserById (@PathVariable(value = "id") Integer id){
        JsonResult r = new JsonResult();
        try {
            User user = userService.getUserById(id);
            r.setResult(user);
            r.setStatus(OK);
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 查询用户列表
     * @return
     */
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ResponseEntity<JsonResult> getUserList (){
        JsonResult r = new JsonResult();
        try {
            List<User> users = userService.getUserList();
            r.setResult(users);
            r.setStatus(OK);
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add (@RequestBody User user){
        JsonResult r = new JsonResult();
        try {
            int orderId = userService.add(user);
            if (orderId < 0) {
                r.setResult(orderId);
                r.setStatus(FAIL_STRING);
            } else {
                r.setResult(orderId);
                r.setStatus(OK);
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Integer id){
        JsonResult r = new JsonResult();
        try {
            int ret = userService.delete(id);
            if (ret < 0) {
                r.setResult(ret);
                r.setStatus(FAIL_STRING);
            } else {
                r.setResult(ret);
                r.setStatus(OK);
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update (@PathVariable("id") Integer id, @RequestBody User user){
        JsonResult r = new JsonResult();
        try {
            int ret = userService.update(id, user);
            if (ret < 0) {
                r.setResult(ret);
                r.setStatus(FAIL_STRING);
            } else {
                r.setResult(ret);
                r.setStatus(OK);
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);

            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }


    /**
     * 根据用户名修改用户密码
     * @param
     * @return
     * 0表示成功
     * 1表示原密码不正确
     * 2 失败
     */
    @RequestMapping(value = "user/update_password", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> updatePassword (@RequestParam("username") String username,
                                                      @RequestParam("password") String password,
                                                      @RequestParam(value = "new_password" ,required = true,defaultValue = "NO") String newPassword){
        JsonResult r = new JsonResult();//不管是地址栏里的参数，还是表单里面的参数都可以用@RequestParam取得
        /*if(new_password!=null && !"NO".equals(new_password)){//太诡异了，required = false居然不行
        }*/
        try {
            int ret = userService.updatePassword(username,password,newPassword);
            if (ret == 2) {
                r.setResult(ret);
                r.setStatus(FAIL_STRING);
            } else if (ret == 0){
                r.setResult(ret);
                r.setStatus(OK);
            } else {
                r.setResult(ret);
                r.setStatus("password error");
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }


    /**
     * 根据用户名修改用户信息
     * @param
     * @return
     *   * 0表示成功
     *   * 1失败
     */
    @RequestMapping(value = "user/update_profile", method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> updateProfile (@RequestParam("username") String username,
                                                     @RequestParam("company") String company,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("telephone") String telephone){
        JsonResult r = new JsonResult();//不管是地址栏里的参数，还是表单里面的参数都可以用@RequestParam取得

        try {
            int ret = userService.updateProfile(username,company,email,telephone);
            if (ret == 1) {
                r.setResult(ret);
                r.setStatus(FAIL_STRING);
            } else if (ret == 0){
                r.setResult(ret);
                r.setStatus(OK);
            }
        } catch (Exception e) {
            r.setResult(e.getClass().getName() + ":" + e.getMessage());
            r.setStatus(ERROR_STRING);
            e.printStackTrace();
        }
        return ResponseEntity.ok(r);
    }
}
