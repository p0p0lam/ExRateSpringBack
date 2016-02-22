package com.p0p0lam.back.exrate.repository;

import com.p0p0lam.back.exrate.model.CityDBO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sergey on 22.02.2016.
 */
public interface CityRepository extends MongoRepository<CityDBO, String> {
}
