package com.p0p0lam.back.exrate.model.net;

import com.p0p0lam.back.exrate.model.OrganizationDBO;
import org.springframework.data.geo.GeoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 23.02.2016.
 */
public class RatesResponse {
    private double maxBidRate;
    private double minBidRate;
    private double maxAskRate;
    private double minAskRate;
    private int allCount;
    private List<Rate> rates = new ArrayList<>();

    public double getMaxBidRate() {
        return maxBidRate;
    }

    public void setMaxBidRate(double maxBidRate) {
        this.maxBidRate = maxBidRate;
    }

    public double getMaxAskRate() {
        return maxAskRate;
    }

    public void setMaxAskRate(double maxAskRate) {
        this.maxAskRate = maxAskRate;
    }

    public double getMinBidRate() {
        return minBidRate;
    }

    public void setMinBidRate(double minBidRate) {
        this.minBidRate = minBidRate;
    }

    public double getMinAskRate() {
        return minAskRate;
    }

    public void setMinAskRate(double minAskRate) {
        this.minAskRate = minAskRate;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
