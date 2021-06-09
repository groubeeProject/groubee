package com.gig.groubee.core.service;

import com.gig.groubee.common.exception.AlreadyEntity;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.common.service.UserService;
import com.gig.groubee.common.types.ModifyType;
import com.gig.groubee.common.types.YNType;
import com.gig.groubee.core.dto.admin.AdminDto;
import com.gig.groubee.core.model.Admin;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Service
public class AdminService implements UserService<Admin, AdminDto> {

    @Override
    public void addPasswordFailCnt(String username) {

    }

    @Override
    public void loginSuccess(String clientIp, String username) {

    }

    @Override
    public void resetPasswordFailure(String username) {

    }

    @Override
    public void modifyUserRole(String roleName, String username, ModifyType modifyType) throws NotFoundException {

    }

    @Override
    public Admin save(AdminDto user) throws NotFoundException, AlreadyEntity {
        return null;
    }

    @Override
    public Admin getUser(String username) throws NotFoundException {
        return null;
    }

    @Override
    public AdminDto getUserToDto(Long id) throws NotFoundException {
        return null;
    }

    public void multiFactorAuth(Admin admin, String verificationCode, String verificationType) {

        String authCode;
        String authType = verificationType;
        LocalDateTime authExpireAt;

        if (authType.equals("email")) {
            authCode = admin.getEmailValidCode();
            authExpireAt = admin.getEmailValidExpireTime();
        } else {
            authCode = admin.getMobileAuthCode();
            authExpireAt = admin.getMobileAuthExpireAt();
        }

        if (!verificationCode.equals(authCode)) {
            throw new ValidCodeNotConfirmedException("인증 번호가 일치하지 않습니다.");
        }

        if (LocalDateTime.now().isBefore(authExpireAt)) {
            if ("email".equals(authType)) {
//                admin.setEmailValidYn(YNType.Y);
                admin.setEmailValidTime(LocalDateTime.now());
            } else {
//                admin.setMobileAuthYn(YNType.Y);
            }

//            admin.setMultiFactorAuthAt(LocalDateTime.now());
//            adminRepository.save(admin);
        } else {
            throw new BadCredentialsException("Invalid verification code");
        }
    }

    public static class ValidCodeNotConfirmedException extends AuthenticationException {
        public ValidCodeNotConfirmedException(String message) {
            super(message);
        }
    }
}
