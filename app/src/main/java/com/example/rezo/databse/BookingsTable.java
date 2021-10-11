package com.example.rezo.databse;

public class BookingsTable {

    public static final String TABLE_BOOKINGS = "bookingsTable";
    public static final String COLUMN_ID = "bookingsID";
    public static final String COLUMN_USERID = "bookingsUserID";
    public static final String COLUMN_ROOMID = "bookingsRoomID";
    public static final String COLUMN_DATE = "bookingsDate";
    public static final String COLUMN_TIME = "bookingsTime";


    public static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKINGS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ROOMID + " INTEGER NOT NULL, " +
                    COLUMN_USERID + " INTEGER NOT NULL, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_TIME + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USERID +") REFERENCES " + "usersTable" + "(" + COLUMN_USERID + ")" +
                    "FOREIGN KEY (" + COLUMN_ROOMID +") REFERENCES " + RoomsTable.TABLE_ROOMS + "(" + COLUMN_ROOMID + ")" +
                    ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_BOOKINGS;
}
