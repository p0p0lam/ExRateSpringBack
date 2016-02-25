package com.p0p0lam.back.exrate.model.finance;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (ask != null ? !ask.equals(rate.ask) : rate.ask != null) return false;
        return !(bid != null ? !bid.equals(rate.bid) : rate.bid != null);

    }

    @Override
    public int hashCode() {
        int result = ask != null ? ask.hashCode() : 0;
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        return result;
    }
}
