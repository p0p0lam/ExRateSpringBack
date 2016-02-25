package com.p0p0lam.back.exrate.repository;

import com.p0p0lam.back.exrate.BaseTest;
import com.p0p0lam.back.exrate.config.AppConfig;
import com.p0p0lam.back.exrate.model.OrganizationDBO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by Sergey on 24.02.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class OrganizationRepositoryTest extends BaseTest {
    private final static Logger logger = LoggerFactory.getLogger(OrganizationRepositoryTest.class);

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    MongoTemplate mongoTemplate;



    @Test
    public void readFirstPage(){
        Distance distance = new Distance(2, Metrics.KILOMETERS);
        //Page<OrganizationDBO> ors = organizationRepository.findByLegacyCoordsNear(point, distance, new PageRequest(0, 10));
        //logger.info("Legacy: Got {} orgs", ors.getTotalElements());
        GeoPage<OrganizationDBO> ors1 = organizationRepository.findByCoordsNear(new GeoJsonPoint(POINT), DISTANCE_MAX, new PageRequest(0,10));
        logger.info("GeoJSon: Got {} orgs", ors1.getTotalElements());
        assertTrue(ors1.hasContent());
    }

    @Test
    public void findByLocationAndCurrencyTest(){
        GeoPage<OrganizationDBO> orgs = organizationRepository.findByCoordsNearAndCurrency_CurrencyId("EUR", POINT, DISTANCE_MAX, new PageRequest(0, 10));
        assertTrue(orgs.hasContent());
        logger.info("Got {} orgs.", orgs.getTotalElements());
    }

    @Test
    public void testAggregation(){
        final NearQuery nearQuery = NearQuery
                .near(POINT).maxDistance(DISTANCE_MAX).spherical(true).num(1);
        Query query = Query.query(Criteria.where("currency._id").is("BYR"));
        nearQuery.query(query);
        GeoResults<OrganizationDBO> res = mongoTemplate.geoNear(nearQuery, OrganizationDBO.class);
        logger.info("GeoNear count: {}", res.getContent().size());
        TypedAggregation<OrganizationDBO> aggregation = newAggregation(OrganizationDBO.class,
               geoNear(nearQuery, "coords"),
                project("currency"),
                unwind("currency"),
                group("currency._id")
                        .max("currency.rate.ask").as("maxAsk")
                        .min("currency.rate.ask").as("minAsk")
                        .max("currency.rate.bid").as("maxBid")
                        .min("currency.rate.bid").as("minBid"),
                project("maxAsk", "minAsk", "maxBid", "minBid").and("currency").previousOperation(),
                match(Criteria.where("currency").is("BYR"))
        );
        AggregationResults<RatesMinMax> results = mongoTemplate.aggregate(aggregation, RatesMinMax.class);
        List<RatesMinMax> list = results.getMappedResults();
        if (list!=null){
            for (RatesMinMax ratesMinMax : list) {
                logger.info("Result: {}", ratesMinMax);
            }
        }
    }

}
