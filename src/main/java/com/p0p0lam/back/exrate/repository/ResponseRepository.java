package com.p0p0lam.back.exrate.repository;

import com.p0p0lam.back.exrate.model.CityDBO;
import com.p0p0lam.back.exrate.model.ResponseDBO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Sergey on 22.02.2016.
 */
public interface ResponseRepository extends MongoRepository<ResponseDBO, String> {
}
