package com.p0p0lam.back.exrate.controller;

import com.p0p0lam.back.exrate.model.finance.Organization;
import com.p0p0lam.back.exrate.model.net.RatesResponse;
import com.p0p0lam.back.exrate.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Sergey on 23.02.2016.
 */
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatesController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/getRates", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RatesResponse getRates(@RequestParam int start, @RequestParam int count, @RequestParam double lat, @RequestParam double lng, @RequestParam String currency, @RequestParam(required = false, defaultValue = "5") int maxDistance ){
        logger.info("Start getRates request. start: {}, count: {}, latitude: {}, longitude: {}, Currency: {}, maxDistance: {}", start, count, lat, lng, currency, maxDistance);
        return businessService.getRates(start, count, lat, lng, maxDistance, currency);
    }
}
