package com.library.services;

import com.library.entities.Client;
import com.library.entities.Contrat;
import com.library.repository.ContratRepository;
import com.library.services.impl.ContratServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ContratServiceTest {

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;


    @Test
    public void testCreateContrat() {
        Contrat contrat = new Contrat();
        contrat.setReference("CONT11");
        contrat.setNature("NatCONT");
        contrat.setDescription("DESCCONT");
        contrat.setContent(new byte[100]);

        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat contratSaved = contratService.saveContrat(contrat);

        assertNotNull(contrat);
        assertThat(contratSaved).isEqualTo(contrat);
    }

    @Test
    public void testFindContratByReference() {

        Client client = new Client(null, "BIC103", "CL", "CL", "CL", "CL", "CL");

        Contrat contrat = new Contrat(null, "Cont1", "Prestation", 1200, "Logiciel", new Date(), new Date(), "fileName", "file", new byte[100], client);

        when(contratRepository.findByReference(contrat.getReference())).thenReturn(contrat);

        Contrat ctrt = contratService.findByReference(contrat.getReference());

        assertNotNull(ctrt);
        assertThat(ctrt.getReference()).isEqualTo(contrat.getReference());

    }

    @Test
    public void testFindContratByNature() {

        Client client = new Client(null, "BIC103", "CL", "CL", "CL", "CL", "CL");

        Contrat contrat = new Contrat(null, "Cont1", "Prestation", 1200, "Logiciel", new Date(), new Date(), "fileName", "file", new byte[100], client);

        when(contratRepository.findByNature(contrat.getNature())).thenReturn(contrat);

        Contrat ctrt = contratService.findByNature(contrat.getNature());

        assertNotNull(ctrt);
        assertThat(ctrt.getNature()).isEqualTo(contrat.getNature());

    }


    @Test
    public void testfindAllContrats() {
        Client client = new Client(null, "BIC103", "CL", "CL", "CL", "CL", "CL");
        when(contratRepository.findAll()).thenReturn(Stream
                .of(new Contrat(null, "CONT22", "CONT22", 1200, "CONT22", new Date(), new Date(), "fileName", "file", new byte[100], client),
                        new Contrat(null, "CONT33", "CONT33", 1200, "CONT33", new Date(), new Date(), "fileName", "file", new byte[100], client)).collect(Collectors.toList()));
        assertEquals(2, contratService.findAllContrats().size());
    }

}
