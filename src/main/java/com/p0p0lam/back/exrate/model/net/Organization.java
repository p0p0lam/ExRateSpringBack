package com.p0p0lam.back.exrate.model.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 22.02.2016.
 */
public class Organization {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("coords")
    @Expose
    private List<Double> coords = new ArrayList<Double>();
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("currency")
    @Expose
    private List<Currency> currency = new ArrayList<Currency>();
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("link")
    @Expose
    private String link;
}
