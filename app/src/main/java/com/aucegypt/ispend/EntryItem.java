package com.aucegypt.ispend;

public class EntryItem {

    private int id;
    private String title;
    private String notes;
    private String date;
    private String time;
    private String method;
    private double value;
    private String expSav;
    private String store;

    public EntryItem() {}

    public EntryItem(int id, String title, String notes, String date, String time, String method, double value, String expSav, String store) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.method = method;
        this.value = value;
        this.expSav = expSav;
        this.store = store;
    }

    public EntryItem(String title, String notes, String date, String time, String method, double value, String expSav, String store) {
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.method = method;
        this.value = value;
        this.expSav = expSav;
        this.store = store;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getExpSav() {
        return expSav;
    }

    public void setExpSav(String expSav) {
        this.expSav = expSav;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
