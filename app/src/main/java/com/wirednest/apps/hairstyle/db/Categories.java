package com.wirednest.apps.hairstyle.db;

import com.orm.SugarRecord;

public class Categories extends SugarRecord {
    public int id_server;
    public String category_name;
    public String description;
    public int image;

    public Categories() { }

    public Categories(int id_server,String category_name, String description, int image) {
        this.category_name = category_name;
        this.description = description;
        this.image = image;
    }
}
