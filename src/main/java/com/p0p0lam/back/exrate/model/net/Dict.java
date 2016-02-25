package com.p0p0lam.back.exrate.model.net;

/**
 * Created by Sergey on 25.02.2016.
 */
public class Dict {
    private String id;
    private String name;

    public Dict(String id, String name) {
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
