package com.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "categorieCharge")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorieCharge extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String codeCategorieCharge;

    @Column(length = 100, unique = true)
    private String nomCategorieCharge;

}
