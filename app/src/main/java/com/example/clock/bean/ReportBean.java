package com.example.clock.bean;

import org.litepal.crud.LitePalSupport;

public class ReportBean extends LitePalSupport {


    /**
     * id : 2017213072
     * name : 刘连波
     * age : 18
     * ishealthy : 是
     * phone : 1322222222
     * address : 重庆
     * temperature : 36
     * time : 2020-6-26
     */

    public int id;
    public String name;
    private String age;
    private String ishealthy;
    private String phone;
    private String address;
    private String temperature;
    private String time;
    private int userId;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIshealthy() {
        return ishealthy;
    }

    public void setIshealthy(String ishealthy) {
        this.ishealthy = ishealthy;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
