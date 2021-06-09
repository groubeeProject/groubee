package com.gig.groubee.admin.security;

import com.gig.groubee.common.types.LoginServiceType;
import com.gig.groubee.core.security.AbstractAuthenticationFailureHandler;
import com.gig.groubee.core.service.AdminService;
import com.gig.groubee.core.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 로그인 실패 핸들러
 *
 * @author Giwoon Koo
 * @date 2019-04-09
 */
@Component
@Slf4j
public class AuthenticationFailureHandlerImpl extends AbstractAuthenticationFailureHandler {

    LogService logService;

    public AuthenticationFailureHandlerImpl(AdminService userService, LogService logService) {
        super(userService);
        this.logService = logService;
    }

    @Override
    public void saveLoginLog(String username, String clientIp, String loginSuccess, String failMessage) {
        logService.saveLoginLog(LoginServiceType.AdminConsole, username, clientIp, loginSuccess, failMessage);
    }
}
