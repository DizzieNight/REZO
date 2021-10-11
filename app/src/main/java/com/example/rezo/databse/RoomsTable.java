package com.example.rezo.databse;

public class RoomsTable {

    public static final String TABLE_ROOMS = "items";
    public static final String COLUMN_ID = "roomID";
    public static final String COLUMN_NAME = "roomName";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ROOMS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY UNIQUE, " +
                    COLUMN_NAME + " TEXT" +
                    ");";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ROOMS;
}
