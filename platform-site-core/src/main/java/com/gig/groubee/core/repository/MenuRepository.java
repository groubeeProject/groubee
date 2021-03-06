package com.gig.groubee.core.repository;

import com.gig.groubee.core.model.Menu;
import com.gig.groubee.core.model.Role;
import com.gig.groubee.core.types.MenuType;
import com.gig.groubee.core.types.YNType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByParentIsNull(Sort sort);

    List<Menu> findByMenuTypeAndDeleteYnAndParentIsNull(MenuType menuType, YNType deleteYn, Sort sort);

    /**
     * 불필요 권한을 제외한 모든 메뉴 갖고오기
     *
     * @param role
     * @param menuType
     * @return
     */
    @Query("SELECT m FROM Menu m WHERE NOT EXISTS (SELECT r FROM m.roles r WHERE r.roleName=:role) and m.parent is null and m.menuType=:menuType " +
            "and m.activeYn='Y' " +
            "and m.deleteYn='N' ")
    List<Menu> getTopMenusNotExistsRole(@Param("role") String role, @Param("menuType") MenuType menuType, Sort sort);

    @Query("SELECT m FROM Menu m WHERE m.parent is null and m.menuType=:menuType " +
            "and m.activeYn='Y' " +
            "and m.deleteYn='N' ")
    List<Menu> getTopMenusNotExistsRole(@Param("menuType") MenuType menuType, Sort sort);

    List<Menu> findByParentIsNullAndRoles_RoleName(String role);

    List<Menu> findDistinctByParentIsNullAndMenuTypeAndDeleteYnAndActiveYnAndRolesIn(MenuType menuType, YNType deleteYn, YNType activeYn, Collection<Role> role, Sort sort);

    @Query("select distinct m From Menu m join m.roles r " +
            "where m.menuType=:menuType and m.parent is null " +
            "and m.activeYn='Y' " +
            "and m.displayYn='Y' " +
            "and m.deleteYn='N' " +
            "and r.roleName in :roles")
    List<Menu> getTopMenu(@Param("menuType") MenuType menuType, @Param("roles") List<String> roles, Sort sort);

    @Query("select distinct m from Menu m inner join fetch m.roles r where m.menuType =:menuType and r.roleName in (:roles) " +
            " and m.activeYn='Y' and m.deleteYn='N' and m.displayYn='Y' " +
            " order by m.parent.menuId, m.sortOrder desc, m.menuId")
    List<Menu> getAllDisplayMenus(@Param("menuType") MenuType menuType, @Param("roles") List<String> roles);
}
