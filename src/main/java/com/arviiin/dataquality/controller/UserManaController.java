package com.arviiin.dataquality.controller;


import com.arviiin.dataquality.model.RespBean;
import com.arviiin.dataquality.model.Role;
import com.arviiin.dataquality.model.RolesIdDTO;
import com.arviiin.dataquality.model.UserVO;
import com.arviiin.dataquality.service.UserVOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    private UserVOService userService;

    /**
     * 这玩意实际上是有两个作用nickname!=null and nickname!=''时候，起搜索的作用，反之则取前二十个用户
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<UserVO> getUserByNickname(@RequestParam("nickname") String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserVO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getAllRole() {
        return userService.getAllRole();
    }

    @RequestMapping(value = "/user/enabled", method = RequestMethod.POST)
    public RespBean updateUserEnabled(Boolean enabled, Long uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return new RespBean("success", "更新成功!");
        } else {
            return new RespBean("error", "更新失败!");
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public RespBean deleteUserById(@RequestParam("id") Long uid) {
        System.out.println(uid);
        int res = userService.deleteUserById(uid);
        if ( res == 1) {
            return new RespBean("success", "删除成功!");
        } else {
            return new RespBean("error", "删除失败!");
        }
    }

    /**
     * 用户权限更改
     * @return
     */
    @RequestMapping(value = "/user/role", method = RequestMethod.POST)
    public RespBean updateUserRoles(@RequestBody RolesIdDTO rolesIdDTO) {

        Long id = rolesIdDTO.getId();
        List<Long> ridsList = rolesIdDTO.getRids();
        Long[] rids = ridsList.toArray(new Long[0]);
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return new RespBean("success", "更新成功!");
        } else {
            return new RespBean("error", "更新失败!");
        }
    }
}
