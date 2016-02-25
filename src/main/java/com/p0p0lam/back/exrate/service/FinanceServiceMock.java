package com.p0p0lam.back.exrate.service;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.p0p0lam.back.exrate.model.*;
import com.p0p0lam.back.exrate.model.finance.Organization;
import com.p0p0lam.back.exrate.model.finance.Response;
import com.p0p0lam.back.exrate.repository.*;
import com.p0p0lam.back.exrate.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by Sergey on 22.02.2016.
 */
@Service
public class FinanceServiceMock implements FinanceService {
    private static final Logger logger = LoggerFactory.getLogger(FinanceServiceMock.class);
    public static final int DEFAULT_SYNC_PERIOD_MINUTES = 15;
    private final ReentrantLock mainLock = new ReentrantLock();
    @Autowired
    Gson gson;

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

        logger.info("Loading data from finance mock");
        Resource resource = new ClassPathResource("finance.json");
        ResponseDBO localResponse = responseRepository.findOne(ResponseDBO._ID);
        if (localResponse!=null && DateUtil.getMinutesDiffFromNow(localResponse.getSyncDate())< DEFAULT_SYNC_PERIOD_MINUTES){
            logger.info("Sync period not expired. exitting");
            return;
        }
        try {
            Response response = gson.fromJson(new JsonReader(new InputStreamReader(resource.getInputStream())), Response.class);
            if (response.lastChangesDate.equals(localResponse.getLastChangedDate())){
                logger.info("Remote data not changed from last sync. Exitting");
                localResponse.setSyncDate(DateUtil.now().getTime());
                responseRepository.save(localResponse);
                return;
            }
            logger.info("Got data");

            processResponse(response);

        } catch (IOException e) {
            logger.error("Can't open json", e);
        } catch (JsonParseException e) {
            logger.error("Can't parse json", e);
        }
    }

    private void processResponse(Response response) {
        ResponseDBO dbo = FinanceToDBOConverter.getResponseDBO(response);
        cityRepository.save(FinanceToDBOConverter.getIdNameDBO(response.cities, CityDBO.class));
        logger.info("Saved cities");
        currencyRepository.save(FinanceToDBOConverter.getIdNameDBO(response.currencies, CurrencyDBO.class));
        logger.info("Saved currencies");
        locationRepository.save(FinanceToDBOConverter.getIdNameDBO(response.locations, LocationDBO.class));
        logger.info("Saved locations");
        orgTypeRepository.save(FinanceToDBOConverter.getIdNameDBO(response.orgTypes, OrgTypeDBO.class));
        logger.info("Saved org types");
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
