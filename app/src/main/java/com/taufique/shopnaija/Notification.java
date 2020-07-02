package com.taufique.shopnaija;

public class Notification {

    public static final String TABLE_NAME = "notifications";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_IMG_URL = "imgurl";



    private int id;
    private String title;
    private String description;

    private String url;
    private String imgurl;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION+ " TEXT,"
                    + COLUMN_URL + " TEXT,"
                    + COLUMN_IMG_URL+ " TEXT"
                    + ")";

    public Notification() {

    }

    public Notification(int id, String title, String description, String url, String imgurl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imgurl = imgurl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImgurl() {
        return imgurl;
    }
}
