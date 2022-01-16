package com.library.repository;

import com.library.entities.Client;
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
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Rollback(false)
    public void testCreateClient() {
        Client client = new Client(1L, "Dieng", "Sidiya", "Mbao", "77459043", "775643219", "seydou@gmail.com");

        Client saveClient = clientRepository.save(client);

        assertNotNull(saveClient);
    }

    @Test
    public void testFindClientById() {
        long clientId = 2;
        boolean isClientExist = clientRepository.findById(clientId).isPresent();

        assertTrue(isClientExist);
    }

    @Test
    public void testFindClientByRaisonSocialNotExist() {
        String raisonSocial = "Ndiaye";
        Client client = clientRepository.findByRaisonSocial(raisonSocial);

        assertNull(client);
    }


    @Test
    @Rollback(false)
    public void testUpdateClient() {
        String clientRaisonSocial = "Wade";
        String clientChefService = "Wade";
        String clientAdress = "DK";
        String clientEmail = "adama@gmail.com";

        Client client = new Client(1L, clientRaisonSocial, clientChefService, clientAdress,
                "775643219", "77459043", clientEmail);

        client.setId((long) 11);
        clientRepository.save(client);

        Client clientUpdate = clientRepository.findByRaisonSocial(clientRaisonSocial);

        assertThat(clientUpdate.getRaisonSocial()).isEqualTo(clientRaisonSocial);

    }

    @Test
    @Rollback(false)
    public void testDeleteClient() {
        Long id = (long) 15;

        boolean isExistBeforeDelete = clientRepository.findById(id).isPresent();

        clientRepository.deleteById(id);

        boolean notExistAfterDelete = clientRepository.findById(id).isPresent();

        assertTrue(isExistBeforeDelete);

        assertFalse(notExistAfterDelete);
    }

    @Test
    public void testListClients() {
        List<Client> clients = clientRepository.findAll();

        for (Client client : clients) {
            System.out.println(client);
        }
        assertThat(clients).size().isGreaterThan(0);
    }

    @Test
    public void testListFindClientByRaisonSocial() {
        String raisonSocial = "CL";

        List<Client> clients = clientRepository.ListClientByRaisonSocial("%" + raisonSocial + "%");
        List<Client> clientList = new ArrayList<Client>();

        for (Client client : clients) {
            clientList.add(client);
        }
        assertThat(clientList.size()).isBetween(1, 4);
        //assertThat(clientList.size()).isGreaterThan(0);

    }


}
