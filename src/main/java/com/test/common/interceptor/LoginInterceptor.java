package com.test.common.interceptor;

import com.junyi.cms.login.model.User;
import com.test.login.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shuisheng on 2018-02-11.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {


    /**
     * 判断是否登录
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        User user = (User)request.getSession().getAttribute("userOnline");

        if(user == null){
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }else{
            return true;
        }


    }
}
