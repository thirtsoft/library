package com.library.services;

import com.library.entities.Fournisseur;
import com.library.repository.FournisseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FournisseurServiceTest {

    @Autowired
    private FournisseurService fournisseurService;

    @MockBean
    private FournisseurRepository fournisseuRepository;

    @Test
    public void testCreateFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setCode("FFF");
        fournisseur.setNom("FFFF");
        fournisseur.setAdresse("FFFF");

        Mockito.when(fournisseuRepository.save(fournisseur)).thenReturn(fournisseur);

        assertThat(fournisseurService.saveFournisseur(fournisseur)).isEqualTo(fournisseur);
    }

    @Test
    public void testFindFournisseurByCode() {

        Fournisseur fournisseur = new Fournisseur(null, "L", "Diop", "D2020", "ZG", "Bank1", "Cmpt1", "Add1", "77459043", "77459043", "D52020", "D@gmail.com");

        when(fournisseuRepository.findByCode(fournisseur.getCode())).thenReturn(fournisseur);

        Fournisseur frt = fournisseurService.findByCode(fournisseur.getCode());

        assertNotNull(frt);
        assertThat(frt.getCode()).isEqualTo(fournisseur.getCode());

    }

    @Test
    public void testFindFournisseurByNom() {

        Fournisseur fournisseur = new Fournisseur(null, "L", "Diop", "D2020", "ZG", "Bank1", "Cmpt1", "Add1", "77459043", "77459043", "D52020", "D@gmail.com");

        when(fournisseuRepository.findByNom(fournisseur.getNom())).thenReturn(fournisseur);

        Fournisseur frt = fournisseurService.findByNom(fournisseur.getNom());

        assertNotNull(frt);
        assertThat(frt.getNom()).isEqualTo(fournisseur.getNom());

    }


    @Test
    public void testAllFournisseurs() {

        when(fournisseuRepository.findAll()).thenReturn(Stream
                .of(new Fournisseur(null, "L", "Diop", "D2020", "ZG", "Bank1", "Cmpt1", "Add1", "77459043", "77459043", "D52020", "D@gmail.com"),
                        new Fournisseur(null, "L", "Diop", "D2020", "ZG", "Bank1", "Cmpt1", "Add1", "77459043", "77459043", "D52020", "D@gmail.com")).collect(Collectors.toList()));
        assertEquals(2, fournisseurService.findAllFournisseurs().size());
    }


}
