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
    private String dateForm;
    private String items;

    public EntryItem() {}

    public EntryItem(int id, String title, String notes, String date, String time, String method, double value, String expSav, String store, String dateForm) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.method = method;
        this.value = value;
        this.expSav = expSav;
        this.store = store;
        this.dateForm = dateForm;
    }

    public EntryItem(String title, String notes, String date, String time, String method, double value, String expSav, String store, String dateForm) {
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.method = method;
        this.value = value;
        this.expSav = expSav;
        this.store = store;
        this.dateForm = dateForm;
    }

    public EntryItem(String title, String notes, String date, String time, String method, double value, String expSav, String store, String dateForm, String items) {
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.method = method;
        this.value = value;
        this.expSav = expSav;
        this.store = store;
        this.dateForm = dateForm;
        this.items = items;
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

    public String getDateForm() { return dateForm; }

    public void setDateForm(String dateForm) { this.dateForm = dateForm; }

    public String getItems() { return items; }

    public void setItems(String items) { this.items = items; }
}
