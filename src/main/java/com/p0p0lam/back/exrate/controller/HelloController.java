package com.p0p0lam.back.exrate.controller;

import com.p0p0lam.back.exrate.model.CityDBO;
import com.p0p0lam.back.exrate.model.net.Response;
import com.p0p0lam.back.exrate.repository.CityRepository;
import com.p0p0lam.back.exrate.service.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 22.02.2016.
 */
@RestController
@RequestMapping(value = "/api")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    CityRepository cityRepository;

    @Autowired
    FinanceService financeService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "Hello my friend";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateLast(){
        List<CityDBO> cities = cityRepository.findAll();
        if (cities==null || cities.isEmpty()){
            logger.info("Cities is empty. Loading from finance service");
            Response response = financeService.getData();
            if (response!=null) {
                cities = new ArrayList<>(response.cities.size());
                for (String id : response.cities.keySet()) {
                    cities.add(new CityDBO(id, response.cities.get(id)));
                }
                logger.info("Saving {} cities", cities.size());
                cityRepository.save(cities);
            }
        } else {
            logger.info("Cities already saved in database");
        }
        return "updated";
    }
}
