package com.p0p0lam.back.exrate.service;

import com.p0p0lam.back.exrate.model.finance.Organization;
import com.p0p0lam.back.exrate.model.net.RatesResponse;

import java.util.List;

/**
 * Created by Sergey on 23.02.2016.
 */
public interface BusinessService {
    RatesResponse getRates(int start, int count, double lat, double lng, int distance, String currency, String language);

}
