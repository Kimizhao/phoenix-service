package com.bj.phoenix.service;

import com.bj.phoenix.dao.PhoenixMapper;
import com.bj.phoenix.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoenixServiceImpl {
    @Autowired
    private PhoenixMapper phoenixMapper;

    public List<User> getAllUserInfo() {
        return phoenixMapper.getAllInfo();
    }

    public User getUserInfo(String id) {
        return phoenixMapper.getInfo(id);
    }
}
