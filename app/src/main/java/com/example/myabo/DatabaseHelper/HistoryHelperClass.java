package com.example.myabo.DatabaseHelper;

public class HistoryHelperClass {
    private String bloodAmount;
    private String bloodOxygenLevel;
    private String bloodSerialNo;
    private String bloodPressure;
    private String eventName;
    private String remark;
    private String date;

    public HistoryHelperClass() {

    }


    public HistoryHelperClass(String eventName, String bloodAmount, String bloodOxygenLevel, String bloodSerialNo, String bloodPressure, String remark, String date) {
        this.bloodAmount = bloodAmount;
        this.bloodOxygenLevel = bloodOxygenLevel;
        this.bloodSerialNo = bloodSerialNo;
        this.bloodPressure = bloodPressure;
        this.eventName = eventName;
        this.remark = remark;
        this.date = date;
    }

    public String getBloodAmount() {
        return bloodAmount;
    }

    public void setBloodAmount(String bloodAmount) {
        this.bloodAmount = bloodAmount;
    }

    public String getBloodOxygenLevel() {
        return bloodOxygenLevel;
    }

    public void setBloodOxygenLevel(String bloodOxygenLevel) {
        this.bloodOxygenLevel = bloodOxygenLevel;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getBloodSerialNo() {
        return bloodSerialNo;
    }

    public void setBloodSerialNo(String bloodSerialNo) {
        this.bloodSerialNo = bloodSerialNo;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}