package com.example.thuvienjack;

public class DataClass {
    private String dataTitle;
    private String dataDesc;
    private String dataAuthor;
    private String dataCate;

    private String dataImage;
    private String key;

    public DataClass(String dataTitle, String dataDesc, String dataAuthor, String dataCate, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataAuthor = dataAuthor;
        this.dataCate = dataCate;
        this.dataImage = dataImage;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataAuthor() {
        return dataAuthor;
    }

    public String getDataCate() {
        return dataCate;
    }

    public String getDataImage() {
        return dataImage;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public DataClass(){

    }
}

