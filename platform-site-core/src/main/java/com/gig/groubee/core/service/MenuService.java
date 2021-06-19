package com.gig.groubee.core.service;

import com.gig.groubee.common.exception.InvalidRequiredParameter;
import com.gig.groubee.common.exception.NotFoundException;
import com.gig.groubee.core.dto.menu.MenuDto;
import com.gig.groubee.core.dto.role.RoleDto;
import com.gig.groubee.core.model.Menu;
import com.gig.groubee.core.model.Role;
import com.gig.groubee.core.repository.MenuRepository;
import com.gig.groubee.core.types.AntMatcherType;
import com.gig.groubee.core.types.MenuType;
import com.gig.groubee.core.types.ModifyType;
import com.gig.groubee.core.types.YNType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Jake
 * @date : 2021-06-06
 */
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RoleService roleService;

    /**
     * 메뉴가 있는지 확인
     *
     * @return
     */
    public boolean isEmpty() {
        return menuRepository.count() == 0;
    }

    /**
     * 하나의 메뉴 갖고 오기
     *
     * @param menuId 메뉴 id
     * @return
     */
    @Transactional(readOnly = true)
    public Menu getMenu(long menuId) throws NotFoundException {
        Optional<Menu> optMenu = menuRepository.findById(menuId);
        if (optMenu.isPresent())
            return optMenu.get();
        else
            throw new NotFoundException("Menu not found", NotFoundException.MENU_NOT_FOUND);
    }

    /**
     * 메뉴 수정 및 생성
     * menu.id가 null일 경우 새로 생성하고, root에 등록이됨.
     * 그리고 기본 권한이 없기때문에 접근이 불가능함.
     *
     * @param menuDto 메뉴
     * @return 결과
     * @throws NotFoundException
     * @throws InvalidRequiredParameter
     */
    @Transactional
    @Deprecated
    public Menu createAndUpdateMenu(MenuDto menuDto) throws NotFoundException, InvalidRequiredParameter {
        return createAndUpdateMenu(menuDto, null, null, null);
    }

    /**
     * 메뉴 수정 및 생성
     * menu.id가 null일 경우 새로 생성하고, root에 등록이됨.
     * 그리고 기본 권한이 없기때문에 접근이 불가능함.
     *
     * @param dto  메뉴
     * @param pDto 부모 메뉴
     * @return 결과
     * @throws NotFoundException
     * @throws InvalidRequiredParameter
     */
    @Transactional
    @Deprecated
    public Menu createAndUpdateMenu(MenuDto dto, MenuDto pDto) throws NotFoundException, InvalidRequiredParameter {
        return createAndUpdateMenu(dto, pDto, null, null);
    }

    /**
     * 메뉴 생성 / 수정
     *
     * @param dto         메뉴정보
     * @param pDto        부모 메뉴 정보
     * @param defaultRole 최 상단 메뉴에서의 기본 역할
     * @return 결과
     * @throws NotFoundException
     * @throws InvalidRequiredParameter 부모메뉴가 없을경우(최상위 메뉴일 경우) 기본 역할은 필수값입니다.
     */
    @Transactional
    public Menu createAndUpdateMenu(MenuDto dto, MenuDto pDto, String defaultRole, String modifier) throws NotFoundException, InvalidRequiredParameter {

        /*
         * crate menu이고, 부모가 없고, 기본 권한이 없을경우 오류발생
         */
        if (dto.getMenuId() == null && pDto == null && defaultRole == null) {
            throw new InvalidRequiredParameter();
        }

        Optional<Menu> optMenu;
        Menu menu;
        Menu parent = null;
        if (dto.getMenuId() != null) {
            optMenu = menuRepository.findById(dto.getMenuId());
            if (optMenu.isEmpty())
                throw new NotFoundException(dto.getMenuName() + " Not found: " + dto.getMenuId(), NotFoundException.MENU_NOT_FOUND);
            menu = optMenu.get();
        } else {
            menu = new Menu();
            //부모 메뉴가 있는지 여부
            if (pDto != null && pDto.getMenuId() != null) {
                Optional<Menu> optParent = menuRepository.findById(pDto.getMenuId());
                if (optParent.isEmpty())
                    throw new NotFoundException("Parent Menu Not found: " + pDto.getMenuId(), NotFoundException.PARENT_MENU_NOT_FOUND);
                parent = optParent.get();
                parent.addChildren(menu);
            }
        }
        menu.setIconClass(dto.getIconClass());
        menu.setMenuName(dto.getMenuName());
        menu.setActiveYn(dto.getActiveYn());
        menu.setDisplayYn(dto.getDisplayYn());
        menu.setLastModifier(modifier);
        menu.setUrl(dto.getUrl());
        menu.setSortOrder(dto.getSortOrder());
        menu.setMenuType(dto.getMenuType());

        if (menu.getChildren().size() > 0) {
            /*
             * Security 등록 방법으로 인해
             * children 메뉴가 있을 경우 antMatcher를 무조건 single로 등록되도록 해야함.
             */
            menu.setAntMatcherType(AntMatcherType.Single);
        } else {
            menu.setAntMatcherType(dto.getAntMatcherType());
        }
        /*
          최 상단 메뉴 추가시 Default Role 추가
         */
        if (menu.getRoles().size() == 0 && menu.getParent() == null && defaultRole != null) {
            Role role = roleService.findByRoleName(defaultRole);
            menu.getRoles().add(role);
            role.getMenus().add(menu);
        }

        menuRepository.save(menu);

        if (parent != null) {
            /*
              Security 등록 방법으로 인해
              children 메뉴가 있을 경우 antMatcher를 무조건 single로 등록되도록 해야함.
             */
            parent.setAntMatcherType(AntMatcherType.Single);
            menuRepository.save(parent);
        }

        return menu;
    }

    /**
     * 모든 자식 메뉴에 권한 설정
     *
     * @param menu       메뉴
     * @param role       권한
     * @param modifyType 등록/삭제
     */

    @Transactional
    public void allModifyChildrenMenu(Menu menu, Role role, ModifyType modifyType) throws NotFoundException {
        if (modifyType == ModifyType.Register) {
            menu.getRoles().add(role);
            role.getMenus().add(menu);
        } else {
            menu.getRoles().remove(role);
            role.getMenus().remove(menu);
        }
        for (Menu m : menu.getChildren()) {
            allModifyChildrenMenu(m, role, modifyType);
        }
    }

    /**
     * 역할별 메뉴 등록/삭제
     *
     * @param menuId     메뉴 ID
     * @param roleId     역할명
     * @param modifyType 등록/삭제
     * @throws NotFoundException
     */
    @Transactional
    public void modifyMenuRole(long menuId, String roleId, ModifyType modifyType) throws NotFoundException {
        Menu menu = getMenu(menuId);
        Role role = roleService.findByRoleName(roleId);

        allModifyChildrenMenu(menu, role, modifyType);
        menuRepository.save(menu);

    }

    /**
     * 기존 메뉴의 롤을 클리어 하고 신규로 롤 지정
     *
     * @param menuId
     * @param roles
     */
    @Transactional
    public void allChangeMenuRole(long menuId, List<RoleDto> roles) throws NotFoundException {
        Menu menu = getMenu(menuId);
        menu.setRoles(new HashSet<>());

        for (RoleDto dto : roles) {
            Role role = roleService.findByRoleName(dto.getRoleName());
            allModifyChildrenMenu(menu, role, ModifyType.Register);
        }

        menuRepository.save(menu);
    }


    /**
     * 메뉴 계층구조 갖고 오기
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<MenuDto> getAllMenuHierarchy(MenuType menuType) {
        List<Menu> topMenus = menuRepository.findByMenuTypeAndDeleteYnAndParentIsNull(menuType, YNType.N,
                Sort.by(Sort.Direction.DESC, "sortOrder").and(Sort.by(Sort.Direction.ASC, "menuId")));
        List<MenuDto> hierarchy = new ArrayList<>();
        for (Menu m : topMenus) {
            hierarchy.add(new MenuDto(m, true));
        }

        return hierarchy;
    }

    /**
     * 권한 있는 메뉴 갖고 오기
     *
     * @param roleId
     * @return
     * @throws NotFoundException
     */
    @Deprecated
    @Transactional(readOnly = true)
    public List<MenuDto> getMenuHierarchyByRole(String roleId) throws NotFoundException {
        Role role = roleService.findByRoleName(roleId);
        List<Menu> topMenus = menuRepository.findByParentIsNullAndRoles_RoleName(roleId);
        List<MenuDto> hierarchy = new ArrayList<>();
        for (Menu m : topMenus) {
            hierarchy.add(new MenuDto(m, Arrays.asList(role), true));
        }
        return hierarchy;
    }


    /**
     * 권한 있는 메뉴 갖고 오기
     *
     * @param roles
     * @param includeNonActive 숨김 메뉴 표시 여부
     * @return
     * @throws NotFoundException
     */
    @Transactional(readOnly = true)
    public List<MenuDto> getMenuHierarchyByRoles(MenuType menuType, Set<Role> roles, boolean includeNonActive) {
        List<Menu> allDisplayMenus = menuRepository.getAllDisplayMenus(menuType, roles.stream().map(Role::getRoleName).collect(Collectors.toList()));
        List<MenuDto> hierarchy = new ArrayList<>();
        HashMap<Long, MenuDto> menuMap = new HashMap<>();
        for (Menu m : allDisplayMenus) {
            MenuDto dto = new MenuDto(m, false);
            if (m.getParent() == null) {
                hierarchy.add(dto);
                menuMap.put(dto.getMenuId(), dto);
            } else {
                long parentId = m.getParent().getMenuId();
                if (menuMap.containsKey(parentId)) {
                    menuMap.get(parentId).getChildren().add(dto);
                    if (!menuMap.containsKey(dto.getMenuId()))
                        menuMap.put(dto.getMenuId(), dto);
                } else {
                    hierarchy.add(dto);
                    menuMap.put(dto.getMenuId(), dto);
                }


            }
        }

        return hierarchy;
    }

}
