package com.p0p0lam.back.exrate.model;

import org.springframework.data.annotation.Id;

/**
 * Created by Sergey on 23.02.2016.
 */
public class IdNameDBO {
    @Id
    private String id;
    private String name;
    private String nameRu;

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

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdNameDBO idNameDBO = (IdNameDBO) o;

        if (!id.equals(idNameDBO.id)) return false;
        return name.equals(idNameDBO.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
