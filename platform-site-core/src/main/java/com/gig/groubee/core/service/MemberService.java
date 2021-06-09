package com.gig.groubee.core.service;

import com.gig.groubee.common.exception.AlreadyEntity;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.common.service.UserService;
import com.gig.groubee.common.types.ModifyType;
import com.gig.groubee.core.dto.member.MemberDto;
import com.gig.groubee.core.model.Member;
import org.springframework.stereotype.Service;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Service
public class MemberService implements UserService<Member, MemberDto> {

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
    public Member save(MemberDto user) throws NotFoundException, AlreadyEntity {
        return null;
    }

    @Override
    public Member getUser(String username) throws NotFoundException {
        return null;
    }

    @Override
    public MemberDto getUserToDto(Long id) throws NotFoundException {
        return null;
    }
}
