package com.gig.groubee.core.service;

import com.gig.groubee.common.types.LoginServiceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LogService {

//    @Transactional
//    public void saveLoginLog(LoginServiceType loginServiceType, String username, String clientIp, String loginSuccess) {
//        this.saveLoginLog(loginServiceType, username, clientIp, loginSuccess, null);
//    }

    @Transactional
    public void saveLoginLog(LoginServiceType loginServiceType, String username, String clientIp, String loginSuccess, String failMessage) {
//        try {
//            LoginLog loginLog = loginServiceType == LoginServiceType.AdminConsole ? new AdminLoginLog() : new CustmLoginLog();
//            loginLog.setIp(clientIp);
//            loginLog.setUsername(username);
//            loginLog.setLoginSuccessYn(YNType.valueOf(loginSuccess));
//            loginLog.setHostName(InetAddress.getLocalHost().getHostName());
//            loginLog.setHostIp(InetAddress.getLocalHost().getHostAddress());
//            loginLog.setLoginFailMessage(failMessage);
//            if (loginServiceType == LoginServiceType.AdminConsole)
//                adminLoginLogRepository.save((AdminLoginLog) loginLog);
//            else
//                custmLoginLogRepository.save((CustmLoginLog) loginLog);
//        } catch (UnknownHostException e) {
//            log.error(e.getMessage(), e);
//        }

    }
}
