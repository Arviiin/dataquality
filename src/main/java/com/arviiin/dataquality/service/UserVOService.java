package com.arviiin.dataquality.service;


import com.arviiin.dataquality.mapper.RolesMapper;
import com.arviiin.dataquality.mapper.UserVOMapper;
import com.arviiin.dataquality.model.Role;
import com.arviiin.dataquality.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
//@Transactional
public class UserVOService{
    @Autowired
    private UserVOMapper userMapper;
    @Autowired
    private RolesMapper rolesMapper;

    public UserVO loadUserByUsername(String s)  {
        UserVO user = userMapper.loadUserByUsername(s);
        if (user == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new UserVO();
        }
        //查询用户的角色信息，并返回存入user中
        List<Role> roles = rolesMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }

    /**
     * @param user
     * @return 0表示成功
     * 1表示用户名重复
     * 2表示失败
     */
    public int reg(UserVO user) {
        UserVO loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setEnabled(true);//用户可用
        long result = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, Integer.parseInt(user.getId()+""));
        boolean b = i == roles.length && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }

    public List<UserVO> getUserByNickname(String nickname) {
        List<UserVO> list = userMapper.getUserByNickname(nickname);
        return list;
    }

    public List<Role> getAllRole() {
        return userMapper.getAllRole();
    }

    public int updateUserEnabled(Boolean enabled, Long uid) {
        return userMapper.updateUserEnabled(enabled, uid);
    }

    public int deleteUserById(Long uid) {
        return userMapper.deleteUserById(uid);
    }

    public int updateUserRoles(Long[] rids, Long id) {
        int i = userMapper.deleteUserRolesByUid(id);//采用先全部删除，再添加角色的方法
        return userMapper.setUserRoles(rids, id);
    }

    public UserVO getUserById(Long id) {
        return userMapper.getUserById(id);
    }
}
