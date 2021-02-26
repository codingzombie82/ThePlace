package com.changzakso.theplace.items;

public class PlaceItem {
    public int id;
    public String name;
    public String description;
    public double latitude;
    public double longitude;
    public String tel;
    public String address;
    public String email;
    public String homepage;
    public boolean isNation;
    public String mainImage;

    @Override
    public String toString() {
        return "PlaceItem{" +
                "id=" + id +
                ", name=" + name +
                ", description='" + name + '\'' +
                ", latitude='" + tel + '\'' +
                ", longitude='" + address + '\'' +
                ", tel=" + latitude +
                ", address=" + longitude +
                ", email='" + description + '\'' +
                ", homepage='" + homepage + '\'' +
                ", isNation='" + isNation + '\'' +
                '}';
    }
}
