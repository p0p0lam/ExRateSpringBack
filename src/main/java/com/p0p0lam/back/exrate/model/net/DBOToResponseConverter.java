package com.p0p0lam.back.exrate.model.net;

import com.p0p0lam.back.exrate.controller.RatesController;
import com.p0p0lam.back.exrate.model.OrganizationDBO;
import com.p0p0lam.back.exrate.model.finance.Currency;
import com.p0p0lam.back.exrate.util.DateUtil;
import org.springframework.data.geo.GeoResult;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Sergey on 25.02.2016.
 */
public class DBOToResponseConverter {



    public static List<Rate> getRatesFromOrganizationDBOList(List<GeoResult<OrganizationDBO>> source, String currencyCode, String language){
        List<Rate> result = new ArrayList<>(source.size());
        for (GeoResult<OrganizationDBO> organizationDBOGeoResult : source) {
            Rate rate = new Rate(currencyCode);

            OrganizationDBO organizationDBO = organizationDBOGeoResult.getContent();
            for (Currency currency : organizationDBO.getCurrency()) {
                if (currency.currencyId.equals(currencyCode)){
                    rate.setAsk(Double.valueOf(currency.rate.ask));
                    rate.setBid(Double.valueOf(currency.rate.bid));
                    break;
                }
            }
            rate.setUpdatedAt(GregorianCalendar.getInstance(DateUtil.UTC).getTime());
            Organization organization = new Organization();
            if (language.equals(RatesController.RU_LANG)) {
                organization.setTitle(organizationDBO.getTitleRu());
                organization.setAddress(organizationDBO.getAddressRu());
                organization.setLink(organizationDBO.getLinkRu());
                organization.setCity(organizationDBO.getCityRu());
                organization.setLocation(organizationDBO.getLocationRu());
            } else {
                organization.setTitle(organizationDBO.getTitle());
                organization.setAddress(organizationDBO.getAddress());
                organization.setLink(organizationDBO.getLink());
                organization.setCity(organizationDBO.getCity());
                organization.setLocation(organizationDBO.getLocation());
            }
            organization.setPhone(organizationDBO.getPhone());
            organization.setLatitude(organizationDBO.getCoords().getY());
            organization.setLongitude(organizationDBO.getCoords().getX());
            organization.setType(organizationDBO.getType());

            rate.setOrganization(organization);
            rate.setDistance(organizationDBOGeoResult.getDistance());
            result.add(rate);
        }
        return result;
    }

}
