package com.library.services;

import com.library.entities.Contrat;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ContratService {

    List<Contrat> findAllContrats();

    Optional<Contrat> findContratById(Long id);

    Contrat findByReference(String reference);

    List<Contrat> findListContratByReference(String reference);

    Contrat findByNature(String nature);

    List<Contrat> findListContratByNature(String nature);

    List<Contrat> findContratByClientId(Long clientId);

    Contrat saveContrat(Contrat contrat);

    Contrat updateContrat(Long id, Contrat contrat);

    void deleteContrat(Long id);

    //   Contrat createContrat(String contrat, MultipartFile fileContrant) throws JsonParseException, JsonMappingException, IOException;

    Contrat createContrat(String contrat, MultipartFile fileContrant) throws IOException;

    Resource loadFileAsResource(String fileName) throws Exception;


}
