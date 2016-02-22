package com.p0p0lam.back.exrate.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Sergey on 22.02.2016.
 */
@Configuration
@ComponentScan({"com.p0p0lam.back.exrate.controller","com.p0p0lam.back.exrate.service"})
@EnableWebMvc
@Import({MongoDbConfig.class})
public class AppConfig {

    @Bean
    public Gson getGson(){
        return new Gson();
    }
}
