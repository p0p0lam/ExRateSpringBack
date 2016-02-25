package com.p0p0lam.back.exrate.model.net;

import com.p0p0lam.back.exrate.model.OrganizationDBO;
import com.p0p0lam.back.exrate.model.finance.Currency;
import com.p0p0lam.back.exrate.util.DateUtil;
import com.sun.istack.internal.NotNull;
import org.springframework.data.geo.GeoResult;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Sergey on 25.02.2016.
 */
public class DBOToResponseConverter {

    public static List<Rate> getRatesFromOrganizationDBOList(@NotNull List<GeoResult<OrganizationDBO>> source, String currencyCode){
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
            organization.setTitle(organizationDBO.getTitle());
            organization.setAddress(organizationDBO.getAddress());
            organization.setPhone(organizationDBO.getPhone());
            organization.setLatitude(organizationDBO.getCoords().getY());
            organization.setLongitude(organizationDBO.getCoords().getX());
            organization.setType(organizationDBO.getType());
            organization.setLink(organizationDBO.getLink());
            rate.setOrganization(organization);
            rate.setDistance(organizationDBOGeoResult.getDistance());
            result.add(rate);
        }
        return result;
    }

}
