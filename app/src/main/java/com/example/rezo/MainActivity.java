package com.example.rezo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezo.databse.DBHelper;
import com.example.rezo.model.DataBookings;
import com.example.rezo.model.DataItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnDeleteButtonListener {

    private TextView floorsPaneTitle;
    private TextView availabilityText;
    private Button bookButton;
    private TextView dateText;
    private Spinner timeSpinner;
    DatePickerDialog.OnDateSetListener setListener;
    protected ArrayList<bookingList> listBookingList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    public MainActivity() {
    }


    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 1; i <= 10; i++) {
            DBHelper dbHelper = new DBHelper(this);
            String name = "Room " + Integer.parseInt(String.valueOf(i));
            DataItem dataItem = new DataItem(i, name, 2);
            dbHelper.addRoom(dataItem);
        }

        // INITIALISE THE SPINNER AND THE ONITEMSELECTEDLISTENER
        Spinner floorSpinner = findViewById(R.id.floorSpinner);
        floorSpinner.setOnItemSelectedListener(floorSpinnerListener);
        floorsPaneTitle = findViewById(R.id.roomsPaneTitle);
        availabilityText = findViewById(R.id.availabilityText);
        bookButton = findViewById(R.id.bookButton);
        dateText = findViewById(R.id.editTextDate);

        timeSpinner = findViewById(R.id.setTime);
        timeSpinner.setOnItemSelectedListener(timeSpinnerListener);

        dateText.setInputType(InputType.TYPE_NULL);

        availabilityText.setVisibility(View.INVISIBLE);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

// TOAST A MESSAGE SAYING IF THE DATABASE HAS BEEN ACQUIRED OR NOT
//        Toast.makeText(this, "Database Acquired", Toast.LENGTH_SHORT).show();
        mAdapter = new MyRecyclerViewAdapter(listBookingList, this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new
        LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareItems();

//
// SETS THE FRAGMENT TO THE FIRST FLOOR
        FirstFloorFragment fragment = new FirstFloorFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FragmentCON, fragment)
                .commit();
// SELECTING THE NAVIGATION BAR AND SETTING THE TRANSITION
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_floors:
                    ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.floorsTransition);
                    ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
                    break;
                case R.id.navigation_home:
                    ((MotionLayout) findViewById(R.id.motionLayout)).transitionToStart();
                    break;
                case R.id.navigation_bookings:
                    ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.settingsTransition);
                    ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
                    break;
            }

            return true;
        });

        Calendar calendar =  Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth
                    ,setListener,year,month,day);
            datePickerDialog.getWindow().setBackgroundDrawable((new ColorDrawable((Color.TRANSPARENT))));
            datePickerDialog.show();
        });
        setListener  = (view, year1, month1, dayOfMonth) -> {
            month1 = month1 +1;
            String date = dayOfMonth + "/" + month1 + "/" + year1;
            dateText.setText(date);
        };
    }

// CHANGING THE LAYOUT OF THE FLOORS DEPENDING ON WHICH OPTION IS SELECTED IN THE FLOOR SPINNER
    private final AdapterView.OnItemSelectedListener floorSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch ((int) id) {
                case 0:
                    FirstFloorFragment FirstFloorfragment = new FirstFloorFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.FragmentCON, FirstFloorfragment)
                            .commit();
                    break;
                case 1:
                    SecondFloorFragment SecondFloorfragment = new SecondFloorFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.FragmentCON, SecondFloorfragment)
                            .commit();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void prepareItems(){
        DBHelper dbHelper = new DBHelper(this);
        try {
            bookingsValues list = dbHelper.queryAvailability(1);
            for (int i = 0; i < list.getValueCount(); i++) {
                String roomName = "Room " + list.getRoomName().get(i);
                String date = list.getDate().get(i);
                String time = list.getTime().get(i);
                bookingList listAdd = new bookingList(roomName, date, time);
                listBookingList.add(listAdd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removeItem(int position) {
        listBookingList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void addItems(String newRoomName, String newDate, String newTime){
        bookingList list = new bookingList(newRoomName, newDate, newTime);
        listBookingList.add(list);
    }

    private void removeBooking(String newRoomName, String newDate, String newTime) {
        DBHelper dbHelper = new DBHelper(this);

        dbHelper.removeBooking(newRoomName, newDate, newTime);
    }

    @Override
    public void onDeleteButtonClick(int position) {
        DBHelper dbHelper = new DBHelper(this);
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(listBookingList, this);
//        bookingsValues list = new bookingsValues();
        bookingsValues list = dbHelper.queryAvailability(1);

        String roomName = list.getRoomName().get(position);
        String date = list.getDate().get(position);
        String time = list.getTime().get(position);

        removeBooking(roomName, date, time);
        removeItem(position);

        myRecyclerViewAdapter.notifyItemRemoved(position);
    }

    private final AdapterView.OnItemSelectedListener timeSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (id >= 1) {
                availabilityFunction();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void availabilityFunction() {
        DBHelper dbHelper = new DBHelper(this);
        String roomName = floorsPaneTitle.getText().toString();
        int roomID = Integer.parseInt(String.valueOf(dbHelper.getRoomID(roomName)));
        String date = dateText.getText().toString();
        String time = timeSpinner.getSelectedItem().toString();

        boolean availability = dbHelper.queryAvailability(roomID, date, time);
        if (availability) {
            availabilityText.setText("Available");
            availabilityText.setTextColor(getColor(R.color.availabilityGreen));
            availabilityText.setVisibility(View.VISIBLE);
            bookButton.setEnabled(true);
        }
        else {
            availabilityText.setText("Not Available");
            availabilityText.setTextColor(getColor(R.color.availabilityRed));
            availabilityText.setVisibility(View.VISIBLE);
            bookButton.setEnabled(false);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bookButtonPressed(View view) {
        DBHelper dbHelper = new DBHelper(this);

        String string = floorsPaneTitle.getText().toString();

        String date = dateText.getText().toString();
        String time = timeSpinner.getSelectedItem().toString();
        int roomID = Integer.parseInt(String.valueOf(dbHelper.getRoomID(string)));
        DataBookings dataBookings = new DataBookings(roomID, 1 ,date, time);
        dbHelper.addBooking(dataBookings);

        addItems(string, date, time);

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

    }

// CHANGING THE ROOM TAB DEPENDING ON WHICH ROOM IS SELECTED
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room1ButtonPressed(View view) {
        String roomID = "Room " + 1;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(dataItem.getRoomName());

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room2ButtonPressed(View view) {
        String roomID = "Room " + 2;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room3ButtonPressed(View view) {
        String roomID = "Room " + 3;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room4ButtonPressed(View view) {
        String roomID = "Room " + 4;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room5ButtonPressed(View view) {
        String roomID = "Room " + 5;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room6ButtonPressed(View view) {
        String roomID = "Room " + 6;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(dataItem.getRoomName());

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room7ButtonPressed(View view) {
        String roomID = "Room " + 7;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room8ButtonPressed(View view) {
        String roomID = "Room " + 8;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room9ButtonPressed(View view) {
        String roomID = "Room " + 9;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void room10ButtonPressed(View view) {
        String roomID = "Room " + 10;

        DBHelper dbHelper = new DBHelper(this);
        DataItem dataItem = dbHelper.queryRoom(roomID);
        floorsPaneTitle.setText(String.valueOf(dataItem.getRoomName()));

        dateText.setText("");
        timeSpinner.setSelection(0);
        availabilityText.setVisibility(View.INVISIBLE);

        ((MotionLayout) findViewById(R.id.motionLayout)).setTransition(R.id.roomsTransition);
        ((MotionLayout) findViewById(R.id.motionLayout)).transitionToEnd();
    }


}