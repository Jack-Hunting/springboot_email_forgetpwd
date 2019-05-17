package com.qf.service;

import com.qf.entity.User;

public interface IUserService {
    boolean isExist(User user);

    void addUser(User user);

    User getUserByUsername(String username);

    boolean updatePwd(String username, String password);
}
