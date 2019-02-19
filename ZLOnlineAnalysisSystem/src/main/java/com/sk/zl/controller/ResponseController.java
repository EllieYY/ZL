package com.sk.zl.controller;

import com.sk.zl.model.JsonRequestModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/18
 */
@RestController
@RequestMapping(value = "/zl")
public class ResponseController {
    @ResponseBody
    @RequestMapping(value = "/testGet/{param}")
    public String responseGet(@PathVariable("param") String param) {
        return "Test Get: " + param;
    }

    @ResponseBody
    @RequestMapping(value = "/testGet")
    public String responseGet1(@RequestParam("param2") String param1, @RequestParam("param2") String param2) {
        return "Test Get: " + param1 + " + " + param2;
    }

    @ResponseBody
    @RequestMapping(value = "/testPost", method = RequestMethod.POST)
    public String responsePost() {
        return "Test post";
    }

    @CrossOrigin
    @RequestMapping(value = "testJson", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonRequestModel responseJson(@RequestBody JsonRequestModel entity) {
        entity.setId("66");
        return entity;
    }


}
