package com.example.myabo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myabo.DatabaseHelper.EventLocationHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class EventLocationAdapter extends  RecyclerView.Adapter<EventLocationAdapter.MyViewHolder> {
    ArrayList<EventLocationHelperClass> mArrayList;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("events");

    public EventLocationAdapter(ArrayList<EventLocationHelperClass> mArrayList){
        this.mArrayList = mArrayList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //here check if database is null or not? thn chn
        //if null thn View view = no_event_list
        //else ini View view = list_event_item
       // mDatabaseReference = FirebaseDatabase.getInstance().getReference("events");
        View view;
        if (mDatabaseReference != null){
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_event_item, viewGroup,false);

        }
        else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.no_event_list, viewGroup, false);

        }
        return new MyViewHolder(view);

    }





    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.eventName.setText(mArrayList.get(position).getEventName());
        myViewHolder.address.setText(mArrayList.get(position).getAddress());
        myViewHolder.dateTime.setText(mArrayList.get(position).getDateTime());
        myViewHolder.bloodType.setText(mArrayList.get(position).getBloodType());
        myViewHolder.contactNo.setText(mArrayList.get(position).getContactNo());


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventName, address,dateTime, bloodType,contactNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.list_event_item_event_name);
            address = itemView.findViewById(R.id.list_event_item_address);
            dateTime = itemView.findViewById(R.id.list_event_item_date_time);
            bloodType =itemView.findViewById(R.id.list_event_item_blood_type);
            contactNo =itemView.findViewById(R.id.list_event_item_contact_no);

        }
    }



}
