package com.example.stefy.idonate;

/**
 * Created by Stefy on 5/30/2017.
 */

public class myValues {
    public static String username;
    public static String email;
    public static String clothes_typeID;
    public static String donationID;
    public static String foundationID;
    public static String quantity;
    public static String size;
    public static String lat;
    public static String lon;
    public static String label;
    public static String categoryInsert;
    public static String myPath="http://192.168.1.6";
    public static String rank;
    public static String temp1;

    public static void setUsername(String username) {
        myValues.username = username;
    }
    public static String getUsername() {
        return username;
    }

    public static void setEmail(String email) {
        myValues.email = email;
    }
    public static String getEmail() {
        return email;
    }

    public static void setClothes_typeID(String id) {
        myValues.clothes_typeID = id;
    }
    public static String getClothes_typeID() {
        return clothes_typeID;
    }

    public static void setDonationID(String id) {
        myValues.donationID = id;
    }
    public static String getDonationID() {
        return donationID;
    }

    public static void setfoundationID(String id) {
        myValues.foundationID = id;
    }
    public static String getfoundationID() {
        return foundationID;
    }

    public static void setQuantity(String id) {myValues.quantity = id;}
    public static String getQuantity() {
        return quantity;
    }

    public static void setSize(String id) {
        myValues.size = id;
    }
    public static String getSize() {
        return size;
    }

    public static void setLabel(String id) {
        myValues.label = id;
    }
    public static String getLabel() {
        return label;
    }

    public static void setlat(String id) {
        myValues.lat = id;
    }
    public static String getlat() {
        return lat;
    }
    public static void setlon(String id) {
        myValues.lon = id;
    }
    public static String getlon() {
        return lon;
    }

    public static void setCategoryInsert(String id) {
        myValues.categoryInsert = id;
    }
    public static String getCategoryInsert() {
        return categoryInsert;
    }
    public static String getMyPath(){return myPath;}

    public static void setRank(String id) {
        myValues.rank = id;
    }
    public static String getRank() {
        return rank;
    }

    public static void setTemp1(String temp1) {
        myValues.temp1 = temp1;
    }
    public static String getTemp1() {
        return temp1;
    }

}
