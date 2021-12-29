package com.library.controller;

import com.library.entities.Role;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.library.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = APP_ROOT + "/roles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Role",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Role", responseContainer = "List<Role>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Role / une liste vide")
    })
    public List<Role> getListRoles() {
        return roleService.findAllRoles();

    }

    @GetMapping(value = APP_ROOT + "/roles/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Role par ID",
            notes = "Cette méthode permet de chercher un Role par son ID", response = Role.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le Role a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Role n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Long idRole)
            throws ResourceNotFoundException {
        Role role = roleService.findRoleById(idRole)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not found"));
        return ResponseEntity.ok().body(role);

    }

    @PostMapping(value = APP_ROOT + "/roles/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {

        return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.CREATED);
    }

    @PutMapping(value = APP_ROOT + "/roles/update/{idRole}")
    public ResponseEntity<Role> updateRole(@PathVariable(value = "idRole") Long idRole, @RequestBody Role role) {
        role.setId(idRole);
        return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.OK);

    }

    @DeleteMapping(value = APP_ROOT + "/roles/delete/{idRole}")
    @ApiOperation(value = "Supprimer un Role par son ID",
            notes = "Cette méthode permet de supprimer un Role par son ID", response = Role.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role a été supprimé")
    })
    public ResponseEntity<Object> deleteRole(@PathVariable(value = "idRole") Long idRole) {
        roleService.deleteRole(idRole);
        return ResponseEntity.ok().build();

    }
}
