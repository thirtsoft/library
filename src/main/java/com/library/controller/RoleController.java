package com.library.controller;

import com.library.entities.Role;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/roles")
	public List<Role> getListRoles() {
		return roleService.findAllRoles();

	}

	@GetMapping("/roles/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Long idRole)
			throws ResourceNotFoundException{
		Role role = roleService.findRoleById(idRole)
				.orElseThrow(()-> new ResourceNotFoundException("Role that id is" + idRole + "not found"));
		return ResponseEntity.ok().body(role);

	}


	@PostMapping("/roles")
	public ResponseEntity<Role> createRole(@RequestBody Role role) {

		return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.CREATED);
	}

	@PutMapping("/roles/{idRole}")
	public ResponseEntity<Role>  updateRole(@PathVariable(value = "idRole") Long idRole, @RequestBody Role role) {
		role.setId(idRole);
		return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.OK);

	}
	@DeleteMapping("/roles/{idRole}")
	public ResponseEntity<Object> deleteRole(@PathVariable(value = "idRole") Long idRole) {
		roleService.deleteRole(idRole);
		return ResponseEntity.ok().build();

	}
}
