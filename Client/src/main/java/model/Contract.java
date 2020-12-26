package model;


import annotations.Column;
import main.Constants;

import java.util.UUID;

public class Contract {

    private String id;
    @Column(name = Constants.TEXT_IS_ACTUAL)
    private boolean isActual;
    @Column(name = Constants.TEXT_CREATE_DATE)
    private long date;
    @Column(name = Constants.TEXT_CHANGE_DATE)
    private long changeDate;
    @Column(name = Constants.TEXT_DATA)
    private String data;

    public Contract(long date, long changeDate, String data) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.changeDate = changeDate;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActual() {
        return isActual;
    }

    public void setActual(boolean actual) {
        isActual = actual;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(long changeDate) {
        this.changeDate = changeDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
