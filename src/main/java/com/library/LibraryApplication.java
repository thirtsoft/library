package com.library;

import com.library.entities.Category;
import com.library.entities.Client;
import com.library.entities.Produit;
import com.library.entities.Scategorie;
import com.library.repository.CategoryRepository;
import com.library.repository.ClientRepository;
import com.library.repository.ProduitRepository;
import com.library.repository.ScategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ScategorieRepository scategorieRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Category c1 = categoryRepository.save(new Category(null,"cat1", "cat1"));
        Category c2 = categoryRepository.save(new Category(null,"cat2", "cat2"));
        Category c3 = categoryRepository.save(new Category(null,"cat3", "cat3"));

        Scategorie sc1 = scategorieRepository.save(new Scategorie(null,"scat1","scat1",c1));
        Scategorie sc2 = scategorieRepository.save(new Scategorie(null,"scat2","scat2",c1));
        Scategorie sc3 = scategorieRepository.save(new Scategorie(null,"scat3","scat3",c2));
        Scategorie sc4 = scategorieRepository.save(new Scategorie(null,"scat4","scat4",c3));

        Produit p1= produitRepository.save(new Produit(null,"prod1","prod1", 1500.0,1700.0,1800.0,2.0, 4, 4,true,"photo",new Date(),c1,sc1));
        Produit p2= produitRepository.save(new Produit(null,"prod2","prod2", 150.0,170.0,180.0,1.0, 2, 4,true,"photo",new Date(),c2,sc3));
        Produit p3= produitRepository.save(new Produit(null,"prod3","prod3", 15000.0,17000.0,18000.0,3.0, 6, 4,true,"photo",new Date(),c3,sc4));
        Produit p4= produitRepository.save(new Produit(null,"prod4","prod4", 150000.0,170000.0,180000.0,4.0, 8, 4,true,"photo",new Date(),c1,sc2));

        Client cl1 = clientRepository.save(new Client(null, 1234, "cl1","cl1","cl1","cl1","cl1"));
        Client cl2 = clientRepository.save(new Client(null, 1234,"cl2","cl2","cl2","cl2","cl2"));
        Client cl3 = clientRepository.save(new Client(null, 1234, "cl3","cl3","cl3","cl3","cl3"));
        Client cl4 = clientRepository.save(new Client(null, 1234,"cl4","cl4","cl4","cl4","cl4"));


    }
}
