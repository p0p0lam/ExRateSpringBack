package com.p0p0lam.back.exrate.model.finance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;

/**
 * Created by Sergey on 22.02.2016.
 */
public class Currency {
    @SerializedName("currency_id")
    @Expose
    @Id
    public String currencyId;
    @SerializedName("rate")
    @Expose
    public Rate rate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        if (!currencyId.equals(currency.currencyId)) return false;
        return !(rate != null ? !rate.equals(currency.rate) : currency.rate != null);

    }

    @Override
    public int hashCode() {
        int result = currencyId.hashCode();
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        return result;
    }
}
