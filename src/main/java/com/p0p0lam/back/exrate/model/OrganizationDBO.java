package com.p0p0lam.back.exrate.model;

import com.p0p0lam.back.exrate.model.finance.Currency;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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
    private String addressRu;
    //@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint coords;
    private List<Double> legacyCoords;
    private String cityRu;
    private String city;
    private String location;
    private String locationRu;
    private List<Currency> currency = new ArrayList<Currency>();
    private String phone;
    private String title;
    private String titleRu;
    private Integer type;
    private String link;
    private String linkRu;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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


    public String getAddressRu() {
        return addressRu;
    }

    public void setAddressRu(String addressRu) {
        this.addressRu = addressRu;
    }

    public String getCityRu() {
        return cityRu;
    }

    public void setCityRu(String cityRu) {
        this.cityRu = cityRu;
    }

    public String getLocationRu() {
        return locationRu;
    }

    public void setLocationRu(String locationRu) {
        this.locationRu = locationRu;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getLinkRu() {
        return linkRu;
    }

    public void setLinkRu(String linkRu) {
        this.linkRu = linkRu;
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
        if (!city.equals(that.city)) return false;
        if (!location.equals(that.location)) return false;
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
        result = 31 * result + city.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + title.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
