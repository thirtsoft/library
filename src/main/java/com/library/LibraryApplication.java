package com.library;

import com.library.entities.Category;
import com.library.entities.Client;
import com.library.entities.Produit;
import com.library.entities.Scategorie;
import com.library.repository.*;
import com.library.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    private AccountService accountService;

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

        Produit p1 = produitRepository.save(new Produit(1L, "prod1", "prod1", 1500.0, 1700.0, 1800.0, 4, 4, true, sc1));
        Produit p2 = produitRepository.save(new Produit(2L, "prod2", "prod2", 150.0, 170.0, 180.0, 2, 4, true, sc3));
        Produit p3 = produitRepository.save(new Produit(3L, "prod3", "prod3", 15000.0, 17000.0, 18000.0, 6, 4, true, sc4));
        Produit p4 = produitRepository.save(new Produit(4L, "prod4", "prod4", 150000.0, 170000.0, 180000.0, 8, 4, true, sc2));


        Client cl1 = clientRepository.save(new Client(1L, "1234", "cl1", "cl1", "cl1", "cl1", "cl1", "cl1"));
        Client cl2 = clientRepository.save(new Client(2L, "1235", "cl2", "cl2", "cl2", "cl2", "cl2", "cl2"));
        Client cl3 = clientRepository.save(new Client(3L, "1237", "cl3", "cl3", "cl3", "cl3", "cl3", "cl3"));
        Client cl4 = clientRepository.save(new Client(4L, "1238", "cl4", "cl4", "cl4", "cl4", "cl4", "cl4"));

/*
        accountService.saveUtilisateur(new Utilisateur(null, "admin", "1234", true, null));
        accountService.saveUtilisateur(new Utilisateur(null, "user", "1234", true, null));


        accountService.saveRole(new Role(null, "ADMIN"));
        accountService.saveRole(new Role(null, "USER"));

        accountService.addRoleToUtilisateur("admin", "ADMIN");
        accountService.addRoleToUtilisateur("admin", "USER");
        accountService.addRoleToUtilisateur("user", "USER");
*/

/*
        Role useRole = new Role(RoleName.ROLE_USER);
        Role vendorRole = new Role(RoleName.ROLE_VENDEUR);
        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        roleRepository.save(useRole);
        roleRepository.save(vendorRole);
        roleRepository.save(adminRole);*/


    }
}
