package com.example.rezo.model;

public class DataItem {
    private int roomID;
    private String roomName;
    public  int availability_ID;

    public DataItem() {

    }

    public DataItem(int roomID, String roomName, int availabilityID) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.availability_ID = availability_ID;
    }

    public DataItem(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public int getAvailabilityID() {
        return this.availability_ID;
    }

    public void setAvailability_ID(int availability_ID) {
        this.availability_ID = availability_ID;
    }

//    public void setAvailabilityColumn(int availabilityColumn) {
//        this.availabilityColumn = availabilityColumn;
//    }


}
