package com.library.services.impl;

import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneCmdClientRepository;
import com.library.services.LigneCmdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LigneCmdClientServiceImpl implements LigneCmdClientService {

    @Autowired
    private LigneCmdClientRepository ligneCmdClientRepository;

    @Override
    public List<LigneCmdClient> findAllLigneCmdClient() {
        return ligneCmdClientRepository.findAll();
    }

    @Override
    public List<LigneCmdClient> findAllLigneCmdClientsOrderDesc() {
        return ligneCmdClientRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<LigneCmdClient> findLigneCmdClientById(Long lCmdId) {
        if (!ligneCmdClientRepository.existsById(lCmdId)) {
            throw new ResourceNotFoundException("Ligne Comande N° " + lCmdId + "not found");
        }

        return ligneCmdClientRepository.findById(lCmdId);
    }

    @Override
    public LigneCmdClient saveLigneCmdClient(LigneCmdClient ligneCmdClient) {
        return ligneCmdClientRepository.save(ligneCmdClient);
    }

    @Override
    public LigneCmdClient updateLigneCmdClient(Long lCmdId, LigneCmdClient ligneCmdClient) {
        if (!ligneCmdClientRepository.existsById(lCmdId)) {
            throw new ResourceNotFoundException("Ligne Commande N° " + lCmdId + "not found");
        }
        Optional<LigneCmdClient> lcmdClient = ligneCmdClientRepository.findById(lCmdId);
        if (!lcmdClient.isPresent()) {
            throw new ResourceNotFoundException("Ligne Commande N°" + lCmdId + "not found");
        }

        LigneCmdClient lcmdClientResult = lcmdClient.get();

        lcmdClientResult.setProduit(ligneCmdClient.getProduit());
        lcmdClientResult.setCommande(ligneCmdClient.getCommande());
        lcmdClientResult.setQuantite(ligneCmdClient.getQuantite());

        return ligneCmdClientRepository.save(lcmdClientResult);
    }

    @Override
    public ResponseEntity<Object> deleteLigneCmdClient(Long id) {
        if (!ligneCmdClientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ligne Commande N° " + id + "not found");
        }

        ligneCmdClientRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public void deleteLcomByNumero(Long numero) {
        ligneCmdClientRepository.deleteByNumero(numero);
    }

    @Override
    public List<LigneCmdClient> findLigneCmdClientByProduitId(Long prodId) {
        return ligneCmdClientRepository.ListLigneCmdClientByProduitId(prodId);
    }

    @Override
    public List<LigneCmdClient> findLigneCmdClientByCommandeClientId(Long comId) {
        return ligneCmdClientRepository.ListLigneCmdClientByCommandeClientId(comId);
    }

    @Override
    public List<LigneCmdClient> findAllLcomByNumero(Long numero) {
        return ligneCmdClientRepository.findAllByNumero(numero);
    }

    @Override
    public List<LigneCmdClient> saveListLigneCmd(List<LigneCmdClient> lcmdClients) {
        return ligneCmdClientRepository.saveAll(lcmdClients);
    }

}
