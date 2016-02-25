package com.p0p0lam.back.exrate.controller;

import com.p0p0lam.back.exrate.model.CityDBO;
import com.p0p0lam.back.exrate.model.finance.Response;
import com.p0p0lam.back.exrate.repository.CityRepository;
import com.p0p0lam.back.exrate.service.BusinessService;
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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "Hello my friend";
    }


}
