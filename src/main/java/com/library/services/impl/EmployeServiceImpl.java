package com.library.services.impl;

import com.library.entities.Employe;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.EmployeRepository;
import com.library.services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public List<Employe> findAllEmploye() {
        return employeRepository.findAll();
    }

    @Override
    public Optional<Employe> findEmployeById(Long empId) {
        if (!employeRepository.existsById(empId)) {
            throw new ResourceNotFoundException("Employee N° " + empId + "not found");
        }

        return employeRepository.findById(empId);
    }


    @Override
    public Employe saveEmploye(Employe employe) {

        if ((employeRepository.findByEmail(employe.getEmail()) != null)) {
            throw new IllegalArgumentException("Ce Employe exist");
        }
        return employeRepository.save(employe);
    }


    @Override
    public Employe updateEmploye(Long empId, Employe employe) {
        if (!employeRepository.existsById(empId)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        Optional<Employe> emp = employeRepository.findById(empId);
        if (!emp.isPresent()) {
            throw new ResourceNotFoundException("Employee not found");
        }

        Employe empResult = emp.get();

        empResult.setCni(employe.getCni());
        empResult.setNom(employe.getNom());
        empResult.setPrenom(employe.getPrenom());
        empResult.setAdresse(employe.getAdresse());
        empResult.setTelephone(employe.getTelephone());
        empResult.setTelephone2(employe.getTelephone2());
        empResult.setEmail(employe.getEmail());

        return employeRepository.save(empResult);
    }

    @Override
    public void deleteEmploye(Long empId) {
        if (!employeRepository.existsById(empId)) {
            throw new ResourceNotFoundException("Employe N° " + empId + "not found");
        }
        employeRepository.deleteById(empId);

    }

    @Override
    public Employe findByCni(String cni) {
        return employeRepository.findByCni(cni);
    }

    @Override
    public Employe findByNom(String nom) {
        return employeRepository.findByNom(nom);
    }

    @Override
    public Employe findByPrenom(String prenom) {
        return employeRepository.findByPrenom(prenom);
    }

    @Override
    public Employe findByEmail(String email) {
        return employeRepository.findByEmail(email);
    }

    @Override
    public Employe findByTelephone(String telephone) {
        return employeRepository.findByTelephone(telephone);
    }

    @Override
    public List<Employe> ListEmployeByCni(String cni) {
        return employeRepository.ListEmployeByCni(cni);
    }

    @Override
    public List<Employe> ListEmployeByNom(String nom) {
        return employeRepository.ListEmployeByNom(nom);
    }

    @Override
    public List<Employe> ListEmployeByPrenom(String prenom) {
        return employeRepository.ListEmployeByPrenom(prenom);
    }

    @Override
    public List<Employe> ListEmployeByEmail(String email) {
        return employeRepository.ListEmployeByEmail(email);
    }

    @Override
    public List<Employe> ListEmployeByTelephone(String telephone) {
        return employeRepository.ListEmployeByTelephone(telephone);
    }


}
