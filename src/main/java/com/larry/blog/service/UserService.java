package com.larry.blog.service;

import com.larry.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
