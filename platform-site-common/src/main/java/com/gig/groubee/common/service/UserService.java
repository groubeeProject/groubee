package com.gig.groubee.common.service;

import com.gig.groubee.common.exception.AlreadyEntity;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.common.types.ModifyType;

/**
 * @author : Jake
 * @date : 2021-06-09
 */
public interface UserService<T, V> {
    /**
     * 페스워드 틀린 횟수 추가
     * @param username
     */
    void addPasswordFailCnt(String username);

    /**
     * 로그인 성공
     * @param clientIp
     * @param username
     */
    void loginSuccess(String clientIp, String username);

    /**
     * 패스워드 틀린 횟수 초기화
     * @param username
     */
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

    V getUserToDto(Long id) throws NotFoundException;
}
