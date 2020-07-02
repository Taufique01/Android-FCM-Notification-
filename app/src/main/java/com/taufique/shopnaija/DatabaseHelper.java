package com.taufique.shopnaija;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notifications_db";

    public DatabaseHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Notification.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Notification.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }




    public long insertNotification(String title,String description,String url,String imgurl) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Notification.COLUMN_TITLE, title);
        values.put(Notification.COLUMN_DESCRIPTION, description);
        values.put(Notification.COLUMN_IMG_URL, imgurl);
        values.put(Notification.COLUMN_URL, url);

        // insert row
        long id = db.insert(Notification.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public Notification getNotification(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Notification.TABLE_NAME,
                new String[]{Notification.COLUMN_ID, Notification.COLUMN_TITLE, Notification.COLUMN_DESCRIPTION, Notification.COLUMN_IMG_URL, Notification.COLUMN_URL},

                Notification.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Notification notification = new Notification(
                cursor.getInt(cursor.getColumnIndex(Notification.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Notification.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Notification.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Notification.COLUMN_URL)),
                cursor.getString(cursor.getColumnIndex(Notification.COLUMN_IMG_URL))
        );

        // close the db connection
        cursor.close();

        return notification;
    }


     public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Notification.TABLE_NAME + " ORDER BY " +
                Notification.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification(

                        cursor.getInt(cursor.getColumnIndex(Notification.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Notification.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Notification.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Notification.COLUMN_URL)),
                        cursor.getString(cursor.getColumnIndex(Notification.COLUMN_IMG_URL))

                );
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notifications;
    }





    public int getNotificationsCount() {
        String countQuery = "SELECT  * FROM " + Notification.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Log.d("db","ddb:"+cursor.getCount());


        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }




    public void deleteNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Notification.TABLE_NAME, Notification.COLUMN_ID + " = ?",
                new String[]{String.valueOf(notification.getId())});
        db.close();
    }

    public void deleteLastNotification() {
        String query="Delete from " + Notification.TABLE_NAME + " where " + Notification.COLUMN_ID + " IN " + "( Select "+ Notification.COLUMN_ID + " from "+ Notification.TABLE_NAME+"   ORDER BY "+ Notification.COLUMN_ID+ " ASC  limit 3 )";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query, null);
        Log.d("db2","ddb:"+cursor.getCount());

        cursor.close();
    }
}
