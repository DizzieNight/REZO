package com.example.rezo.model;

public class DataBookings {
    private int bookingsID;
    private int bookingsUserID;
    private int bookingsRoomID;
    private String bookingsDate;
    private String bookingsTime;

    public DataBookings() {

    }

    public DataBookings(int bookingsID, int bookingsUserID, int bookingsRoomID, String bookingsDate, String bookingsTime) {
        this.bookingsID = bookingsID;
        this.bookingsUserID = bookingsUserID;
        this.bookingsRoomID = bookingsRoomID;
        this.bookingsDate = bookingsDate;
        this.bookingsTime = bookingsTime;
    }

    public DataBookings(int bookingsRoomID, int bookingsUserID, String bookingsDate, String bookingsTime) {
        this.bookingsRoomID = bookingsRoomID;
        this.bookingsUserID = bookingsUserID;
        this.bookingsDate = bookingsDate;
        this.bookingsTime = bookingsTime;
    }

    public int getBookingsRoomID() {
        return this.bookingsRoomID;
    }

    public void setBookingsRoomID(int bookingsRoomID) {
        this.bookingsRoomID = bookingsRoomID;
    }

    public void setBookingsID(int bookingsID) {
        this.bookingsID = bookingsID;
    }

    public int getBookingsID() {
        return this.bookingsID;
    }

    public void setBookingsUserID(int bookingsUserID) {
        this.bookingsUserID = bookingsUserID;
    }

    public int getBookingsUserID() {
        return this.bookingsUserID;
    }

    public void setBookingsDate(String bookingsDate) {
        this.bookingsDate = bookingsDate;
    }

    public String getBookingsDate() {
        return this.bookingsDate;
    }

    public void setBookingsTime(String bookingsTime) {
        this.bookingsTime = bookingsTime;
    }

    public String getBookingsTime() {
        return this.bookingsTime;
    }
}
