package com.wirednest.apps.hairstyle.db;


import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class Captures extends SugarRecord{
    public String captureName;
    public String image1;
    public String image2;
    //@Ignore
    public String image3;
    public String description;
    public String person;
    public String capturePassword;

    Albums album;

    public Captures (){}

    public Captures (String capture_name,String image_1,String image_2,String image_3,
                     String description, String person, String capture_password,Albums album){
        this.captureName = capture_name;
        this.image1 = image_1;
        this.image2 = image_2;
        this.image3 = image_3;
        this.description = description;
        this.person = person;
        this.capturePassword = capture_password;
        this.album = album;
    }
    public Captures (String capture_name,String image_1,String image_2,
                     String description, String person, String capture_password,Albums album){
        this.captureName = capture_name;
        this.image1 = image_1;
        this.image2 = image_2;
        this.image3 = "";
        this.description = description;
        this.person = person;
        this.capturePassword = capture_password;
        this.album = album;
    }
}
