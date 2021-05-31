package com.example.advenegro;

public class Blog {

    private String title;
    private String city;
    private String shortdescription;
    private String description;
    private String longitude;
    private String latitude;
    private String image;

    private Blog(){
    }

    private Blog(String title, String city, String shortdescription,String description,String longitude, String latitude, String image){
        this.title = title;
        this.city = city;
        this.shortdescription = shortdescription;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.image = image;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }




    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
