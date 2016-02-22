package com.p0p0lam.back.exrate.model.net;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergey on 22.02.2016.
 */
public class Rate {
    @SerializedName("ask")
    @Expose
    public String ask;
    @SerializedName("bid")
    @Expose
    public String bid;
}
