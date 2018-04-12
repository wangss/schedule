package com.test.login.manager;

import com.junyi.cms.common.utils.MathUtil;

public class UserManager {


	/**
	 * 密码加盐加密
	 * 
	 * 加密算法：md5(md5(pwd)+username)
	 * 
	 * @param username 登录账号
	 * @param password 密码字符串
	 *            密码字符串是否是明文密码
	 * @return 加密后的密码串
	 */
	public static String md5Password(String username, String password) {
		return MathUtil.getMD5(password + username);
	}

    /**
     * 密码加盐加密
     *
     * 加密算法：md5(md5(pwd)+username)
     *
     * @param username
     *            登录账号
     * @param pswd
     *            密码字符串
     * @param pswdIsPlaintext
     *            密码字符串是否是明文密码
     * @return
     */
    public static String md5Password(String username, String pswd, boolean pswdIsPlaintext) {
        if (pswdIsPlaintext) {
            // 如果密码是明文的，先md5一次
            pswd = MathUtil.getMD5(pswd);
        }
        pswd = MathUtil.getMD5(pswd + username);
        return pswd;
    }

	public static void main(String[] args) {
        System.out.println(md5Password("admin", "123456", true));
	}

}
