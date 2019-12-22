package com.kh_sof_dev.real_estate_bosnia.Activities.Classes;

import java.util.ArrayList;
import java.util.List;

public class Real_estate {
    private int nb,room,bath;
    private String img1,img2,img3,youtup,uid,address,govkey,munkey;
    private Double price=0.0,building,earth;
private location location;
private  int type=1,earth_type=1;
private Boolean Solde=false;
private List<String> imagesURL=new ArrayList<>();
    public com.kh_sof_dev.real_estate_bosnia.Activities.Classes.location getLocation() {
        return location;
    }

    public void setLocation(com.kh_sof_dev.real_estate_bosnia.Activities.Classes.location location) {
        this.location = location;
    }

    public Real_estate() {

    }

    public int getEarth_type() {
        return earth_type;
    }

    public void setEarth_type(int earth_type) {
        this.earth_type = earth_type;
    }

    public String getMunkey() {
        return munkey;
    }

    public void setMunkey(String munkey) {
        this.munkey = munkey;
    }

    public String getGovkey() {
        return govkey;
    }

    public void setGovkey(String govkey) {
        this.govkey = govkey;
    }

    public Boolean getSolde() {
        return Solde;
    }

    public List<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public void setSolde(Boolean solde) {
        Solde = solde;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getBath() {
        return bath;
    }

    public void setBath(int bath) {
        this.bath = bath;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getYoutup() {
        return youtup;
    }

    public void setYoutup(String youtup) {
        this.youtup = youtup;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBuilding() {
        return building;
    }

    public void setBuilding(Double building) {
        this.building = building;
    }

    public Double getEarth() {
        return earth;
    }

    public void setEarth(Double earth) {
        this.earth = earth;
    }
}
