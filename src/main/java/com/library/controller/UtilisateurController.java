package com.library.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.domaine.Response;
import com.library.entities.Utilisateur;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.AccountService;
import com.library.services.UtilisateurService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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

    @GetMapping("/utilisateurs")
    public List<Utilisateur> getListUtulisateurs() {
        return utilisateurService.findAllUtilisateurs();

    }

    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable(value = "id") Long idUser)
            throws ResourceNotFoundException {
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + idUser + "not found"));
        return ResponseEntity.ok().body(utilisateur);
    }

    @GetMapping("/avatar/{id}")
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception {

        Utilisateur user = utilisateurService.findUtilisateurById(id).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/") + user.getPhoto()));

    }

    @GetMapping("/searchUtilisateurByUsername")
    public Utilisateur getUtilisateurByUsername(@RequestParam(value = "username") String username) {
        return utilisateurService.findUtilisateurByUsername(username);
    }

    @GetMapping("/searchListUtilisateurByUsername")
    public List<Utilisateur> getListUtilisateurByUsername(@RequestParam(value = "username") String username) {

        return utilisateurService.findListUtilisateurByUsername("%" + username + "%");

    }

    @PutMapping("/photo")
    public void editPhoto(@RequestParam("image") MultipartFile file, @RequestParam("id") String id)
            throws JsonParseException, JsonMappingException, Exception {

        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PutMapping("/user")
    public ResponseEntity<Response> editUser(@RequestParam("image") MultipartFile file, @RequestParam("user") String user)
            throws JsonParseException, JsonMappingException, Exception {

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

    @GetMapping(path = "photoUser/{id}", produces = IMAGE_PNG_VALUE)
    public byte[] getPhotoUser(@PathVariable("id") Long id) throws Exception {
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + id + "not found"));
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/users/" + utilisateur.getPhoto()));
    }

    /**
     * Enregistre la photo dans le dossier utilisateur
     */
    @PostMapping(path = "uploadUserPhoto/{id}", produces = IMAGE_PNG_VALUE)
    public void uploadUserPhoto(MultipartFile file, @PathVariable("id") Long id) throws IOException {
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + id + "not found"));
        utilisateur.setPhoto(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home") + "/users/photo/" + utilisateur.getPhoto()), file.getBytes());
        utilisateurService.saveUtilisateur(utilisateur);
    }

    /**
     * Enregistre la photo dans la base de donnée
     */
    @PostMapping(path = "uploadUserPhoto/{id}/uploadUserPhoto", produces = IMAGE_PNG_VALUE)
    public void uploadUserPhotoToDB(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id) throws IOException {
        byte[] bytes = file.getBytes();
        String encodedFile = new String(Base64.encodeBase64(bytes), "UTF-8");
        Utilisateur utilisateur = utilisateurService.findUtilisateurById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur that id is" + id + "not found"));
        utilisateur.setPhoto("data:image/jpeg;base64;" + encodedFile.toString());
        utilisateurService.saveUtilisateur(utilisateur);
    }

    @PutMapping("/utilisateurs/{idUser}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable(value = "idUser") Long idUser, @RequestBody Utilisateur utilisateur) {
        utilisateur.setId(idUser);
        Utilisateur savingUser = utilisateurService.saveUtilisateur(utilisateur);
        if (savingUser != null) {
            return new ResponseEntity<Utilisateur>(savingUser, HttpStatus.OK);
        }
        //	return new ResponseEntity<>(utilisateurService.saveUtilisateur(utilisateur), HttpStatus.OK);
        return new ResponseEntity<Utilisateur>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<Object> deleteUtilisateur(@PathVariable(value = "id") Long idUser) {
        utilisateurService.deleteUtilisateur(idUser);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/updateUsername")
    public ResponseEntity<Boolean> updateUsername(@RequestBody ObjectNode json) {
        String username;
        String newUsername;
        try {
            username = new ObjectMapper().treeToValue(json.get("username"), String.class);
            newUsername = new ObjectMapper().treeToValue(json.get("newUsername"), String.class);
            boolean existsUser = this.utilisateurService.updateUsernameOfUtilisateur(username, newUsername);
            if (existsUser)
                return new ResponseEntity<Boolean>(existsUser, HttpStatus.OK);

        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception");
            e.printStackTrace();
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PatchMapping("/updatePassword")
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
                return new ResponseEntity<Boolean>(existUser, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            System.out.println("Parsing Exception");
            e.printStackTrace();
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
    }


}
