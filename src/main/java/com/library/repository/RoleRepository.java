package com.library.repository;

import com.library.entities.Role;
import com.library.entities.Utilisateur;
import com.library.enumeration.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    @Query("select c from Role c where c.roleName like :role")
    List<Role> findListRoleByRoleName(@Param("role") String roleName);

    Optional<Role> findByName(RoleName roleName);


}
