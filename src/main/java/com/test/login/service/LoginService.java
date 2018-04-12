package com.test.login.service;


import com.junyi.cms.login.model.User;
import com.test.login.model.User;


public interface LoginService {

	User getUser(String username, String password);

}
