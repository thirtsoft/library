package com.library.services;

import com.library.entities.Client;
import com.library.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAllClient();

    List<Client> findAllClientsOrderDesc();

    Client saveClient(Client client);

    Optional<Client> findClientById(Long id);

    Client updateClient(Long id, Client client);

    void deleteClient(Long id);

    Client findByRaisonSocial(String raisonSocial);

    Client findByChefService(String chefService);

    Client findByEmail(String email);

    Client findByTelephone(String telephone);

    Client findByCodeClient(String codeClient);

    List<Client> ListClientByRaisonSocial(String raisonSocial);

    List<Client> ListClientByChefService(String chefService);

    List<Object[]> ListClientGroupByRaisonSocial();

    Long countNumberOfClient();

    Client updateClientByEmail(String email, String id);

}
