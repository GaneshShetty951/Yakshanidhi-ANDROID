package com.example.ganeshshetty.yakshanidhi.model;

import java.io.Serializable;

/**
 * Created by Ganesh Shetty on 07-04-2017.
 */

public class Show_class implements Serializable{
    private int show_id;
    private String show_producer_name,show_date,show_time;
    private String contact1,contact2;
    private String village,taluk,district,pincode;
    private String mela_name,mela_pic,prasangha_name;

    public Show_class() {
    }

    public Show_class(int show_id, String show_producer_name, String show_date, String show_time, String contact1, String contact2, String village, String taluk, String district, String pincode, String mela_name, String mela_pic, String prasangha_name) {
        this.show_id = show_id;
        this.show_producer_name = show_producer_name;
        this.show_date = show_date;
        this.show_time = show_time;
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.village = village;
        this.taluk = taluk;
        this.district = district;
        this.pincode = pincode;
        this.mela_name = mela_name;
        this.mela_pic = mela_pic;
        this.prasangha_name = prasangha_name;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public String getShow_producer_name() {
        return show_producer_name;
    }

    public void setShow_producer_name(String show_producer_name) {
        this.show_producer_name = show_producer_name;
    }

    public String getShow_date() {
        return show_date;
    }

    public void setShow_date(String show_date) {
        this.show_date = show_date;
    }

    public String getShow_time() {
        return show_time;
    }

    public void setShow_time(String show_time) {
        this.show_time = show_time;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMela_name() {
        return mela_name;
    }

    public void setMela_name(String mela_name) {
        this.mela_name = mela_name;
    }

    public String getMela_pic() {
        return mela_pic;
    }

    public void setMela_pic(String mela_pic) {
        this.mela_pic = mela_pic;
    }

    public String getPrasangha_name() {
        return prasangha_name;
    }

    public void setPrasangha_name(String prasangha_name) {
        this.prasangha_name = prasangha_name;
    }
}
