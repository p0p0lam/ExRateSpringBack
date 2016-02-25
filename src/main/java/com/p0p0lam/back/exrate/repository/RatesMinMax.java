package com.p0p0lam.back.exrate.repository;

import org.springframework.data.annotation.Id;

/**
 * Created by Sergey on 25.02.2016.
 */
public class RatesMinMax {
    @Id
    String id;
    public String currency;
    public double maxAsk;
    public double minAsk;
    public double maxBid;
    public double minBid;

    @Override
    public String toString() {
        return "RatesMinMax{" +
                "minBid=" + minBid +
                ", maxBid=" + maxBid +
                ", minAsk=" + minAsk +
                ", maxAsk=" + maxAsk +
                ", currency='" + currency + '\'' +
                '}';
    }
}
