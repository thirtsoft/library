package com.library.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categorieCharge")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorieCharge implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeCategorieCharge;
    private String nomCategorieCharge;
}
