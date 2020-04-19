package com.mybatis.study.dao;

import com.mybatis.study.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    int insert(User user);

    User findUserById(int id);

    List<User> findAll();
}
