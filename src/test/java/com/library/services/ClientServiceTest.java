package com.library.services;

import com.library.entities.Client;
import com.library.repository.ClientRepository;
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
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void testCreateClient() {
        Client client = new Client();
        client.setRaisonSocial("CL22");
        client.setAdresse("HHHHH");

        Mockito.when(clientRepository.save(client)).thenReturn(client);

        assertThat(clientService.saveClient(client)).isEqualTo(client);
    }

    @Test
    public void testFindClientByRaisonSocial() {

        Client client = new Client(1L, "Dieng", "Sidiya", "Mbao", "775643219", "775643219", "seydou@gmail.com");

        when(clientRepository.findByRaisonSocial(client.getRaisonSocial())).thenReturn(client);

        Client clt = clientService.findByRaisonSocial(client.getRaisonSocial());

        assertNotNull(clt);
        assertThat(clt.getRaisonSocial()).isEqualTo(client.getRaisonSocial());

    }

    @Test
    public void testAllClients() {
        when(clientRepository.findAll()).thenReturn(Stream
                .of(new Client(1L, "CL", "CL", "CL", "CL", "CL", "CL"),
                        new Client(2L, "CL", "CL", "CL", "CL", "CL", "CL")).collect(Collectors.toList()));
        assertEquals(2, clientService.findAllClient().size());
    }


}
