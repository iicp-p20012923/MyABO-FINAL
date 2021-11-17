package com.example.myabo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myabo.DatabaseHelper.HistoryHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    ArrayList<HistoryHelperClass> mArrayList;

    DatabaseReference historyDatabase = FirebaseDatabase.getInstance().getReference().child("histories");
    DatabaseReference eventDatabase = FirebaseDatabase.getInstance().getReference().child("events");

    public HistoryAdapter(ArrayList<HistoryHelperClass> mArrayList){
        this.mArrayList = mArrayList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_history_item, viewGroup,false);

        //Query query = historyDatabase.orderByChild("eventId").equalTo(eventDatabase.orderByChild("eventId"));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.eventName.setText(mArrayList.get(position).getEventName());
        myViewHolder.bloodAmount.setText("Blood Amount: "+mArrayList.get(position).getBloodAmount());
        myViewHolder.bloodPressure.setText("Blood Pressure: "+mArrayList.get(position).getBloodPressure());
        myViewHolder.bloodSerialNo.setText("Blood Serial No.: "+mArrayList.get(position).getBloodSerialNo());
        myViewHolder.bloodOxygenLevel.setText("Blood Oxygen Level: "+mArrayList.get(position).getBloodOxygenLevel());
        myViewHolder.remark.setText("Remark: "+mArrayList.get(position).getRemark());
        myViewHolder.date.setText(mArrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventName, bloodAmount, bloodOxygenLevel, bloodSerialNo, bloodPressure, remark,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.history_event_name);
            bloodAmount = itemView.findViewById(R.id.history_amount);
            bloodOxygenLevel = itemView.findViewById(R.id.history_bo);
            bloodPressure = itemView.findViewById(R.id.history_bp);
            bloodSerialNo = itemView.findViewById(R.id.history_bsn);
            remark =itemView.findViewById(R.id.history_remark);
            date =itemView.findViewById(R.id.history_date);

        }
    }

}
