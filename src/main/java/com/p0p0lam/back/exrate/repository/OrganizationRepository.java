package com.p0p0lam.back.exrate.repository;

import com.p0p0lam.back.exrate.model.OrganizationDBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Created by Sergey on 23.02.2016.
 */
public interface OrganizationRepository extends MongoRepository<OrganizationDBO, String> {
        Page<OrganizationDBO> findByLegacyCoordsNear(Point point, Distance distanceMax, Pageable page);
        GeoPage<OrganizationDBO> findByCoordsNear(Point point, Distance distanceMax, Pageable page);
        GeoPage<OrganizationDBO> findByCoordsNearAndCurrency_CurrencyId(String currencyId, Point point, Distance distanceMax, Pageable page);
}
