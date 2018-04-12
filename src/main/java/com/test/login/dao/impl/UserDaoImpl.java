package com.test.login.dao.impl;


import com.junyi.cms.login.dao.UserDao;
import com.junyi.cms.login.model.User;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by shuisheng on 2018-02-11
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static String userNameSpace = "com.junyi.cms.login.model.UserMapper.";

    @Autowired
    SqlSession sqlSession;


    public User getUserByUsername(String username) {
        if (StringUtils.isNotBlank(username)) {
            return sqlSession.selectOne(userNameSpace + "getUserByUsername", username);

        } else {
            return null;
        }

    }


}