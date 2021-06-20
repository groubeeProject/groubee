package com.gig.groubee.core.service;

import com.gig.groubee.common.exception.AlreadyEntity;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.common.service.UserService;
import com.gig.groubee.common.types.ModifyType;
import com.gig.groubee.core.dto.admin.AdminDto;
import com.gig.groubee.core.model.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Jake
 * @date : 2021-06-19
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
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
    public AdminDto findByIdToDto(Long id) throws NotFoundException {
        return null;
    }
}
