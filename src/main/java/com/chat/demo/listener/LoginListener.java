package com.chat.demo.listener;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.listener.SaTokenListenerForSimple;
import cn.dev33.satoken.stp.SaLoginModel;

/**
 * @author lsf
 * @date 2023年05月04日 10:21 AM
 */
public class LoginListener extends SaTokenListenerForSimple {

    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        super.doLogin(loginType, loginId, tokenValue, loginModel);
    }

    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        super.doLogout(loginType, loginId, tokenValue);
    }
}
