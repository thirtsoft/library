package com.library.jasper;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class Base {

    public abstract Long getId();

    @Column(name = "CREATED_DATE")
    private Date createdDate;
}
