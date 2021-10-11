package com.example.rezo;

import java.util.ArrayList;

public class bookingList {
    private String roomName;
    private String date;
    private String time;

//    bookingList(String roomName, String date, String time) {
//        this.roomName = roomName;
//        this.date = date;
//        this.time = time;
//    }

    public bookingList() {

    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public void add(bookingList list) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MyRecyclerViewAdapter.ViewHolder, list.getRoomName());
//    }

    private ArrayList<String> roomNameArray = new ArrayList<String>();
    private ArrayList<String> dateArray = new ArrayList<String>();
    private ArrayList<String> timeArray = new ArrayList<String>();
    private int counter;

    bookingList(String roomName, String date, String time) {
        this.roomName = roomName;
        this.date = date;
        this.time = time;
    }

    public ArrayList<String> getRoomNameArray() {
        return roomNameArray;
    }

    public ArrayList<String> getDateArray() {
        return dateArray;
    }

    public ArrayList<String> getTimeArray() {
        return timeArray;
    }

    public void addRoomName(String name) {
        roomNameArray.add(name);
    }

    public void addDate(String date) {
        dateArray.add(date);
    }

    public void addTime(String time) {
        dateArray.add(time);
    }

    public void changeValueCount(int count) {
        this.counter = count;
    }

    public int getValueCount() {
        return counter;
    }
}
