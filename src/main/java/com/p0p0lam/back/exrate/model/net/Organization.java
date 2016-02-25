package com.p0p0lam.back.exrate.model.net;

import org.springframework.data.geo.Point;

/**
 * Created by Sergey on 23.02.2016.
 */
public class Organization {
    public static final int TYPE_FOP =2;
    public static final int TYPE_BANK =1;
    private String phone;
    private String title;
    private String address;
    private String link;
    private int type;
    private double latitude = 0.0D;
    private double longitude = 0.0D;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
