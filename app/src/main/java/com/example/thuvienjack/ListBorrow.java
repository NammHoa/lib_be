package com.example.thuvienjack;

public class ListBorrow {
    private String tenDS;
    private String hinhDS;
    private String key;

    public ListBorrow(String tenDS, String hinhDS, String key) {
        this.tenDS = tenDS;
        this.hinhDS = hinhDS;
        this.key = key;
    }

    public String getTenDS() {
        return tenDS;
    }

    public void setTenDS(String tenDS) {
        this.tenDS = tenDS;
    }

    public String getHinhDS() {
        return hinhDS;
    }

    public void setHinhDS(String hinhDS) {
        this.hinhDS = hinhDS;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ListBorrow(){

    }
}
