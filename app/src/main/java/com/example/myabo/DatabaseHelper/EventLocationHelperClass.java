package com.example.myabo.DatabaseHelper;



public class EventLocationHelperClass {
    private String address;
    private String bloodType;
    private String contactNo;
    private String eventName;
    private String organiser;
    private String dateTime;
    private String location;

    public EventLocationHelperClass(String address, String bloodType, String contactNo, String eventName, String organiser, String dateTime,  String location) {
        this.address = address;
        this.bloodType = bloodType;
        this.contactNo = contactNo;
        this.eventName = eventName;
        this.organiser = organiser;
        this.dateTime = dateTime;
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventLocationHelperClass() {

    }
}

