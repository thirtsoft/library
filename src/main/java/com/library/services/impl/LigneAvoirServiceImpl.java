package com.library.services.impl;

import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.repository.LigneCmdClientRepository;
import com.library.services.LigneAvoirService;
import com.library.services.LigneCmdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LigneAvoirServiceImpl implements LigneAvoirService {



}
