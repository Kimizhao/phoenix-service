package com.bj.phoenix.controller;

import com.bj.phoenix.domain.User;
import com.bj.phoenix.service.PhoenixServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoenixController {
    @Autowired
    private PhoenixServiceImpl phoenixServiceImpl;

    @RequestMapping("/alluser")
    public Object showAllInfo() {
        List<User> user= phoenixServiceImpl.getAllUserInfo();
        if(user != null){
            return user;
        }
        else{
            return "查询为空！";
        }
    }

    @RequestMapping("/user/{id}")
    public Object showInfo(@PathVariable("id") String id) {
        User user= phoenixServiceImpl.getUserInfo(id);
        if(user != null){
            return user;
        }
        else{
            return "查询为空！";
        }
    }
}
