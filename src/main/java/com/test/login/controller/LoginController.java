package com.test.login.controller;

import com.junyi.cms.login.model.User;
import com.junyi.cms.login.service.LoginService;
import com.test.login.model.User;
import com.test.login.service.LoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 
 * 管理人员登录登出
 * shuisheng 2018-02-11
 * 
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService userService;



    /**
     * 用户登录
     * @throws IOException
     * @Author shuisheng
     */
    @RequestMapping(value = "/in", method = RequestMethod.POST)
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request){


        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)) {
            return "/";
        }

        User user = userService.getUser(username, password);

        if(user != null){//表示登录成功
            request.getSession().setAttribute("userOnline", user);
            return "head";
        }else{
            return "/";
        }

    }


    /**
     * 用户登出
     * @throws IOException
     * @Author shuisheng
     */
    @RequestMapping(value = "/out")
    public String login(
            HttpServletRequest request) {
        request.getSession().removeAttribute("userOnline");
        return "/";
    }
}
