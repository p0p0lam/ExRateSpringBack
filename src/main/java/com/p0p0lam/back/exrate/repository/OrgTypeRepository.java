package com.p0p0lam.back.exrate.repository;

import com.p0p0lam.back.exrate.model.OrgTypeDBO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Sergey on 23.02.2016.
 */
public interface OrgTypeRepository extends MongoRepository<OrgTypeDBO, String> {
}
