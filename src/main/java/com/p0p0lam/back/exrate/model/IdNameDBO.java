package com.p0p0lam.back.exrate.model;

import org.springframework.data.annotation.Id;

/**
 * Created by Sergey on 23.02.2016.
 */
public class IdNameDBO {
    @Id
    private String id;
    private String name;

    public IdNameDBO() {
    }

    public IdNameDBO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
