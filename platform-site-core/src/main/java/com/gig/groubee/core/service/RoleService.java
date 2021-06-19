package com.gig.groubee.core.service;

import com.gig.groubee.common.exception.AlreadyEntity;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.core.dto.role.RoleExpendDto;
import com.gig.groubee.core.model.Role;
import com.gig.groubee.core.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author : Jake
 * @date : 2021-06-07
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * 롤이 있는지 확인
     *
     * @return
     */
    public boolean isEmpty() {
        return roleRepository.count() == 0;
    }

    /**
     * 롤 생성
     *
     * @param roleName    Role 이름
     * @param description Role 설명
     * @throws AlreadyEntity 이미 생성된 Role
     */
    public Role createRole(String roleName, String description, int level, int sortOrder) throws AlreadyEntity {
        String upperRoleName = roleName.toUpperCase();
        if (!roleName.startsWith("ROLE_")) {
            upperRoleName = "ROLE_" + roleName.toUpperCase();
        }
        Optional<Role> optRole = roleRepository.findByRoleName(upperRoleName);
        if (optRole.isPresent())
            throw new AlreadyEntity("Role");

        Role role = new Role();
        role.setRoleName(upperRoleName);
        role.setDescription(description);
        role.setSortOrder(sortOrder);
        role.setLevel(level);
        roleRepository.save(role);

        return role;
    }

    public RoleExpendDto createRole(RoleExpendDto dto) throws AlreadyEntity {
        return new RoleExpendDto(createRole(dto.getRoleName(), dto.getDescription(), dto.getLevel(), dto.getSortOrder()));
    }

    /**
     * Role 목록
     *
     * @return
     */
    public List<Role> roleList() {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "sortOrder"));
    }


    public Role findByRoleName(String roleName) throws NotFoundException {
        Optional<Role> findRole = roleRepository.findByRoleName(roleName);
        if (findRole.isPresent())
            return findRole.get();
        else
            throw new NotFoundException(NotFoundException.ROLE_NOT_FOUND);
    }

}
