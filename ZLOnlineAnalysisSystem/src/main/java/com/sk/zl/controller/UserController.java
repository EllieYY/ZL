package com.sk.zl.controller;

import com.sk.zl.entity.User;
import com.sk.zl.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping(value = "/all")
    public List<User> getAllUser(User user){
        return userService.getAllUser();
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "user", value = "用户信息实体", required = true, dataType = "User")
    @ResponseBody
    @RequestMapping(value = "/add",  method = RequestMethod.POST)
    public String addUser(User user) {
        userService.addUser(user);
        return "success to add User:" + user.toString();
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public int deleteUser(@PathVariable(value = "id") Integer userId) {
        return userService.deleteUserById(userId);
    }
}
