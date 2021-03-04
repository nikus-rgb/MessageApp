package com.example.qrcodebysensors.Models;

public class UserModel {
    private String uName, uNumber;

    public UserModel(String name, String number) {
        this.uName = name;
        this.uNumber = number;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }
}
