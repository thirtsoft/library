package com.library.services.impl;

import com.library.entities.Versement;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.VersementRepository;
import com.library.services.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VersementServiceImpl implements VersementService {

    @Autowired
    private VersementRepository versementRepository;

    @Override
    public List<Versement> findAllVersements() {
        return versementRepository.findAll();
    }

    @Override
    public List<Versement> findAllVersementsOrderDesc() {
        return versementRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<Versement> findVersementById(Long id) {
        if (!versementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Versement Not found");
        }

        return versementRepository.findById(id);
    }

    @Override
    public Versement findByNumVersement(String numVersement) {
        return versementRepository.findByNumVersement(numVersement);
    }

    @Override
    public Versement findByNumeroRecu(String numeroRecu) {
        return versementRepository.findByNumeroRecu(numeroRecu);
    }

    @Override
    public Versement findByNature(String nature) {
        return versementRepository.findByNature(nature);
    }

    @Override
    public List<Versement> findListVersementByNumVersement(String numVersement) {
        return versementRepository.findListVersementByNumVersement(numVersement);
    }

    @Override
    public List<Versement> findListVersementByNature(String nature) {
        return versementRepository.findListVersementNature(nature);
    }

    @Override
    public List<Versement> findVersementByEmployeId(Long empId) {
        return versementRepository.findVersementByEmployeId(empId);
    }

    @Override
    public Versement saveVersement(Versement versement) {
        versement.setDateVersement(new Date());

        return versementRepository.save(versement);
    }

    @Override
    public Versement updateVersement(Long id, Versement versement) {
        if (!versementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Versement Not found");
        }
        Optional<Versement> versment = versementRepository.findById(id);
        if (!versment.isPresent()) {
            throw new ResourceNotFoundException("Versment Not found");
        }

        Versement versementResult = versment.get();

        versementResult.setNumVersement(versement.getNumVersement());
        versementResult.setNature(versement.getNature());
        versementResult.setMontantVersement(versement.getMontantVersement());
        versementResult.setDateVersement(versement.getDateVersement());


        return versementRepository.save(versementResult);
    }

    @Override
    public void deleteVersement(Long id) {
        if (!versementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Versement not found");
        }

        versementRepository.deleteById(id);
    }


}
