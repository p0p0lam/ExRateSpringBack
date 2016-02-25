package com.p0p0lam.back.exrate.model;

import com.p0p0lam.back.exrate.model.finance.Organization;
import com.p0p0lam.back.exrate.model.finance.Response;
import com.sun.istack.internal.NotNull;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.*;

/**
 * Created by Sergey on 23.02.2016.
 */
public class FinanceToDBOConverter {
    public static final String ID_PATTERN="^http://organizations.finance.ua/(ua|ru)/info/currency/-/(\\w+)/cash/.*";
    public static ResponseDBO getResponseDBO(Response response){
        ResponseDBO dbo = new ResponseDBO(response.date, response.lastChangesDate);
        return dbo;
    }

    private static String getOrgIdFromLink(String link){
        if (link!=null){
            return link.replaceAll(ID_PATTERN, "$2");
        }
        return null;
    }

    public static List<OrganizationDBO> getOrganizations(Response response){
        if (response.organizations!=null){
            List<OrganizationDBO> result = new ArrayList<>();
            for (Organization organization : response.organizations) {
                OrganizationDBO dbo = new OrganizationDBO();
                dbo.setId(getOrgIdFromLink(organization.getLink()));
                dbo.setAddress(organization.getAddress());
                dbo.setCityId(organization.getCityId());
                if (organization.getCoords()!=null && organization.getCoords().size()==2) {
                    Double lat = organization.getCoords().get(1);
                    Double lng = organization.getCoords().get(0);
                    if (lat==null || lng == null){
                        continue;
                    }
                    GeoJsonPoint point = new GeoJsonPoint(lng==null?0:lng, lat==null?0:lat);
                    dbo.setCoords(point);
                } else {
                   continue;
                }
                dbo.setLegacyCoords(Arrays.asList(new Double[]{dbo.getCoords().getX(), dbo.getCoords().getY()}));

                dbo.setCurrency(organization.getCurrency());
                dbo.setLink(organization.getLink());
                dbo.setLocationId(organization.getLocationId());
                dbo.setPhone(organization.getPhone());
                dbo.setTitle(organization.getTitle());
                dbo.setType(organization.getType());
                result.add(dbo);
            }
            return result;
        }
        return Collections.emptyList();
    }

    public static <T extends IdNameDBO> List<T> getIdNameDBO(Map<String, String> values, Class<T> tClass){
        if (values!=null && !values.isEmpty()){
            List<T> result = new ArrayList<>(values.size());
            for (String id : values.keySet()) {
                try {
                    T t = tClass.newInstance();
                    t.setId(id);
                    t.setName(values.get(id));
                    result.add(t);
                } catch (Exception e){
                    //ignore
                }
            }
            return result;
        }
        return Collections.emptyList();
    }


}
