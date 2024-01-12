package com.corddt.link_manage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LinkManager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "links";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_URL = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_URL + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Code to upgrade database
    }

    public boolean addLink(Link link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, link.getName());
        cv.put(COLUMN_URL, link.getUrl());

        long insert = db.insert(TABLE_NAME, null, cv);
        return insert != -1;
    }

    public boolean updateLink(Link link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, link.getName());
        cv.put(COLUMN_URL, link.getUrl());

        int update = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(link.getId())});
        return update == 1;
    }

    public ArrayList<Link> getAllLinks() {
        ArrayList<Link> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int linkID = cursor.getInt(0);
                String linkName = cursor.getString(1);
                String linkURL = cursor.getString(2);

                Link newLink = new Link(linkID, linkName, linkURL);
                returnList.add(newLink);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return returnList;
    }
}
