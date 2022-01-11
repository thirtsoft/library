package com.library.services;

import com.library.entities.Employe;
import com.library.entities.Versement;
import com.library.repository.EmployeRepository;
import com.library.repository.VersementRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VersementServiceTest {

    @Autowired
    private VersementService versementService;

    @MockBean
    private VersementRepository versementRepository;

    @MockBean
    private EmployeRepository employeRepository;


    @Test
    public void testCreateVersement() {
        Versement versement = new Versement();

        versement.setNumVersement("VER222");
        versement.setMontantVersement(250000.0);

        Mockito.when(versementRepository.save(versement)).thenReturn(versement);

        assertThat(versementService.saveVersement(versement)).isEqualTo(versement);
    }

    @Test
    public void testFindVersementByNumVersement() {

        Employe employe = new Employe(1L, "Diallo", "Saliou Woury", "1989d", "B", "776543219", "776543219", "ad@gmail.com");

        Versement versement = new Versement(1L, "Vers08", "Recu", 2000000.0, "BCEAO", "ver_1.png", new Date(), employe);

        when(versementRepository.findByNumVersement(versement.getNumVersement())).thenReturn(versement);

        Versement vers = versementService.findByNumVersement(versement.getNumVersement());

        assertNotNull(vers);
        assertThat(vers.getNumVersement()).isEqualTo(versement.getNumVersement());

    }


    @Test
    public void testfindAllVersements() {
        Employe emp = new Employe(null, "Emp", "Emp", "Empl", "Emp", "Emp", "Emp", "Emp");
        when(versementRepository.findAll()).thenReturn(Stream
                .of(new Versement(1L, "Vers08", "Recu", 2000000.0, "Ver", "ver_1.png", new Date(), emp),
                        new Versement(2L, "Vers08", "Recu", 2000000.0, "Ver", "ver_2.png", new Date(), emp)).collect(Collectors.toList()));
        assertEquals(2, versementService.findAllVersements().size());
    }


}
