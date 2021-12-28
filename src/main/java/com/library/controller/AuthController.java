package com.library.controller;

import com.library.entities.HistoriqueLogin;
import com.library.entities.Role;
import com.library.entities.Utilisateur;
import com.library.enumeration.RoleName;
import com.library.message.request.LoginForm;
import com.library.message.request.SignUpForm;
import com.library.message.response.JwtResponse;
import com.library.message.response.ResponseMessage;
import com.library.repository.RoleRepository;
import com.library.repository.UtilisateurRepository;
import com.library.security.jwt.JwtProvider;
import com.library.security.services.UserPrinciple;
import com.library.services.HistoriqueLoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.library.utils.Constants.APP_ROOT;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private HistoriqueLoginService historiqueLoginService;

    @PostMapping(value = APP_ROOT + "/auth/signIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Authentifier ",
            notes = "Cette méthode permet d'authentifier un utilisateur", response = LoginForm.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur est authentifié avec succès"),
            @ApiResponse(code = 400, message = "Error d'authentification, veuillez vérifer vos identifiants")

    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        //    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Optional<Utilisateur> optionalUtilisateur = userRepository.findById(userDetails.getId());
        Utilisateur utilisateur = optionalUtilisateur.get();
        HistoriqueLogin historiqueLogin = new HistoriqueLogin();
        historiqueLogin.setUtilisateur(utilisateur);
        historiqueLogin.setAction("SE CONNECTER");
        historiqueLogin.setCreatedDate(new Date());
        historiqueLoginService.saveHistoriqueLogin(historiqueLogin);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping(value = APP_ROOT + "/auth/signUp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Créer un compte ",
            notes = "Cette méthode permet à un utilisateur de créer son compte", response = SignUpForm.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utlisateur a été crée"),
            @ApiResponse(code = 400, message = "Aucun utilisateur n'a été crée")

    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        Utilisateur user = new Utilisateur(signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Set<String> strRoles = signUpRequest.getRole();
        String[] roleArr = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (roleArr == null) {
            roles.add(roleRepository.findByName(RoleName.ROLE_VENDEUR).get());
        }

        for (String role : roleArr) {
            switch (role.toLowerCase()) {
                case "admin":
                    roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).get());
                    break;

                case "manager":
                    roles.add(roleRepository.findByName(RoleName.ROLE_MANAGER).get());
                    break;

                case "gerant":
                    roles.add(roleRepository.findByName(RoleName.ROLE_GERANT).get());
                    break;

                case "vendeur":
                    roles.add(roleRepository.findByName(RoleName.ROLE_VENDEUR).get());
                    break;

                default:
                    return ResponseEntity.badRequest().body("Specified role not found");

            }
        }

        user.setRoles(roles);

        user.setActive(true);

        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.CREATED);

/*
        roles.forEach(role -> {
            switch (roleArr.toLowerCase()) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Admin Role not find."));
                    roles.add(adminRole);

                    break;
                case "vendeur":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_VENDEUR)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Vendeur Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.CREATED);

        */
    }

    @GetMapping(value = APP_ROOT + "/utilisateurs/getUserByUsername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Utilisateur par username",
            notes = "Cette méthode permet de chercher un Utilisateur par son nom d'utilisateur", response = Utilisateur.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Utilisateur a été trouver avec cet username"),
            @ApiResponse(code = 404, message = "Aucun Utilisateur n'existe avec cet username pas dans la BD")

    })
    public Optional<Utilisateur> getUserByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

}
