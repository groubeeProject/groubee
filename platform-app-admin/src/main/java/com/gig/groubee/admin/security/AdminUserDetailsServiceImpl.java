package com.gig.groubee.admin.security;

import com.gig.groubee.common.exception.AccountNotConfirmedException;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.core.dto.menu.MenuDto;
import com.gig.groubee.core.model.Admin;
import com.gig.groubee.core.model.role.AdminRole;
import com.gig.groubee.core.model.role.Role;
import com.gig.groubee.core.security.LoginUser;
import com.gig.groubee.core.security.component.UrlCache;
import com.gig.groubee.core.service.AdminService;
import com.gig.groubee.core.service.MenuService;
import com.gig.groubee.core.types.MenuType;
import com.gig.groubee.core.types.YNType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jake
 * @date : 2021-06-07
 */
@Slf4j
@Qualifier("adminUserDetailsService")
@Service
@RequiredArgsConstructor
public class AdminUserDetailsServiceImpl implements UserDetailsService {

    private final AdminService adminService;
    private final MenuService menuService;
    private final MenuType menuType = MenuType.AdminConsole;

    @Autowired
    private final UrlCache urlCache;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin;
        try {
            admin = adminService.getUser(username);
        } catch (NotFoundException e) {
            String msg = "User Not found: " + username;
            log.error(msg);
            throw new UsernameNotFoundException(msg);
        }

        if (admin.getPasswordFailCnt() >= 5 && admin.getPasswordFailTime() != null && admin.getPasswordFailTime().plusMinutes(30).isBefore(LocalDateTime.now())) {
            adminService.resetPasswordFailure(username);
            admin.setPasswordFailCnt(0);
        }

        //user.setPassword("{SHA-512}" + user.getPassword());

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (AdminRole r : admin.getRoles()) {
            if (r.getRole() != null) {
                authorities.add(new SimpleGrantedAuthority(r.getRole().getRoleName()));
            }
        }

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // 접속가능 여부
        if (admin.getActiveYn() == YNType.N) {
            log.error("account disabled");
            enabled = false;
        }

        // 패스워드 틀린 횟수
        if (admin.getPasswordFailCnt() >= 5) {
            accountNonLocked = false;
        }

        // 계정 만료 여부
        if (admin.getDormancyYn() == YNType.Y) {
            accountNonExpired = false;
        }

        /*
        // 슈퍼 관리자 체크
        if (user.getRoles().size() > 0) {
            List<Role> roles = new ArrayList<>(user.getRoles());
            Role topRole = roles.get(roles.size() - 1);

            if("ROLE_SUPER_ADMIN".equals(topRole.getRoleName())) {
                accountNonExpired = false;
            }
        }
        */

        // 비밀번호 만료 여부
        if (admin.getPasswordModifyAt() != null && admin.getPasswordModifyAt().plusMonths(3).isBefore(LocalDateTime.now())) {
            credentialsNonExpired = false;
        }

        // 최초 비밀번호 변경 여부
        if (YNType.Y == admin.getPasswordResetYn()) {
            throw new AccountNotConfirmedException("Password reset required");
        }

        LoginUser loginUser = new LoginUser(admin.getUsername(), admin.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, admin);

        /**
         * 메뉴성능을 위해 로그인시 추가
         */
        //long start = System.currentTimeMillis();
        //log.debug(">>>>>>>>>Menu Start: " + start);
        List<MenuDto> menus = menuService.getMenuHierarchyByRoles(menuType, admin.getRoles(), false);
        loginUser.setMenus(menus, urlCache.getLastLoadedAt());
        //log.debug(">>>>>>>>>Menu End: " + ((System.currentTimeMillis() - start) / 1000) + "s");
        return loginUser;
    }
}
