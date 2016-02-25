package com.p0p0lam.back.exrate.repository;

import com.p0p0lam.back.exrate.model.CurrencyDBO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Sergey on 23.02.2016.
 */
public interface CurrencyRepository extends MongoRepository<CurrencyDBO, String> {
}
