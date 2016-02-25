package com.p0p0lam.back.exrate.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Sergey on 22.02.2016.
 */
@Document(collection = "cities")
public class CityDBO extends IdNameDBO{

    public CityDBO(String id, String name) {
        super(id, name);
    }
}
