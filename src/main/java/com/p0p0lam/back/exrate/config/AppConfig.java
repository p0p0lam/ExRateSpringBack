package com.p0p0lam.back.exrate.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Sergey on 22.02.2016.
 */
@Configuration
@ComponentScan({"com.p0p0lam.back.exrate.controller"})
@EnableWebMvc
public class AppConfig {
}
