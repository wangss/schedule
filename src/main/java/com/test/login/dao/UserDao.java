package com.test.login.dao;

import com.junyi.cms.login.model.User;

/**
 * Created by shuisheng on 2018-02-11.
 */
public interface UserDao{

    public User getUserByUsername(String username);
}
