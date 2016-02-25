package com.p0p0lam.back.exrate.service;

import com.p0p0lam.back.exrate.model.CurrencyDBO;
import com.p0p0lam.back.exrate.model.OrganizationDBO;
import com.p0p0lam.back.exrate.model.net.DBOToResponseConverter;
import com.p0p0lam.back.exrate.model.net.Dict;
import com.p0p0lam.back.exrate.model.net.DictResponse;
import com.p0p0lam.back.exrate.model.net.RatesResponse;
import com.p0p0lam.back.exrate.repository.CurrencyRepository;
import com.p0p0lam.back.exrate.repository.OrganizationRepository;
import com.p0p0lam.back.exrate.repository.RatesMinMax;
import com.p0p0lam.back.exrate.repository.ResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by Sergey on 23.02.2016.
 */
@Service
public class BusinessServiceImpl implements BusinessService {
    private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private FinanceService financeService;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public RatesResponse getRates(int start, int count, double lat, double lng, int distanceKm, String currency, String language) {
        if (!financeService.isRunning()) {
            financeService.runUpdateIfNeed();
        }
        Distance distance = new Distance(distanceKm, Metrics.KILOMETERS);
        Point point = new Point(lng, lat);
        GeoPage<OrganizationDBO> result = organizationRepository.findByCoordsNearAndCurrency_CurrencyId(currency, point,
                distance,
                new PageRequest(start, count));

        RatesResponse response = new RatesResponse();
        if (result!=null && result.hasContent()){
            logger.info("Got result from db. All count: {}, Pages: {}, ", result.getTotalElements(), result.getTotalPages());
            RatesMinMax minMax = getRatesMinMax(point, distance, currency, (int) result.getTotalElements());
            response.setAllCount((int) result.getTotalElements());
            List<GeoResult<OrganizationDBO>> content = result.getContent();
            response.setRates(DBOToResponseConverter.getRatesFromOrganizationDBOList(content, currency, language));
            if (minMax!=null) {
                response.setMaxAskRate(minMax.maxAsk);
                response.setMaxBidRate(minMax.maxBid);
                response.setMinAskRate(minMax.minAsk);
                response.setMinBidRate(minMax.minBid);
            }
        } else {
            logger.info("No results found for given criteria");
        }
        return response;
    }

    private RatesMinMax getRatesMinMax(Point point, Distance distance, String currency, int num){
        final NearQuery nearQuery = NearQuery
                .near(point).maxDistance(distance).spherical(true).num(num);
        Query query = Query.query(Criteria.where("currency._id").is(currency));
        nearQuery.query(query);
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
                match(Criteria.where("currency").is(currency))
        );
        AggregationResults<RatesMinMax> results = mongoTemplate.aggregate(aggregation, RatesMinMax.class);
        return results.getUniqueMappedResult();
    }

    @Override
    public DictResponse<List<Dict>> getCurrencies(String language) {
        List<CurrencyDBO> currencies = currencyRepository.findAll();
        List<Dict> result = new ArrayList<>(currencies.size());
        for (CurrencyDBO currency : currencies) {
            result.add(new Dict(currency.getId(), "ru".equals(language)?currency.getNameRu():currency.getName()));
        }
        return new DictResponse<>(result);
    }
}
