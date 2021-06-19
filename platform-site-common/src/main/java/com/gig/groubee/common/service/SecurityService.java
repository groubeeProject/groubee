package com.gig.groubee.common.service;

import com.gig.groubee.common.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Jake
 * @date : 2021-06-19
 */
public interface SecurityService<U, M, R> {

    /**
     * 로그인 사용자 계정 갖고오기
     *
     * @return
     * @throws NotFoundException
     */
    String getLoginUsername();

    /**
     * Login 사용자를 T 형태로 대상 전달
     *
     * @return
     */
    U getLoginUser();


    /**
     * menu
     *
     * @return
     */
    List<M> getMenus();

    /**
     * 메뉴가 마지막으로 반영된 시간
     *
     * @return
     */
    LocalDateTime getMenuLastLoadedAt();

    /**
     * 계정별 메뉴 셋팅
     *
     * @param menus        메뉴목록
     * @param lastLoadedAt 마지막 업데이트 기준
     */
    void setMenus(List<M> menus, LocalDateTime lastLoadedAt);

    /**
     * 최상위 롤 갖고 오기
     *
     * @return
     */
    R getTopRole() throws NotFoundException;

}
