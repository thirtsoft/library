package com.library.controller;

import com.library.entities.Employe;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.security.RegisterForm;
import com.library.services.AccountService;
import com.library.services.EmployeService;
import com.library.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class UtilisateurController {
	
	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private AccountService accountService;
	
	@GetMapping("/utilisateurs")
	public List<Utilisateur> getListUtulisateurs() {
		return utilisateurService.findAllUtilisateurs();
		
	}
	
	@GetMapping("/utilisateurs/{id}")
	public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable(value = "id") Long idUser)
			throws ResourceNotFoundException{
		Utilisateur utilisateur = utilisateurService.findUtilisateurById(idUser)
				.orElseThrow(()-> new ResourceNotFoundException("Utilisateur that id is" + idUser + "not found"));
		return ResponseEntity.ok().body(utilisateur);
		
	}

	@GetMapping("/searchUtilisateurByUsername")
	public Utilisateur getUtilisateurByUsername(@RequestParam(value = "username") String username) {
		return utilisateurService.findUtilisateurByUsername(username);

	}

	
	@GetMapping("/searchListUtilisateurByUsername")
	public List<Utilisateur> getListUtilisateurByUsername(@RequestParam(value = "username") String username) {
		
		return utilisateurService.findListUtilisateurByUsername("%"+username+"%");
		
	}
	
/*
	@PostMapping("/utilisateurs")
	public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {

		return new ResponseEntity<>(utilisateurService.saveUtilisateur(utilisateur), HttpStatus.CREATED);
	}
*/
/*
	@PostMapping("/register")
	public ResponseEntity<Utilisateur> register(@RequestBody RegisterForm registerForm) {
		if (!registerForm.getPassword().equals(registerForm.getConfirmPassword()))
		    throw new RuntimeException("Les deux mots de passe ne sont identiques");
		Utilisateur user = accountService.findUtilisateurByUsername(registerForm.getUsername());
		if (user != null) throw new RuntimeException("Cet utilisateur existe");
		Utilisateur userinfo = new Utilisateur();
		userinfo.setUsername(registerForm.getUsername());
		userinfo.setPassword(registerForm.getPassword());

		System.out.println("********************");
        System.out.println("Username register : " +userinfo.getUsername());
        System.out.println("Password register : " +userinfo.getPassword());

		return new ResponseEntity<>(accountService.saveUtilisateur(userinfo), HttpStatus.CREATED);
		//return accountService.saveUtilisateur(utilisateur);
	}
*/
	@PutMapping("/utilisateurs/{idUser}")
	public ResponseEntity<Utilisateur>  updateUtilisateur(@PathVariable(value = "idUser") Long idUser, @RequestBody Utilisateur utilisateur) {
		utilisateur.setId(idUser);
		return new ResponseEntity<>(utilisateurService.saveUtilisateur(utilisateur), HttpStatus.OK);
		
	}
	@DeleteMapping("/utilisateurs/{id}")
	public ResponseEntity<Object> deleteUtilisateur(@PathVariable(value = "id") Long idUser) {
		utilisateurService.deleteUtilisateur(idUser);
		return ResponseEntity.ok().build();
		
	}

}
