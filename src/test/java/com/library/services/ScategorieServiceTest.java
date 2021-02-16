package com.library.services;

import com.library.entities.Category;
import com.library.entities.Scategorie;
import com.library.repository.ScategorieRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ScategorieServiceTest {

    @Autowired
    private ScategorieService scategorieService;

    @MockBean
    private ScategorieRepository scategorieRepository;

    @Test
    public void testCreateScategorie() {

        Category category = new Category(null, "PC", "PCMobile");

        Scategorie scategorie = new Scategorie();
        scategorie.setCode("SCAT");
        scategorie.setLibelle("SCAT");

        Mockito.when(scategorieRepository.save(scategorie)).thenReturn(scategorie);

        assertThat(scategorieService.saveScategorie(scategorie)).isEqualTo(scategorie);
    }


    @Test
    public void testFindScategorieByCode() {

        Category category = new Category(null, "Bureau", "Chaise Roulante");

        Scategorie scategory = new Scategorie(null, "CAT", "CATACAT", category);

        when(scategorieRepository.findByCode(scategory.getCode())).thenReturn(scategory);

        Scategorie scat = scategorieService.findByCode(scategory.getCode());

        assertNotNull(scat);
        assertThat(scat.getCode()).isEqualTo(scategory.getCode());

    }

    @Test
    public void testFindScategorieByLibelle() {

        Category category = new Category(null, "Bureau", "Chaise Roulante");

        Scategorie scategory = new Scategorie(null, "CAT", "CATACAT", category);

        when(scategorieRepository.findByLibelle(scategory.getLibelle())).thenReturn(scategory);

        Scategorie scat = scategorieService.findByLibelle(scategory.getLibelle());

        assertNotNull(scat);
        assertThat(scat.getLibelle()).isEqualTo(scategory.getLibelle());

    }


    @Test
    public void testFindAllScategories() {
        Category category = new Category(null, "Bureau", "Chaise Roulante");

        when(scategorieRepository.findAll()).thenReturn(Stream
                .of(new Scategorie(null, "CAT", "CATACAT", category),
                        new Scategorie(null, "Samsung", "Samsung 120", category)).collect(Collectors.toList()));
        assertEquals(2, scategorieService.findAllScategories().size());
    }


}
