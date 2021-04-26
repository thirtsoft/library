package com.library.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationRepository<UserInformation, Long> {

    Optional<UserInformation> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
