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

//    private final AdminLoginLogRepository adminLoginLogRepository;
//    private final CustmLoginLogRepository custmLoginLogRepository;
//    private final PageLogRepository pageLogRepository;
//
//    @Transactional
//    public void saveLoginLog(LoginServiceType loginServiceType, String username, String clientIp, String loginSuccess) {
//        this.saveLoginLog(loginServiceType, username, clientIp, loginSuccess, null);
//    }
//
    /**
     * 사용자 로그인 저장
     */
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
//
//    public void requestWatch(String requestURI, String httpMethod, String jsonParam, String controller, String springMethod,
//                             Long adminId, String username) {
//
//        if (requestURI.contains("/login")) return;
//
//        PageLog pageLog = new PageLog();
//        pageLog.setPageLogType(PageLogType.AdminConsole);
//        pageLog.setHttpMethod(httpMethod);
//        pageLog.setUri(requestURI);
//        pageLog.setParam(jsonParam);
//        pageLog.setAdminId(adminId);
//        pageLog.setUsername(username);
//        pageLog.setSpringControllerName(controller);
//        pageLog.setSpringMethodName(springMethod);
//        pageLogRepository.save(pageLog);
//    }

}
