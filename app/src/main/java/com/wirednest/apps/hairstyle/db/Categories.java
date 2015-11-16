package com.wirednest.apps.hairstyle.db;

import com.orm.SugarRecord;

public class Categories extends SugarRecord {
    public int idServer;
    public String categoryName;
    public String description;
    public int image;

    public Categories() { }

    public Categories(int idServer,String category_name, String description, int image) {
        this.idServer = idServer;
        this.categoryName = category_name;
        this.description = description;
        this.image = image;
    }
    public Categories(String category_name, String description, int image) {
        this.idServer = 0;
        this.categoryName = category_name;
        this.description = description;
        this.image = image;
    }
}
