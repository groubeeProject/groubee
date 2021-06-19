package com.gig.groubee.core.security;

import com.gig.groubee.core.service.AdminService;
import com.gig.groubee.core.service.LogService;
import com.gig.groubee.core.service.MemberService;
import com.gig.groubee.core.service.UserService;
import com.gig.groubee.core.types.YNType;
import com.gig.groubee.core.util.CommonUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
@NoArgsConstructor
public class AbstractAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    final int SESSION_TIMEOUT = 60 * 60; //1시간

    LogService logService;
    private final UserService userService;


    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT);
        String username = authentication.getName();
        if (Strings.isEmpty(username) && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            username = user.getUsername();
        }
        String clientIp = CommonUtils.getClientIP(request);

        /**
         * TODO USER-AGENT 를 이용한 OS 정보
         */
        userService.loginSuccess(clientIp, username, null);
//        logService.saveLoginLog(username, clientIp, YNType.Y, null);
    }
}

