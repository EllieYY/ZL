package com.sk.zl.service;

import com.sk.zl.entity.User;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/15
 */

public interface UserService {
    int addUser(User user);

    List<User> getAllUser();

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(User user);

    int deleteUserById(Integer id);
}
