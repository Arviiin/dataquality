package com.arviiin.dataquality.service;

import com.arviiin.dataquality.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Integer id);

    public List<User> getUserList();

    public int add(User user);

    public int update(Integer id, User user);

    public int delete(Integer id);

    public int reg(User user);


    public int login(String username, String password);

}
