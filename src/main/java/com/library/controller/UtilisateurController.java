package com.library.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.domaine.Response;
import com.library.entities.Scategorie;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.AccountService;
import com.library.services.UtilisateurService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.library.utils.Constants.APP_ROOT;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class UtilisateurController {

    @Autowired
    ServletContext context;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/utilisateurs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Utilisateur",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Utilisateur", responseContainer = "List<Utilisateur>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Utilisateur / une liste vide")
    })
    public List<Utilisateur> getListUtulisateurs() {
        return utilisateurService.findAllUtilisateurs();
    }

    @GetMapping(value = APP_ROOT + "/utilisateurs/allUtilisateurOrderDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Utilisateur",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Utilisateur",
            responseContainer = "List<Utilisateur>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Utilisateur / une liste vide")
    })
    ResponseEntity<List<Utilisateur>> getAllUtilisateurOrderDesc() {
        List<Utilisateur> utilisateurList = utilisateurService.findAllUtilisateursOrderDesc();
        return new ResponseEntity<>(utilisateurList, HttpStatus.OK);
    }

    @GetMapping(value = "/utilisateurs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un Utilisateur par ID",
            notes = "Cette méthode permet de chercher une Utilisateur par son ID", response = Utilisateur.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Utilisateur a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Utilisateur n'existe avec cette ID pas dans la BD")

    })
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable(value = "id") Long idUser)
            throws ResourceNotFoundException {
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + idUser + "not found"));
        return ResponseEntity.ok().body(utilisateur);
    }

    @GetMapping(value = "/avatar/{id}")
    @ApiOperation(value = "Afficher une photo",
            notes = "Cette méthode permet de chercher et d'afficher la photo d'un Utilisateur par son ID"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La photo de l'utilisateur a été trouver"),
            @ApiResponse(code = 404, message = "Aucun Photo n'existe avec cette ID pas dans la BD")

    })
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception {

        Utilisateur user = utilisateurService.findUtilisateurById(id).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + user.getPhoto()));

    }

    @PutMapping("/photo")
    public void editPhotoUser (@RequestParam("image") MultipartFile file, @RequestParam("id") String id)
            throws JsonParseException, JsonMappingException, Exception {

        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File (context.getRealPath("/Images/"+File.separator+newFileName));
        try
        {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

        }catch(Exception e) {
            e.printStackTrace();
        }

    }


    @GetMapping(value = "/searchUtilisateurByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une utilisateur par son username",
            notes = "Cette méthode permet de chercher un utilisateur par son nom d'utilisateur", response = Utilisateur.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été trouver avec cet identifiant fourni"),
            @ApiResponse(code = 404, message = "Aucun Utilisateur n'existe avec ce username pas dans la BD")

    })
    public Utilisateur getUtilisateurByUsername(@RequestParam(value = "username") String username) {
        return utilisateurService.findUtilisateurByUsername(username);
    }

    @GetMapping(value = "/searchListUtilisateurByUsername", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des Utilisateur par username",
            notes = "Cette méthode permet de chercher et renvoyer la liste des Utilisateur par le nom d'utilisateur", responseContainer = "List<Utilisateur>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des Utilisateur par nom d'utilisateur / une liste vide")
    })
    public List<Utilisateur> getListUtilisateurByUsername(@RequestParam(value = "username") String username) {

        return utilisateurService.findListUtilisateurByUsername("%" + username + "%");

    }

    @PutMapping(value = "/user")
    public ResponseEntity<Response> editUser(@RequestParam("image") MultipartFile file, @RequestParam("user") String user)
            throws Exception {

        Utilisateur userIm = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(user, Utilisateur.class);

        boolean isExit = new File(context.getRealPath("/Images/")).exists();

        if (!isExit) {
            new File(context.getRealPath("/Images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }


        userIm.setPhoto(newFileName);
        Utilisateur user3 = utilisateurService.saveUtilisateur(userIm);
        if (user3 != null) {
            return new ResponseEntity<Response>(new Response(""), HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(new Response("Article not saved"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/photoUser/{id}", produces = IMAGE_PNG_VALUE)
    @ApiOperation(value = "Recupérer une photo par ID",
            notes = "Cette méthode permet de récupérer la photo d'un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La photo est affiché")

    })
    public byte[] getPhotoUser(@PathVariable("id") Long id) throws Exception {
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + id + "not found"));
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/users/photos/" + utilisateur.getPhoto()));
    }

    @PostMapping(path = "/uploadUserPhoto/{id}", produces = IMAGE_PNG_VALUE)
    @ApiOperation(value = "Enregistrer une photo dans un dossier",
            notes = "Cette méthode permet d'enregistrer la photo d'un utilisateur dans un dossier externe utilisateur")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La photo a été enregistré dans le dossier utilisateur")

    })
    public void uploadUserPhoto(MultipartFile file, @PathVariable("id") Long id) throws IOException {
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + id + "not found"));
        utilisateur.setPhoto(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/users/photos/" + utilisateur.getPhoto()), file.getBytes());
        utilisateurService.saveUtilisateur(utilisateur);
    }


    @PostMapping(path = "uploadUserPhoto/{id}/uploadUserPhoto", produces = IMAGE_PNG_VALUE)
    @ApiOperation(value = "Enregistrer une photo en BD",
            notes = "Cette méthode permet d'enregistrer la photo d'un utilisateur dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La photo a été enregistré dans la BD avec succès")

    })
    public void uploadUserPhotoToDB(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) throws IOException {
        byte[] bytes = file.getBytes();
        String encodedFile = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + id + "not found"));
        utilisateur.setPhoto("data:image/jpeg;base64;" + encodedFile);
        utilisateurService.saveUtilisateur(utilisateur);
    }

    @PutMapping(value = "/utilisateurs/{idUser}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un utilisateur par son ID",
            notes = "Cette méthode permet de modifier un utilisateur par son ID", response = Utilisateur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été été modifié"),
            @ApiResponse(code = 400, message = "Aucun utiisateur n'a été modifié")

    })
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable(value = "idUser") Long idUser, @RequestBody Utilisateur utilisateur) {
        utilisateur.setId(idUser);
        Utilisateur savingUser = utilisateurService.saveUtilisateur(utilisateur);
        if (savingUser != null) {
            return new ResponseEntity<>(savingUser, HttpStatus.OK);
        }
        //	return new ResponseEntity<>(utilisateurService.saveUtilisateur(utilisateur), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(value = "/utilisateurs/{id}")
    @ApiOperation(value = "Supprimer un Utilisateur par son ID",
            notes = "Cette méthode permet de supprimer un Utilisateur par son ID", response = Utilisateur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Utilisateur a été supprimé")
    })
    public ResponseEntity<Object> deleteUtilisateur(@PathVariable(value = "id") Long idUser) {
        utilisateurService.deleteUtilisateur(idUser);
        return ResponseEntity.ok().build();

    }

    @PatchMapping(value = "/updateUsername")
    @ApiOperation(value = "Modifier le username",
            notes = "Cette méthode permet de modifier le nom d'utilisateur d'un utilisateur", response = Utilisateur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le nom d'utlisateur a été été modifié"),
            @ApiResponse(code = 400, message = "Aucun username n'a été modifié")

    })
    public ResponseEntity<Boolean> updateUsername(@RequestBody ObjectNode json) {
        String username;
        String newUsername;
        try {
            username = new ObjectMapper().treeToValue(json.get("username"), String.class);
            newUsername = new ObjectMapper().treeToValue(json.get("newUsername"), String.class);
            boolean existsUser = this.utilisateurService.updateUsernameOfUtilisateur(username, newUsername);
            if (existsUser)
                return new ResponseEntity<>(existsUser, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PatchMapping(value = "/updatePassword")
    @ApiOperation(value = "Modifier le mot de passe ",
            notes = "Cette méthode permet de modifier le mot de passe d'un", response = Utilisateur.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le mot de passe a été été modifié"),
            @ApiResponse(code = 400, message = "Aucun mot de passe n'a été modifié avec ce username")

    })
    ResponseEntity<Boolean> updatePassword(@RequestBody ObjectNode jsonNodes) {
        String username;
        String oldPassword;
        String newPassword;

        try {
            username = new ObjectMapper().treeToValue(jsonNodes.get("username"), String.class);
            oldPassword = new ObjectMapper().treeToValue(jsonNodes.get("oldPassword"), String.class);
            newPassword = new ObjectMapper().treeToValue(jsonNodes.get("newPassword"), String.class);

            boolean existUser = this.utilisateurService.updatePasswordofUtilisateur(username, oldPassword, newPassword);
            if (existUser)
                return new ResponseEntity<>(existUser, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }


    @GetMapping(value = "/utilisateurs/authorities")
    @ApiOperation(value = "Renvoi la liste de string des roles",
            notes = "Cette méthode permet de chercher et renvoyer la liste de string des roles", responseContainer = "List<String>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste de string des roles / une liste vide")
    })
    public List<String> getAuthorities() {
        return utilisateurService.findAuthorities();
    }


}
