package com.p0p0lam.back.exrate.model.finance;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 22.02.2016.
 */
public class Response {
    public String date;
    public String lastChangesDate;

    @SerializedName("organization")
    public List<Organization> organizations = new ArrayList<Organization>();

    @SerializedName("currency")
    public Map<String, String> currencies = new HashMap<>();

    @SerializedName("location")
    public Map<String, String> locations = new HashMap<>();

    @SerializedName("org_type")
    public Map<String, String> orgTypes = new HashMap<>();

    @SerializedName("city")
    public Map<String, String> cities = new HashMap<>();

    @Override
    public String toString() {
        return "Response{" +
                "date='" + date + '\'' +
                ", lastChangesDate='" + lastChangesDate + '\'' +
                '}';
    }
}
