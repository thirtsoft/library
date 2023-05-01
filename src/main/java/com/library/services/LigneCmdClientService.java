package com.library.services;

import com.library.entities.LigneCmdClient;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface LigneCmdClientService {

    List<LigneCmdClient> findAllLigneCmdClient();

    List<LigneCmdClient> findAllLigneCmdClientsOrderDesc();

    Optional<LigneCmdClient> findLigneCmdClientById(Long lCmdId);

    LigneCmdClient saveLigneCmdClient(LigneCmdClient ligneCmdClient);

    LigneCmdClient updateLigneCmdClient(Long lCmdId, LigneCmdClient ligneCmdClient);

    ResponseEntity<Object> deleteLigneCmdClient(Long id);

    void deleteLcomByNumero(Long numero);
    //public LigneCmdClient findByQuantite(int quantite);

    //public List<LigneCmdClient> findListLigneCmdClientByQuantite(int quantite);
    List<LigneCmdClient> findLigneCmdClientByProduitId(Long prodId);

    List<LigneCmdClient> findLigneCmdClientByCommandeClientId(Long comId);

    List<LigneCmdClient> findAllLcomByNumero(Long numero);

    List<LigneCmdClient> saveListLigneCmd(List<LigneCmdClient> lcmdClients);
}
