package com.gig.groubee.admin.security;

import com.gig.groubee.core.security.AbstractAuthenticationSuccessHandler;
import com.gig.groubee.core.service.AdminService;
import com.gig.groubee.core.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 로그인 성공 핸들러
 *
 * @author Giwoon Koo
 * @date 2019-04-09
 */
@Component
@Slf4j
public class AuthenticationSuccessHandlerImpl extends AbstractAuthenticationSuccessHandler {
    LogService logService;

    public AuthenticationSuccessHandlerImpl(AdminService userService, LogService logService) {
        super(userService);
        this.logService = logService;
    }

    @Override
    public void saveLoginLog(String username, String clientIp, String loginSuccess) {
//        logService.saveLoginLog(LoginServiceType.AdminConsole,username, clientIp, "Y");
    }
}
