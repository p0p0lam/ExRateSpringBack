package com.p0p0lam.back.exrate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.p0p0lam.back.exrate.model.finance.Currency;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 23.02.2016.
 */
@Document(collection = "organizations")
public class OrganizationDBO {
    @Id
    private String id;
    private String address;
    //@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint coords;
    private List<Double> legacyCoords;
    private String cityId;
    private String locationId;
    private List<Currency> currency = new ArrayList<Currency>();
    private String phone;
    private String title;
    private Integer type;
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoJsonPoint getCoords() {
        return coords;
    }

    public void setCoords(GeoJsonPoint coords) {
        this.coords = coords;
    }

    public List<Double> getLegacyCoords() {
        return legacyCoords;
    }

    public void setLegacyCoords(List<Double> legacyCoords) {
        this.legacyCoords = legacyCoords;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    @Override
    public String toString() {
        return "OrganizationDBO{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationDBO that = (OrganizationDBO) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (!legacyCoords.equals(that.legacyCoords)) return false;
        if (!cityId.equals(that.cityId)) return false;
        if (!locationId.equals(that.locationId)) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (!title.equals(that.title)) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return !(link != null ? !link.equals(that.link) : that.link != null);

    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + legacyCoords.hashCode();
        result = 31 * result + cityId.hashCode();
        result = 31 * result + locationId.hashCode();
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + title.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
