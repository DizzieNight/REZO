package com.example.rezo.databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.rezo.bookingsValues;
import com.example.rezo.model.DataBookings;
import com.example.rezo.model.DataItem;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "rezoDB.db";
    public static final int DB_VERSION = 1;


    public DBHelper(@Nullable Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(RoomsTable.SQL_CREATE);
        db.execSQL(BookingsTable.SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RoomsTable.SQL_DELETE);
        db.execSQL(BookingsTable.SQL_CREATE);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void addRoom (DataItem dataItem) {
        ContentValues values = new ContentValues();
        values.put(RoomsTable.COLUMN_NAME, dataItem.getRoomName());
        values.put(RoomsTable.COLUMN_ID, dataItem.getRoomID());

        SQLiteDatabase db = this.getWritableDatabase();

        db.replace(RoomsTable.TABLE_ROOMS, null, values);
        //db.close();
    }

    public DataItem queryRoom (String roomID) {
        String query = "SELECT * FROM " + RoomsTable.TABLE_ROOMS + " WHERE " + RoomsTable.COLUMN_NAME + " = \"" + roomID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        DataItem dataItem = new DataItem();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            dataItem.setRoomID(Integer.parseInt(cursor.getString(0)));
            dataItem.setRoomName(cursor.getString(1));
            cursor.close();
        }
        else {
            dataItem = null;
        }
        db.close();
        return dataItem;
    }

    public int getRoomID (String roomName) {
        int returnInt;
        String query = "SELECT * FROM " + RoomsTable.TABLE_ROOMS + " WHERE " + RoomsTable.COLUMN_NAME + " = \"" + roomName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        DataItem dataItem = new DataItem();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            dataItem.setRoomID(Integer.parseInt(cursor.getString(0)));
            dataItem.setRoomName(cursor.getString(1));
            cursor.close();
            returnInt = dataItem.getRoomID();
        }
        else {
            returnInt = 0;
        }
        db.close();
        return returnInt;
    }

    public boolean queryAvailability (int roomID, String date, String time) {
        boolean bookingExist;

        String query = "SELECT * FROM " + BookingsTable.TABLE_BOOKINGS + " WHERE " + BookingsTable.COLUMN_ROOMID + " = " + roomID + " AND " + BookingsTable.COLUMN_DATE + " = '" + date + "'" + " AND " + BookingsTable.COLUMN_TIME + " = '" + time + "'" + ";";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        DataBookings dataBookings = new DataBookings();

        if (cursor.moveToFirst()) {
            dataBookings.setBookingsRoomID(Integer.parseInt(cursor.getString(1)));
            dataBookings.setBookingsDate((cursor.getString(3)));
            dataBookings.setBookingsTime((cursor.getString(4)));
            cursor.close();

            bookingExist = false;
        }
        else {
            bookingExist = true;
        }
        db.close();
        return bookingExist;
    }

    public bookingsValues queryAvailability (int userID) {

        String query = "SELECT * FROM " + BookingsTable.TABLE_BOOKINGS + " WHERE " + BookingsTable.COLUMN_USERID + " = " + userID + ";";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        String[] roomName = new String[(cursor.getCount())+1];
        String[] date = new String[(cursor.getCount())+1];
        String[] time = new String[(cursor.getCount())+1];

        bookingsValues bookingsValues = new bookingsValues();

        if (cursor.moveToFirst()) {
            for (int i = 1; i<=cursor.getCount(); i++) {
                roomName[i] = cursor.getString(1);
                date[i] = cursor.getString(3);
                time[i] = cursor.getString(4);
                bookingsValues.addRoomName(roomName[i]);
                bookingsValues.addDate(date[i]);
                bookingsValues.addTime(time[i]);
                bookingsValues.changeValueCount(cursor.getCount());
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        else {
            db.close();
            return null;
        }

        return bookingsValues;
    }

    public void addBooking(DataBookings dataBookings) {
        ContentValues values = new ContentValues();
        values.put(BookingsTable.COLUMN_ROOMID, dataBookings.getBookingsRoomID());
        values.put(BookingsTable.COLUMN_USERID, dataBookings.getBookingsUserID());
        values.put(BookingsTable.COLUMN_DATE, dataBookings.getBookingsDate());
        values.put(BookingsTable.COLUMN_TIME, dataBookings.getBookingsTime());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(BookingsTable.TABLE_BOOKINGS, null, values);
        //db.close();
    }

    public void removeBooking(String roomID, String date, String time) {

        String query = "SELECT * FROM " + BookingsTable.TABLE_BOOKINGS + " WHERE " + BookingsTable.COLUMN_ROOMID + " = " + roomID + " AND " + BookingsTable.COLUMN_DATE + " = '" + date + "'" + " AND " + BookingsTable.COLUMN_TIME + " = '" + time + "'" + ";";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        DataBookings dataBookings = new DataBookings();

        if (cursor.moveToFirst()) {
            dataBookings.setBookingsRoomID(Integer.parseInt(cursor.getString(1)));
            dataBookings.setBookingsDate(cursor.getString(3));
            dataBookings.setBookingsTime(cursor.getString(4));
            db.delete(BookingsTable.TABLE_BOOKINGS, BookingsTable.COLUMN_ROOMID + " =?" + " and " + BookingsTable.COLUMN_DATE + " =?" + " and " + BookingsTable.COLUMN_TIME + " =?",
                    new String[] {String.valueOf(dataBookings.getBookingsRoomID()), dataBookings.getBookingsDate(), dataBookings.getBookingsTime()});
            cursor.close();
        }
        //db.close();
    }
}
