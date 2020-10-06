package com.library.services;
import com.library.entities.Creance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface CreanceService {

    public List<Creance> findAllCreances();
    public Optional<Creance> findCreanceById(Long id);

    public Creance findByReference(String reference);
    public List<Creance> findListCreanceByReference(String reference);

    public Creance findByLibelle(String libelle);
    public List<Creance> findListCreanceByLibelle(String libelle);


    public List<Creance> findCreanceByClientId(Long clientId);

    public Creance saveCreance(Creance creance);
    public Creance updateCreance(Long id, Creance creance);
    public ResponseEntity<Object> deleteCreance(Long id);


    public Page<Creance> findAllCreancesByPageable(Pageable page);
    public Page<Creance>findAllCreancesByClient(Long clientId, Pageable pageable);

    public Page<Creance> findCreanceByKeyWord(String mc, Pageable pageable);
}
