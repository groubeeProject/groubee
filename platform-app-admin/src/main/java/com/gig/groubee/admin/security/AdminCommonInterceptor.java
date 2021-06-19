package com.gig.groubee.admin.security;

import com.gig.groubee.common.service.SecurityService;
import com.gig.groubee.core.dto.menu.MenuDto;
import com.gig.groubee.core.model.Admin;
import com.gig.groubee.core.model.Role;
import com.gig.groubee.core.security.component.UrlCache;
import com.gig.groubee.core.service.MenuService;
import com.gig.groubee.core.types.MenuType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author : Jake
 * @date : 2021-06-20
 */
@Component
@RequiredArgsConstructor
public class AdminCommonInterceptor extends HandlerInterceptorAdapter {

    private final MenuService menuService;
    private final UrlCache urlCache;
    private final SecurityService<Admin, MenuDto, Role> securityService;
    private final MenuType menuType = MenuType.AdminConsole;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
        if (mav != null) {
            Admin loginAccount = securityService.getLoginUser();
            if (loginAccount != null) {

                if (securityService.getMenus() == null || securityService.getMenuLastLoadedAt().isBefore(urlCache.getLastLoadedAt())) {
                    List<MenuDto> menus = menuService.getMenuHierarchyByRoles(menuType, loginAccount.getRoles(), false);
                    securityService.setMenus(menus, urlCache.getLastLoadedAt());
                }
                String uri = request.getRequestURI();
                List<MenuDto> menus = securityService.getMenus();
                for (MenuDto m : menus) {
                    m.setActiveClass("");
                    if (uri.equals("/") && uri.equals(m.getUrl())) {
                        m.setActiveClass("active");
                    } else {
                        if (uri.indexOf(m.getUrl()) == 0) {
                            m.setActiveClass("active");
                            boolean isCorrect = false;
                            for (MenuDto c : m.getChildren()) {
                                c.setActiveClass("");
                                if (uri.indexOf(c.getUrl()) == 0) {
                                    if (uri.equals(c.getUrl())) {
                                        c.setActiveClass("active");
                                        isCorrect = true;
                                    }
                                }
                            }
                            if (!isCorrect) {
                                for (MenuDto c : m.getChildren()) {
                                    c.setActiveClass("");
                                    if (uri.indexOf(c.getUrl()) == 0) {
                                        c.setActiveClass("active");
                                    }
                                }
                            }
                        }
                    }
                }

                mav.addObject("menus", menus);
                mav.addObject("navAdmin", loginAccount);

                /**
                 YNType partnerYn = YNType.N;

                 if (!CollectionUtils.isEmpty(loginAccount.getRoles())) {
                 List<Role> roles = new ArrayList<>(loginAccount.getRoles());
                 Role topRole = roles.get(roles.size() - 1);
                 if (!"ROLE_ADMIN".equals(topRole.getRoleName()) && !"ROLE_SUPER_ADMIN".equals(topRole.getRoleName())) {
                 partnerYn = YNType.Y;
                 }
                 }

                 mav.addObject("partnerYn", partnerYn);
                 **/

//                List<Role> roles = new ArrayList<>(loginAccount.getRoles());
//                Role topRole = roles.get(roles.size() - 1);

                //mav.addObject("adminRoleName", topRole.getRoleName());
//                mav.addObject("dataHandlerYn", loginAccount.getDataHandlerYn());
            }

        }
        super.postHandle(request, response, handler, mav);
    }

}
