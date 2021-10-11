package com.example.rezo;

import java.util.ArrayList;

public class bookingsValues {
    private ArrayList<String> roomName = new ArrayList<String>();
    private ArrayList<String> dateString = new ArrayList<String>();
    private ArrayList<String> timeString = new ArrayList<String>();
    private int counter;

//    bookingsValues(String roomName, String date, String time) {
//        this.roomName = roomName;
//        this.dateString = date;
//        this.timeString = time;
//    }

    public ArrayList<String> getRoomName() {
        return roomName;
    }

    public ArrayList<String> getDate() {
        return dateString;
    }

    public ArrayList<String> getTime() {
        return timeString;
    }

    public void addRoomName(String name) {
        roomName.add(name);
    }

    public void addDate(String date) {
        dateString.add(date);
    }

    public void addTime(String time) {
        timeString.add(time);
    }

    public void changeValueCount(int count) {
        this.counter = count;
    }

    public int getValueCount() {
        return counter;
    }
}
