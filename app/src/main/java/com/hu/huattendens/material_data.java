package com.hu.huattendens;

public class material_data {
    String name;
    String time;
    String hall;
    String sec;
    String days;

    public material_data(String name, String time, String hall, String sec,String days) {
        this.name = name;
        this.time = time;
        this.hall = hall;
        this.sec = sec;
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }
}
