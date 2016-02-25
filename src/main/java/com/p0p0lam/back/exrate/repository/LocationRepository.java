package com.p0p0lam.back.exrate.repository;

import com.p0p0lam.back.exrate.model.LocationDBO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Sergey on 23.02.2016.
 */
public interface LocationRepository extends MongoRepository<LocationDBO, String> {
}
