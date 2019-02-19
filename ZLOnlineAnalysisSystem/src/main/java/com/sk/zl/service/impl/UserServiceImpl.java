package com.sk.zl.service.impl;

import com.sk.zl.entity.User;
import com.sk.zl.dao.user.UserExample;
import com.sk.zl.dao.user.UserMapper;
import com.sk.zl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> getAllUser() {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIsNotNull();
        List<User> list = userMapper.selectByExample(example);
        return list;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIsNotNull();

        return userMapper.updateByExampleSelective(user, example);
    }

    @Override
    public int deleteUser(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(user.getUserId());

        return userMapper.deleteByExample(example);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
