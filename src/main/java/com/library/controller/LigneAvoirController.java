package com.library.controller;

import com.library.entities.LigneCmdClient;
import com.library.exceptions.ResourceNotFoundException;
import com.library.services.LigneCmdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/alAmine")
public class LigneAvoirController {
	
	@Autowired
	private LigneCmdClientService ligneCmdClientService;
	

}
