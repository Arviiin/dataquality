package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserById(Integer id);

    User getUserByName(String username);

    List<User> getUserList();

    int add(User user);

    int update(Integer id, User user);

    int delete(Integer id);

    int reg(User user);

    Map<String, String>  register(User user);

    //public int login(String username, String password);
    Map<String, String> login(String username, String password);


    int updatePassword(String username, String password, String newPassword);

    int updateProfile(String username, String company, String email, String telephone);
}
