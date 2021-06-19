package com.gig.groubee.core.security;

import com.gig.groubee.common.model.IUser;
import com.gig.groubee.core.dto.menu.MenuDto;
import com.gig.groubee.core.model.AbstractUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
public class LoginUser extends User {
    private IUser loginUser;
    private List<MenuDto> menus;
    @Getter
    private LocalDateTime menuLastLoadedAt;


    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities, AbstractUser loginUser) {
        super(username, password == null ? "" : password, authorities);
        this.loginUser = loginUser;
    }

    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                     boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, IUser loginUser) {
        super(username, password == null ? "" : password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.loginUser = loginUser;
    }

    public IUser getLoginAccount() {
        return loginUser;
    }

    public List<MenuDto> getMenus() {
        return menus;
    }


    public void setMenus(List<MenuDto> menus, LocalDateTime loadedAt) {
        this.menus = menus;
        this.menuLastLoadedAt = loadedAt;
    }
}
