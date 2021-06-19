package com.gig.groubee.core.service;

import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.common.service.SecurityService;
import com.gig.groubee.core.dto.menu.MenuDto;
import com.gig.groubee.core.model.Role;
import com.gig.groubee.core.security.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Jake
 * @date : 2021-06-19
 */
public abstract class AbstractSecurityService<T> implements SecurityService<T, MenuDto, Role> {


    /**
     * Login Object 형태로 대상 전달
     *
     * @return
     */
    public abstract T getLoginUser();

    /**
     * 최상위 롤 갖고 오기
     *
     * @return
     */
    public abstract Role getTopRole() throws NotFoundException;

    /**
     * 로그인 사용자 계정 갖고오기
     *
     * @return
     * @throws NotFoundException
     */
    public String getLoginUsername() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) return null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    /**
     * menu
     *
     * @return
     */
    public List<MenuDto> getMenus() {
        if (SecurityContextHolder.getContext() != null) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) return null;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof LoginUser) {
                return ((LoginUser) principal).getMenus();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 메뉴가 마지막으로 반영된 시간
     *
     * @return
     */
    public LocalDateTime getMenuLastLoadedAt() {
        if (SecurityContextHolder.getContext() != null) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) return null;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof LoginUser) {
                return ((LoginUser) principal).getMenuLastLoadedAt();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 계정별 메뉴 셋팅
     *
     * @param menus        메뉴목록
     * @param lastLoadedAt 마지막 업데이트 기준
     */
    public void setMenus(List<MenuDto> menus, LocalDateTime lastLoadedAt) {
        if (SecurityContextHolder.getContext() != null) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) return;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof LoginUser) {
                ((LoginUser) principal).setMenus(menus, lastLoadedAt);
            }
        }
    }


}
