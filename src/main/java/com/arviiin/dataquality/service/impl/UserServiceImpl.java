package com.arviiin.dataquality.service.impl;

import com.arviiin.dataquality.mapper.LoginTicketMapper;
import com.arviiin.dataquality.mapper.RolesMapper;
import com.arviiin.dataquality.mapper.UserMapper;
import com.arviiin.dataquality.model.LoginTicketBean;
import com.arviiin.dataquality.model.User;
import com.arviiin.dataquality.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RolesMapper rolesMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setCreatetime(timestamp);
        user.setUpdatetime(timestamp);
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



    /**
     * @return
     * 0表示成功
     * 1表示用户名重复
     * 2表示失败
     */
    public Map<String, String>  register(User user){
        Map<String, String> map = new HashMap<String, String>();
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            map.put("msg", "用户名已经被注册");
            map.put("code", "1");
            return map;
            //return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setEnabled(true);//用户可用
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setCreatetime(timestamp);
        user.setUpdatetime(timestamp);
        long result = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, user.getId());
        boolean b = i == roles.length && result == 1;
        if (b) {
//            return 0;
            map.put("code", "0");
            String ticket = addLoginTicket(userMapper.loadUserByUsername(user.getUsername()).getId());//刚创建一个user就拿出来
            map.put("ticket",ticket);
            return map;
        } else {
//            return 2;
            map.put("code", "2");
            return map;
        }

    }

    //登陆
    //0成功  1用户名不存在  2密码错误
    public Map<String, String> login(String username, String password){
        Map<String, String> map = new HashMap<String, String>();
        //判断用户名是否存在
        User user = userMapper.loadUserByUsername(username);
        if(user == null){
//            respBean.setMsg("msg", "用户名不存在");
            map.put("msg", "用户名不存在");
            map.put("code", "1");
            //return 1;
            return map;
        }
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            //return 2;
            map.put("msg","密码错误");
            map.put("code", "2");
            return map;
        }

        //return 0;
        map.put("code", "0");
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicketBean loginTicket = new LoginTicketBean();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600*24*100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketMapper.addTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
