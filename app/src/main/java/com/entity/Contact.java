package com.entity;

/**
 * Created by qlshi on 2018/7/18.
 */

public class Contact {
    private String name;
    private String headerLetter;
    private String pinyin;
    private String phoneNumber;

    public Contact() {
    }

    public Contact(String name, String pinyin, String letter, String phoneNumber) {
        this.name = name;
        this.pinyin = pinyin;
        this.headerLetter = letter;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderLetter() {
        return headerLetter;
    }

    public void setHeaderLetter(String headerLetter) {
        this.headerLetter = headerLetter;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
