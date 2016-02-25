package com.p0p0lam.back.exrate.service;

import com.p0p0lam.back.exrate.BaseTest;
import com.p0p0lam.back.exrate.config.AppConfig;
import com.p0p0lam.back.exrate.model.net.RatesResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Sergey on 25.02.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class BusinessServiceTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(BusinessServiceTest.class);

    @Autowired
    BusinessService businessService;

    @Test
    public void testGetRates() throws Exception {
        RatesResponse response = businessService.getRates(0,10, POINT.getY(), POINT.getX(), (int) DISTANCE_MAX.getValue(), "BYR", "ua");
        assertTrue(response.getAllCount()>0);
    }
}