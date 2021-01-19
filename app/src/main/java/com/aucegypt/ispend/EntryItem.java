package com.aucegypt.ispend;

public class EntryItem {

    private String title;
    private String notes;
    private String dateTime;
    private String method;
    private double value;
    private String expSav;

    public EntryItem(String title, String notes, String dateTime, String method, double value, String expSav)
    {
        this.title = title;
        this.notes = notes;
        this.dateTime = dateTime;
        this.method = method;
        this.value = value;
        this.expSav = expSav;

        if (expSav.equals("exp"))
            this.value = value * -1.0;

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getExpSav() { return expSav; }

    public void setExpSav(String expSav) { this.expSav = expSav; }
}
