package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @vervion 1.0
 * @date 2019/05/16 20:46
 * @user Jack-Hunting
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 判断用户是否存在
     */
    @Override
    public boolean isExist(User user) {

        User userEx = userMapper.selectById(user.getId());
        if(userEx!=null){
            return true;
        }
        return false;
    }

    /**
     * 添加用户
     */
    @Override
    public void addUser(User user) {
        userMapper.insert(user);

    }

    /**
     * 通过用户名找到用户
     */
    @Override
    public User getUserByUsername(String username) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 修改密码
     */
    @Override
    public boolean updatePwd(String username, String password) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = userMapper.selectOne(wrapper);
        user.setPassword(password);
        userMapper.updateById(user);
        return true;
    }
}
