package com.library.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "charge")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Charge extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String codeCharge;

    private String nature;

    private double montantCharge;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "catCharge_id")
    private CategorieCharge categorieCharge;


}
