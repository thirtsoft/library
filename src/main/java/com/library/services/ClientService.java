package com.library.services;

import com.library.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> findAllClient();

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

    Page<Client> findAllClientByPage(Pageable page);

    Page<Client> findClientByKeyWord(String mc, Pageable pageable);

    Long countNumberOfClient();

    Client updateClientByEmail(String email, String id);

}
