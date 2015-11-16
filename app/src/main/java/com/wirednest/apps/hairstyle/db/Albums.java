package com.wirednest.apps.hairstyle.db;

import com.orm.SugarRecord;

public class Albums extends SugarRecord{
    public static final String HIDDEN_TYPE = "hidden";
    public static final String PUBLIC_TYPE = "public";
    public static final String PRIVATE_TYPE = "private";
    /*
    * Table Columns albums
    - id
    - name
    - description
    - date
    - type
    - password
    * */
    public String albumName;
    public String description;
    public int datetime;
    public String albumType;
    public String albumPassword;

    public Albums() { }

    public Albums(String albumName, String description, int datetime, String albumType, String albumPassword) {
        this.albumName = albumName;
        this.description = description;
        this.datetime = datetime;
        this.albumType = albumType;
        this.albumPassword = albumPassword;
    }

}
