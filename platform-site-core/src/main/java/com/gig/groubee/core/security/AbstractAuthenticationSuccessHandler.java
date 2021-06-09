package com.gig.groubee.core.security;

import com.gig.groubee.common.service.UserService;
import com.gig.groubee.core.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Component
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    final int SESSION_TIMEOUT = 60 * 60; //1시간

    private final UserService userService;
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT);
        String username = authentication.getName();
        if (Strings.isEmpty(username) && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            username = user.getUsername();
        }
        String clientIp = CommonUtils.getClientIP(request);
        this.onAuthenticationSuccess(username, clientIp);
        this.saveLoginLog(username, clientIp, "Y");
    }

    private void onAuthenticationSuccess(String username, String clientIp) {
        userService.loginSuccess(clientIp, username);
    }

    public abstract void saveLoginLog(String username, String clientIp, String loginSuccess);
}

