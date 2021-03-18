package com.library.services;

import com.library.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAllRoles();

    Optional<Role> findRoleById(Long idRole);

    Role saveRole(Role role);

    Role updateRole(Long idRole, Role role);

    void deleteRole(Long idRole);

    Role findRoleByRoleName(String RoleName);

    List<Role> findListRoleByRoleName(String RoleName);

}
