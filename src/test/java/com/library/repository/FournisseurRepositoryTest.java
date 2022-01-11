package com.library.repository;

import com.library.entities.Fournisseur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FournisseurRepositoryTest {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Test
    @Rollback(false)
    public void testCreateFournisseur() {
        Fournisseur fournisseur = new Fournisseur(null, "L", "Diop", "D2020", "ZG", "Cmpt1", "Add1", "77459043", "77459043", "D52020", "D@gmail.com");

        Fournisseur saveFournisseur = fournisseurRepository.save(fournisseur);

        assertNotNull(saveFournisseur);
    }

    @Test
    public void testFindFournisseurById() {
        long fourId = 2;
        boolean isFournisseurExist = fournisseurRepository.findById(fourId).isPresent();

        assertTrue(isFournisseurExist);
    }

    @Test
    public void testFindFournisseurByCode() {
        String code = "F1";
        Fournisseur fournisseur = fournisseurRepository.findByCode(code);

        assertThat(fournisseur.getCode()).isEqualTo(code);
    }

    @Test
    public void testFindFournisseurByNom() {
        String nom = "F1";
        Fournisseur fournisseur = fournisseurRepository.findByCode(nom);

        assertThat(fournisseur.getCode()).isEqualTo(nom);
    }

    @Test
    @Rollback(false)
    public void TestUpdateFournisseur() {
        String fournisseurCode = "F5";
        String fournisseurNom = "Diallo";
        String fournisseurEmail = "diallo@gmail.com";

        Fournisseur fournisseur = new Fournisseur(null, fournisseurCode, "D2020", "DK", "Bank1", "Cmpt1", "Add1", "77459043", "77459043", "D52020", fournisseurEmail);

        fournisseur.setId((long) 3);
        fournisseurRepository.save(fournisseur);

        Fournisseur fournisseurUpdate = fournisseurRepository.findByCode(fournisseurCode);

        assertThat(fournisseurUpdate.getCode()).isEqualTo(fournisseurCode);

    }

    @Test
    @Rollback(false)
    public void testDeleteFournisseur() {
        Long id = (long) 11;

        boolean isExistBeforeDelete = fournisseurRepository.findById(id).isPresent();

        fournisseurRepository.deleteById(id);

        boolean notExistAfterDelete = fournisseurRepository.findById(id).isPresent();

        assertTrue(isExistBeforeDelete);

        assertFalse(notExistAfterDelete);
    }

    @Test
    public void testListFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();

        for (Fournisseur fournisseur : fournisseurs) {
            System.out.println(fournisseur);
        }
        assertThat(fournisseurs).size().isGreaterThan(0);
    }

    @Test
    public void testListFindFournisseurByCode() {
        String code = "F1";

        List<Fournisseur> fournisseurs = fournisseurRepository.ListFournisseurByCode("%" + code + "%");
        List<Fournisseur> fournisseurList = new ArrayList<Fournisseur>();

        for (Fournisseur fournisseur : fournisseurs) {
            fournisseurList.add(fournisseur);
        }
        assertThat(fournisseurList.size()).isEqualTo(1);
        //assertThat(categoriesList.size()).isGreaterThan(0);

    }


}
