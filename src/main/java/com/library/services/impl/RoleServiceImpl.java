package com.library.services.impl;

import com.library.entities.Role;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.RoleRepository;
import com.library.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findRoleById(Long idRole) {
        if (!roleRepository.existsById(idRole)) {
            throw new ResourceNotFoundException("Role Not found");
        }

        return roleRepository.findById(idRole);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long idRole, Role role) {
        if (!roleRepository.existsById(idRole)) {
            throw new ResourceNotFoundException("Role Not found");
        }
        Optional<Role> role1 = roleRepository.findById(idRole);
        if (!role1.isPresent()) {
            throw new ResourceNotFoundException("Role Not found");
        }
        Role roleResult = role1.get();

        roleResult.setName(role.getName());

        return roleRepository.save(roleResult);
    }

    @Override
    public void deleteRole(Long idRole) {
        if (!roleRepository.existsById(idRole)) {
            throw new ResourceNotFoundException("Role Not found");
        }
        roleRepository.deleteById(idRole);

    }


}
