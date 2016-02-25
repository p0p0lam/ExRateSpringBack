package com.p0p0lam.back.exrate.service;

import com.p0p0lam.back.exrate.model.*;
import com.p0p0lam.back.exrate.model.finance.Response;
import com.p0p0lam.back.exrate.repository.*;
import com.p0p0lam.back.exrate.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sergey on 25.02.2016.
 */
@Transactional
@Service(value = "financeService")
public class FinanceServiceImpl implements FinanceService {
    private static final Logger logger = LoggerFactory.getLogger(FinanceServiceImpl.class);
    private final ReentrantLock mainLock = new ReentrantLock();
    private static final URI URI_RU =URI.create("http://resources.finance.ua/ru/app-exchange/currency-cash");
    private static final URI URI_UA =URI.create("http://resources.finance.ua/ua/app-exchange/currency-cash");
    @Autowired
    ThreadPoolExecutor asyncTaskExecutor;
    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    OrgTypeRepository orgTypeRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public synchronized boolean isRunning() {
        return asyncTaskExecutor.getActiveCount() > 0;
    }

    @Async
    public void runUpdateIfNeed(){
        mainLock.lock();
        try {
            getData();
        } finally {
            mainLock.unlock();
        }
    }

    @Override
    @Transactional
    public void getData() {
        ResponseDBO localResponse = responseRepository.findOne(ResponseDBO._ID);
        if (localResponse!=null && DateUtil.getMinutesDiffFromNow(localResponse.getSyncDate())< DEFAULT_SYNC_PERIOD_MINUTES){
            logger.info("Sync period not expired. exiting");
            return;
        }
        try {
            logger.info("Getting data from finance service");
            Response response = restTemplate.getForObject(URI_UA, Response.class);
            Response responseRu = restTemplate.getForObject(URI_RU, Response.class);
            if (response.lastChangesDate.equals(localResponse.getLastChangedDate())){
                logger.info("Remote data not changed from last sync. Exiting");
                localResponse.setSyncDate(DateUtil.now().getTime());
                responseRepository.save(localResponse);
                return;
            }
            logger.info("Got data");
            processResponse(response);
            // RU language

            processRuLanguageResponse(responseRu);
            logger.info("GetData done.");
        } catch (RestClientException e){
            logger.error("Can't get data from finance.", e);
        }
    }

    void processRuLanguageResponse(Response response){
        logger.debug("Updating cities ru language");
        List<CityDBO> cities = FinanceToDBOConverter.getIdNameDBO(response.cities, CityDBO.class);
        HashMap<String, IdNameDBO> remoteMap = new HashMap<>(cities.size());
        for (CityDBO city : cities) {
            remoteMap.put(city.getId(), city);
        }
        List<CityDBO> localCities = cityRepository.findAll();
        for (CityDBO localCity : localCities) {
            if (remoteMap.containsKey(localCity.getId())){
                IdNameDBO remoteCity = remoteMap.get(localCity.getId());
                if (!remoteCity.getName().equals(localCity.getNameRu())){
                    localCity.setNameRu(remoteCity.getName());
                    cityRepository.save(localCity);
                }
                remoteMap.remove(localCity.getId());
            }
        }
        remoteMap.clear();
        localCities.clear();
        cities.clear();
        logger.debug("Updating currencies ru language");
        List<CurrencyDBO> currencyDBOs = FinanceToDBOConverter.getIdNameDBO(response.currencies, CurrencyDBO.class);
        for (CurrencyDBO val : currencyDBOs) {
            remoteMap.put(val.getId(), val);
        }
        List<CurrencyDBO> localCurrencies = currencyRepository.findAll();

        for (CurrencyDBO local : localCurrencies) {
            if (remoteMap.containsKey(local.getId())){
                IdNameDBO remote = remoteMap.get(local.getId());
                if (!remote.getName().equals(local.getNameRu())){
                    local.setNameRu(remote.getName());
                    currencyRepository.save(local);
                }
                remoteMap.remove(local.getId());
            }
        }
        remoteMap.clear();
        localCurrencies.clear();
        currencyDBOs.clear();
        logger.debug("Updating organizations ru language");
        List<OrganizationDBO> remoteOrgsList = FinanceToDBOConverter.getOrganizations(response);
        Map<String, OrganizationDBO> remoteMapOrgs = new HashMap<>(remoteOrgsList.size());
        for (OrganizationDBO remoteOrg : remoteOrgsList) {
            remoteMapOrgs.put(remoteOrg.getId(), remoteOrg);
        }
        List<OrganizationDBO> localOrgs = organizationRepository.findAll();
        for (OrganizationDBO localOrg : localOrgs) {
            if (remoteMapOrgs.containsKey(localOrg.getId())){
                OrganizationDBO remote= remoteMapOrgs.get(localOrg.getId());
                if (!remote.getAddress().equals(localOrg.getAddressRu()) ||
                        !remote.getCity().equals(localOrg.getCityRu()) ||
                        !remote.getLink().equals(localOrg.getLinkRu()) ||
                        !remote.getTitle().equals(localOrg.getTitleRu()) ||
                        !remote.getLocation().equals(localOrg.getLocationRu())){

                    localOrg.setAddressRu(remote.getAddress());
                    localOrg.setTitleRu(remote.getTitle());
                    localOrg.setLinkRu(remote.getLink());
                    localOrg.setCityRu(remote.getCity());
                    localOrg.setLocationRu(remote.getLocation());
                    organizationRepository.save(localOrg);
                }
            }
        }

    }

    private void processResponse(Response response) {
        ResponseDBO dbo = FinanceToDBOConverter.getResponseDBO(response);
        List<CityDBO> cities = FinanceToDBOConverter.getIdNameDBO(response.cities, CityDBO.class);
        List<CityDBO> localCities = cityRepository.findAll();
        for (CityDBO localCity : localCities) {
            if (cities.contains(localCity)){
                CityDBO remoteCity = cities.get(cities.indexOf(localCity));
                if (remoteCity!=null && !localCity.equals(remoteCity)){
                    remoteCity.setNameRu(localCity.getNameRu());
                    cityRepository.save(remoteCity);
                }
                cities.remove(remoteCity);
            } else {
                cityRepository.delete(localCity);
            }
        }
        if (!cities.isEmpty()){
            cityRepository.save(cities);
        }
        logger.info("Saved cities");
        List<CurrencyDBO> remoteCurrencies = FinanceToDBOConverter.getIdNameDBO(response.currencies, CurrencyDBO.class);
        List<CurrencyDBO> localCurrencies = currencyRepository.findAll();
        for (CurrencyDBO localCurrency : localCurrencies) {
            if (remoteCurrencies.contains(localCurrency)){
                CurrencyDBO remote = remoteCurrencies.get(remoteCurrencies.indexOf(localCurrency));
                if (!remote.equals(localCurrency)){
                    remote.setNameRu(localCurrency.getNameRu());
                    currencyRepository.save(remote);
                }
                remoteCurrencies.remove(remote);
            } else {
                currencyRepository.delete(localCurrency);
            }
        }
        if (!remoteCurrencies.isEmpty()) {
            currencyRepository.save(remoteCurrencies);
        }
        logger.info("Saved currencies");
        //locationRepository.save(FinanceToDBOConverter.getIdNameDBO(response.locations, LocationDBO.class));
        //logger.info("Saved locations");
        //orgTypeRepository.save(FinanceToDBOConverter.getIdNameDBO(response.orgTypes, OrgTypeDBO.class));
        //logger.info("Saved org types");
        List<OrganizationDBO> remoteOrgsList = FinanceToDBOConverter.getOrganizations(response);
        logger.debug("Got {} remote organizations", remoteOrgsList.size());
        Map<String, OrganizationDBO> remoteMap = new HashMap<>(remoteOrgsList.size());
        for (OrganizationDBO remoteOrg : remoteOrgsList) {
            remoteMap.put(remoteOrg.getId(), remoteOrg);
        }
        List<OrganizationDBO> localOrgs = organizationRepository.findAll();
        logger.debug("Got {} local organizations", localOrgs.size());
        for (Iterator<OrganizationDBO> iterator = localOrgs.iterator(); iterator.hasNext(); ) {
            OrganizationDBO localOrganizationDBO = iterator.next();
            if (remoteMap.containsKey(localOrganizationDBO.getId())) {
                OrganizationDBO remote = remoteMap.get(localOrganizationDBO.getId());
                if (!remote.equals(localOrganizationDBO)) {
                    logger.debug("Updating local item {} to {}", localOrganizationDBO, remote);
                    organizationRepository.save(remote);
                }
                remoteMap.remove(localOrganizationDBO.getId());
            } else {
                logger.debug("Deleting local item {} ", localOrganizationDBO);
                organizationRepository.delete(localOrganizationDBO);
            }
        }
        if (!remoteMap.isEmpty()) {
            logger.debug("Inserting {} new organizations.", remoteMap.size());
            organizationRepository.insert(remoteMap.values());
        }
        logger.info("Saved organizations");
        responseRepository.save(dbo);
        logger.info("Saved response");
    }
}
