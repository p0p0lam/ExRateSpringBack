package com.p0p0lam.back.exrate.model.net;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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
    public Map<String, String> currencies;

    @SerializedName("location")
    public Map<String, String> locations;

    @SerializedName("org_type")
    public Map<Integer, String> orgTypes;

    @SerializedName("city")
    public Map<String, String> cities;

    @Override
    public String toString() {
        return "Response{" +
                "date='" + date + '\'' +
                ", lastChangesDate='" + lastChangesDate + '\'' +
                '}';
    }
}
