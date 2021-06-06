package com.gig.groubee.core.security;

import com.gig.groubee.core.service.UserService;
import com.gig.groubee.core.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
public abstract class AbstractAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");

        String failMessage = null;
        if (exception instanceof BadCredentialsException) {
            userService.addPasswordFailCnt(username);
            failMessage = "계정 또는 비밀번호가 틀렸습니다.";
        } else if (exception instanceof DisabledException) {
            failMessage = "잠긴 계정 입니다. 관리자에게 문의 하세요.";
        } else if (exception instanceof LockedException) {
            failMessage = "패스워드가 5회 이상 틀렸습니다. 관리자에게 문의 하세요.";
        } else if (exception instanceof CredentialsExpiredException) {
            failMessage = "비밀번호 유효기간이 만료되었습니다. 비밀번호를 변경해 주세요.";
        } else if (exception instanceof AccountExpiredException) {
            failMessage = "휴면 계정입니다. 휴면 해제 후 사용아 가능합니다.";
        }

        request.setAttribute("exceptionMessage", failMessage);

        // this.saveLoginLog(username, CommonUtils.getClientIP(request), "N", failMessage);

        log.error("Authentication fail username: {}, message: {}", username, failMessage);
        request.getRequestDispatcher("/login?error=500").forward(request, response);
    }

    public abstract void saveLoginLog(String username, String clientIp, String loginSuccess, String failMessage);
}
