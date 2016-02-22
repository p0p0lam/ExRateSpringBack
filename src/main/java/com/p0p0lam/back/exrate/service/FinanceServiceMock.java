package com.p0p0lam.back.exrate.service;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.p0p0lam.back.exrate.model.net.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Sergey on 22.02.2016.
 */
@Service
public class FinanceServiceMock implements FinanceService {
    private static final Logger logger = LoggerFactory.getLogger(FinanceServiceMock.class);
    @Autowired
    Gson gson;

    @Override
    public Response getData() {
        logger.info("Loading data from finance mock");
        Resource resource = new ClassPathResource("finance.json");
        try {
            Response response = gson.fromJson(new JsonReader(new InputStreamReader(resource.getInputStream())), Response.class);
            logger.info("Got data");
            return response;
        } catch (IOException e){
            logger.error("Can't open json", e);
        } catch (JsonParseException e){
            logger.error("Can't parse json", e);
        }
        return null;
    }
}
