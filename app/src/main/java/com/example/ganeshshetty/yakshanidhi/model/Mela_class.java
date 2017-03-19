package com.example.ganeshshetty.yakshanidhi.model;

import java.io.Serializable;

/**
 * Created by Nilanchala Panigrahy on 10/25/16.
 */
public class Mela_class implements Serializable {

    private String name;
    private String thumbnail;
    private String email;
    private String contact;
    private String village;
    private String taluk;
    private String district;
    private String pinode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getPinode() {
        return pinode;
    }

    public void setPinode(String pinode) {
        this.pinode = pinode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
