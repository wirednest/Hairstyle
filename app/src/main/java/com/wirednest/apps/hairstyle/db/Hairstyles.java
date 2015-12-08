package com.wirednest.apps.hairstyle.db;


import com.orm.SugarRecord;

public class Hairstyles extends SugarRecord {
    public int idServer;
    public String hairName;
    public String description;
    public String image;
    public double xpoint;
    public double ypoint;
    public Categories categories;

    public void Hairstyles(){}

    public void Hairstyles(int idServer,String hairName,String description, String image, Categories categories,double xpoint, double ypoint){
        this.idServer = idServer;
        this.hairName = hairName;
        this.description = description;
        this.image = image;
        this.categories = categories;
        this.xpoint = xpoint;
        this.ypoint = ypoint;
    }
    public void Hairstyles(String hairName,String description, String image, Categories categories,double xpoint, double ypoint){
        this.idServer = 0;
        this.hairName = hairName;
        this.description = description;
        this.image = image;
        this.categories = categories;
        this.xpoint = xpoint;
        this.ypoint = ypoint;
    }
}
