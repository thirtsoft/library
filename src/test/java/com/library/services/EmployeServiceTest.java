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

import com.library.entities.Employe;
import com.library.repository.EmployeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceTest {
	
	@Autowired
	private EmployeService employeService;
	
	@MockBean
	private EmployeRepository employeRepository;
	
	@Test
	public void testCreateEmploye() {
		Employe employe = new Employe();
		employe.setCni("1234CNI");
		employe.setNom("Emp1");
		employe.setPrenom("Emp1");
		
		Mockito.when(employeRepository.save(employe)).thenReturn(employe);
		
		assertThat(employeService.saveEmploye(employe)).isEqualTo(employe);
	}
	
	
	@Test
	public void testAllEmployees() {
		when(employeRepository.findAll()).thenReturn(Stream
				.of(new Employe(null, "Emp", "Emp","Empl","Emp","Emp","Emp"), 
			new Employe(null, "Emp", "Emp","Empl","Emp","Emp","Emp")).collect(Collectors.toList()));
		assertEquals(2, employeService.findAllEmploye().size());
	}
	
	

}
