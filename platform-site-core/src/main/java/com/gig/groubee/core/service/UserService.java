package com.gig.groubee.core.service;

import com.gig.groubee.common.exception.AlreadyEntity;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.core.types.ModifyType;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
public interface UserService<T,V> {

    void addPasswordFailCnt(String username);

    void loginSuccess(String clientIp, String username, String os);

    void resetPasswordFailure(String username);

    /**
     * 사용자 Role 추가/삭제
     *
     * @param roleName   Role
     * @param username   Role이 등록될 사용자 계정
     * @param modifyType 등록/삭제
     * @throws NotFoundException Role, User, Admin 을 찾을 수 없을 경우
     */
    void modifyUserRole(String roleName, String username, ModifyType modifyType) throws NotFoundException;

    /**
     * 사용자 정보 저장
     *
     * @param user
     * @return
     * @throws NotFoundException
     * @throws AlreadyEntity
     */
    T save(V user) throws NotFoundException, AlreadyEntity;

    /**
     * 사용자 정보
     * @param username
     * @return
     * @throws NotFoundException
     */
    T getUser(String username) throws NotFoundException;

    V findByIdToDto(Long id) throws NotFoundException;
}
