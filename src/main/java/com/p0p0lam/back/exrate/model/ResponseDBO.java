package com.p0p0lam.back.exrate.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Sergey on 22.02.2016.
 */
@Document(collection = "response")
public class ResponseDBO {

    public static final String _ID="1";

    @Id
    private String id =_ID;
    private String date;
    private String lastChangedDate;
    private Date syncDate;

    public ResponseDBO() {
    }

    public ResponseDBO(String id, String date, String lastChangedDate, Date syncDate) {
        this.id = id;
        this.date = date;
        this.lastChangedDate = lastChangedDate;
        if (syncDate == null){
            syncDate = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
        }
        this.syncDate = syncDate;
    }

    public ResponseDBO(String date, String lastChangedDate, Date syncDate) {
     this(_ID, date, lastChangedDate, syncDate);
    }

    public ResponseDBO(String date, String lastChangedDate) {
        this(date, lastChangedDate, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastChangedDate() {
        return lastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }
}
