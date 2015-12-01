package com.wirednest.apps.hairstyle.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Generated("org.jsonschema2pojo")
public class HairstylesObject {

    @SerializedName("hairstyleId")
    @Expose
    private int hairstyleId;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("hairstyleName")
    @Expose
    private String hairstyleName;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("hairsyleDescription")
    @Expose
    private String hairsyleDescription;
    @SerializedName("categoryDescription")
    @Expose
    private String categoryDescription;



    /**
     *
     * @param categoryName
     * @param hairstyleId
     * @param categoryDescription
     * @param categoryId
     * @param image
     * @param hairsyleDescription
     * @param hairstyleName
     */
    public HairstylesObject(int hairstyleId, int categoryId, String hairstyleName, String categoryName, String image, String hairsyleDescription, String categoryDescription) {
        this.hairstyleId = hairstyleId;
        this.categoryId = categoryId;
        this.hairstyleName = hairstyleName;
        this.categoryName = categoryName;
        this.image = image;
        this.hairsyleDescription = hairsyleDescription;
        this.categoryDescription = categoryDescription;
    }

    /**
     *
     * @return
     * The hairstyleId
     */
    public int getHairstyleId() {
        return hairstyleId;
    }

    /**
     *
     * @param hairstyleId
     * The hairstyleId
     */
    public void setHairstyleId(int hairstyleId) {
        this.hairstyleId = hairstyleId;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The categoryId
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The hairstyleName
     */
    public String getHairstyleName() {
        return hairstyleName;
    }

    /**
     *
     * @param hairstyleName
     * The hairstyleName
     */
    public void setHairstyleName(String hairstyleName) {
        this.hairstyleName = hairstyleName;
    }

    /**
     *
     * @return
     * The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     *
     * @param categoryName
     * The categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The hairsyleDescription
     */
    public String getHairsyleDescription() {
        return hairsyleDescription;
    }

    /**
     *
     * @param hairsyleDescription
     * The hairsyleDescription
     */
    public void setHairsyleDescription(String hairsyleDescription) {
        this.hairsyleDescription = hairsyleDescription;
    }

    /**
     *
     * @return
     * The categoryDescription
     */
    public String getCategoryDescription() {
        return categoryDescription;
    }

    /**
     *
     * @param categoryDescription
     * The categoryDescription
     */
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

}