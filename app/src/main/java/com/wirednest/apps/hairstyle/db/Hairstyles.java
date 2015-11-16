package com.wirednest.apps.hairstyle.db;


import com.orm.SugarRecord;

public class Hairstyles extends SugarRecord {
    public int idServer;
    public String hairName;
    public String description;
    public String image;

    public Categories categories;

    public void Hairstyles(){}

    public void Hairstyles(int idServer,String hairName,String description, String image, Categories categories){
        this.idServer = idServer;
        this.hairName = hairName;
        this.description = description;
        this.image = image;
        this.categories = categories;
    }
    public void Hairstyles(String hairName,String description, String image, Categories categories){
        this.idServer = 0;
        this.hairName = hairName;
        this.description = description;
        this.image = image;
        this.categories = categories;
    }
}
