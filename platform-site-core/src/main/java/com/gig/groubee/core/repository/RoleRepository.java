package com.gig.groubee.core.repository;

import com.gig.groubee.core.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<List<Role>> findByRoleNameIn(List<String> roleNames);

    Optional<List<Role>> findByRoleNameLikeOrderByLevelAsc(String roleNames);

    Optional<Role> findByRoleNameLike(String roleName);

    Optional<Role> findByRoleName(String roleName);
}
