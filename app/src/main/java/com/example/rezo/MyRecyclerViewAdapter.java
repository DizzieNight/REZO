package com.example.rezo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

//    private List<bookingList> listBookingList = new ArrayList<bookingList>();
    private ArrayList<bookingList> listBookingList = new ArrayList<bookingList>();
    private ArrayList<bookingsValues> listBookingValues = new ArrayList<bookingsValues>();
    private ImageButton deleteButton;
    private OnDeleteButtonListener mOnDeleteButtonListener;
//    private final AdapterView.OnItemClickListener listener;

    MyRecyclerViewAdapter(ArrayList<bookingList> mBookingList, OnDeleteButtonListener onDeleteButtonListener) {
        this.listBookingList = mBookingList;
        this.mOnDeleteButtonListener = onDeleteButtonListener;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view, mOnDeleteButtonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        final bookingList item = listBookingList.get(position);

        holder.roomName.setText(item.getRoomName());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return listBookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView roomName, date, time;
        OnDeleteButtonListener onDeleteButtonListener;

        public MyViewHolder(@NonNull @NotNull View itemView, OnDeleteButtonListener onDeleteButtonListener) {
            super(itemView);
            roomName = itemView.findViewById(R.id.bookingRoomName);
            date = itemView.findViewById(R.id.bookingDate);
            time = itemView.findViewById(R.id.bookingTime);
            this.onDeleteButtonListener = onDeleteButtonListener;
            itemView.setOnClickListener(this);
        }
        public List<bookingsValues> getlistBookingList() {
            return listBookingValues;
        }

        @Override
        public void onClick(View v) {
            onDeleteButtonListener.onDeleteButtonClick(getAdapterPosition());
        }
    }

    public interface OnDeleteButtonListener{
        void onDeleteButtonClick(int position);
    }
}
