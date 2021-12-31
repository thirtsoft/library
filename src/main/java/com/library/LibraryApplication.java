package com.library;

import com.library.entities.*;
import com.library.enumeration.RoleName;
import com.library.repository.*;
import com.library.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryApplication.class);

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ScategorieRepository scategorieRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
        createDirectoryIfItDoesntExist();
    }

    private static void createDirectoryIfItDoesntExist() {
        Path path = Paths.get(System.getProperty("user.home") + "/users/photos/");

        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ie) {
                LOG.error(String.format("Problem creating directory %s", path));
            }
        }
    }


    // @Bean
  /*  public BCryptPasswordEncoder getBCPE() {
        return new BCryptPasswordEncoder();
    }
*/
    @Override
    public void run(String... args) throws Exception {

        Category c1 = categoryRepository.save(new Category(1L, "cat1", "cat1"));
        Category c2 = categoryRepository.save(new Category(2L, "cat2", "cat2"));
        Category c3 = categoryRepository.save(new Category(3L, "cat3", "cat3"));

        Scategorie sc1 = scategorieRepository.save(new Scategorie(1L, "scat1", "scat1", c1));
        Scategorie sc2 = scategorieRepository.save(new Scategorie(2L, "scat2", "scat2", c1));
        Scategorie sc3 = scategorieRepository.save(new Scategorie(3L, "scat3", "scat3", c2));
        Scategorie sc4 = scategorieRepository.save(new Scategorie(4L, "scat4", "scat4", c3));

        Produit p1 = produitRepository.save(new Produit(1L, "123456", "prod1", "prod1", 1500.0, 1700.0, 1800.0, 4, 4, sc1));
        Produit p2 = produitRepository.save(new Produit(2L, "234567", "prod2", "prod2", 150.0, 170.0, 180.0, 2, 4, sc3));
        Produit p3 = produitRepository.save(new Produit(3L, "345678", "prod3", "prod3", 15000.0, 17000.0, 18000.0, 6, 4, sc4));
        Produit p4 = produitRepository.save(new Produit(4L, "456789", "prod4", "prod4", 150000.0, 170000.0, 180000.0, 8, 4, sc2));


        Client cl1 = clientRepository.save(new Client(1L, "1234", "cl1", "cl1", "cl1", "cl1", "cl1", "cl1"));
        Client cl2 = clientRepository.save(new Client(2L, "1235", "cl2", "cl2", "cl2", "cl2", "cl2", "cl2"));
        Client cl3 = clientRepository.save(new Client(3L, "1237", "cl3", "cl3", "cl3", "cl3", "cl3", "cl3"));
        Client cl4 = clientRepository.save(new Client(4L, "1238", "cl4", "cl4", "cl4", "cl4", "cl4", "cl4"));


        Role vendorRole = roleRepository.save(new Role(RoleName.ROLE_VENDEUR));
        Role gerantRole = roleRepository.save(new Role(RoleName.ROLE_GERANT));
        Role associeRole = roleRepository.save(new Role(RoleName.ROLE_ASSOCIE));
        Role managerRole = roleRepository.save(new Role(RoleName.ROLE_MANAGER));
        Role adminRole = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        Utilisateur admin = new Utilisateur();
        admin.setId(1L);
        admin.setUsername("Admin");
        admin.setName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setActive(true);
        admin.setPassword(bCryptPasswordEncoder.encode("Admin123456"));
        utilisateurRepository.save(admin);

        utilisateurService.addRoleToUser("Admin", RoleName.ROLE_ADMIN);

        Utilisateur manager = new Utilisateur();
        admin.setId(1L);
        admin.setUsername("Manager");
        admin.setName("Manager");
        admin.setEmail("manager@gmail.com");
        admin.setActive(true);
        admin.setPassword(bCryptPasswordEncoder.encode("Manager123456"));
        utilisateurRepository.save(manager);
        utilisateurService.addRoleToUser("Manager", RoleName.ROLE_MANAGER);


    }
}
