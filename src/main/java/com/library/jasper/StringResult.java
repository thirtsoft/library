package com.library.jasper;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class StringResult {

    private Long id;
    private String name;

    public StringResult() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
