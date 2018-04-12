package com.test.login.service.impl;

import com.junyi.cms.login.dao.UserDao;
import com.junyi.cms.login.manager.UserManager;
import com.junyi.cms.login.model.User;
import com.junyi.cms.login.service.LoginService;
import com.test.login.dao.UserDao;
import com.test.login.manager.UserManager;
import com.test.login.model.User;
import com.test.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
    UserDao userDao;


	@Override
	public User getUser(String username, String password) {

		User user = userDao.getUserByUsername(username);

		if (user != null){
			String md5Password = UserManager.md5Password(username, password, true);

			if (!md5Password.equals(user.getPswd())) {
				return null;
			}
		}

		return user;

	}
}
