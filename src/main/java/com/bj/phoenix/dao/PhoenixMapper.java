package com.bj.phoenix.dao;

import com.bj.phoenix.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PhoenixMapper {
    List<User> getAllInfo();
    User getInfo(String id);
}
