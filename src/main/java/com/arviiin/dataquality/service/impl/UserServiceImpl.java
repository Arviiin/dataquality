package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.RolesMapper;
import com.arviiin.dataquality.mapper.UserMapper;
import com.arviiin.dataquality.model.User;
import com.arviiin.dataquality.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public int update(Integer id, User user) {
        return userMapper.update(id, user);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.delete(id);
    }


    /**
     * @param user
     * @return 0表示成功
     * 1表示用户名重复
     * 2表示失败
     */
    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setEnabled(true);//用户可用
        long result = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, user.getId());
        boolean b = i == roles.length && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }



    //登陆
    //0成功  1用户名不存在  2密码错误
    public int login(String username, String password){
        //判断用户名是否存在
        User user = userMapper.loadUserByUsername(username);
        if(user == null){
//            respBean.setMsg("msg", "用户名不存在");
            return 1;
        }
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
//            map.put("msg","密码错误");
            return 2;
        }

        return 0;
    }
}
