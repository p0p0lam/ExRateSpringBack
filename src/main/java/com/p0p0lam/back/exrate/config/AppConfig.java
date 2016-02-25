package com.p0p0lam.back.exrate.config;

import com.google.gson.Gson;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergey on 22.02.2016.
 */
@Configuration
@ComponentScan({"com.p0p0lam.back.exrate.controller","com.p0p0lam.back.exrate.service"})
@EnableWebMvc
@Import({MongoDbConfig.class})
@EnableAsync
public class AppConfig extends WebMvcConfigurerAdapter implements AsyncConfigurer {

    @Override
    @Bean(name = "asyncTaskExecutor")
    public ThreadPoolExecutor getAsyncExecutor() {
        return(ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    @Bean
    public Gson getGson(){
        return new Gson();
    }

    @Bean
    public RestTemplate financeRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout((int)TimeUnit.SECONDS.toMillis(30));
        factory.setConnectTimeout((int)TimeUnit.SECONDS.toMillis(30));
        RestTemplate rt = new RestTemplate(factory);
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(getGson());
        rt.getMessageConverters().add(converter);
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        return rt;
    }
}
