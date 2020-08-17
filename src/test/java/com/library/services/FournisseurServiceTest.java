package com.library.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.entities.Fournisseur;
import com.library.repository.FournisseurRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FournisseurServiceTest {
	
	@Autowired
	private FournisseurService fournisseurService;
	
	@MockBean
	private FournisseurRepository fournisseuRepository;
	
	@Test
	public void testCreateFournisseur() {
		Fournisseur fournisseur = new Fournisseur();
		fournisseur.setCode("FFF");
		fournisseur.setNom("FFFF");
		fournisseur.setAdresse("FFFF");
		
		Mockito.when(fournisseuRepository.save(fournisseur)).thenReturn(fournisseur);
		
		assertThat(fournisseurService.saveFournisseur(fournisseur)).isEqualTo(fournisseur);
	}
	
	
	@Test
	public void testAllFournisseurs() {
		when(fournisseuRepository.findAll()).thenReturn(Stream
				.of(new Fournisseur(null, "F", "F","F","F","F","F","F"), 
			new Fournisseur(null, "F", "F","F","F","F","F","F")).collect(Collectors.toList()));
		assertEquals(2, fournisseurService.findAllFournisseurs().size());
	}
	
	

}
