package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @vervion 1.0
 * @date 2019/05/16 20:35
 * @user Jack-Hunting
 */
@Data
@TableName("t_user")
public class User implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String email;
}
