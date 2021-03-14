package com.library.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @GetMapping("/user")
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_VENDEUR"})
    public String userAccess() {
        return ">>> User Contents!";
    }

    @GetMapping("/admin")
    @Secured({"ROLE_ADMIN"})
    public String adminAccess() {
        return ">>> Admin Contents!";
    }
}
