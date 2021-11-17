package com.example.myabo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myabo.DatabaseHelper.HistoryHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private RecyclerView historyList;
    DatabaseReference historyDatabase = FirebaseDatabase.getInstance().getReference().child("histories");
    DatabaseReference eventDatabase = FirebaseDatabase.getInstance().getReference().child("events");
    DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    ArrayList<HistoryHelperClass> mArrayList;

    List<String> testList = new ArrayList<String>();


    TextView historyEventName;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        //mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("histories");
        historyDatabase.keepSynced(true);

        historyList = (RecyclerView) v.findViewById(R.id.history_list);
        historyList.setHasFixedSize(true);
        historyList.setLayoutManager(new LinearLayoutManager(getContext()));

        historyEventName = (TextView) v.findViewById(R.id.history_event_name);

        //get Bundle arguments
        Bundle bundle = getArguments();
        String userIdDB = bundle.getString("userId");
        String userIdString = String.valueOf(userIdDB);
        //Query string = historyDatabase.orderByChild("userId");


        Query query = historyDatabase.orderByChild("userId").equalTo(userIdString);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String test = snapshot.child("userId").getValue(String.class);
                    String event = snapshot.child(userIdString).child("eventId").getValue(String.class);

                    //Log.d("history event id", String.valueOf(event));
                    mArrayList = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        String event2 = ds.child("eventId").getValue(String.class);
                        String SerialNo = ds.child("bloodSerialNo").getValue(String.class);
                        Log.d("history event id", event2);
                        Log.d("history serial no", SerialNo);
                        testList.add(event2);


                        mArrayList.add(ds.getValue(HistoryHelperClass.class));



                    }
                    Log.d("List: ", String.valueOf(testList));
                    HistoryAdapter historyAdapter = new HistoryAdapter(mArrayList);
                    //HistoryEventAdapter historyEventAdapter = new HistoryEventAdapter(mArrayListEvent);
                    historyList.setAdapter(historyAdapter);
                    //historyList.setAdapter(historyEventAdapter);
                }
                else{
                    Log.d("history user id", "nooo");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(historyDatabase!= null){
//            historyDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()){
//                            mArrayList = new ArrayList<>();
//                            for(DataSnapshot ds : snapshot.getChildren()){
//                                historyDatabase.child("eventId").equalTo()
//                                eventDatabase.child("eventId").addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if(snapshot.exists()){
//                                            String mEventName = snapshot.child("eventId").child("eventName").getValue(String.class);
//                                            Toast.makeText(getContext(), mEventName, Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
////                                String mEventId = eventDatabase.child("eventId").get;
////                                Toast.makeText(getContext(), mEventId, Toast.LENGTH_SHORT).show();
////                                if(mEventId.equals(ds.child("eventId")){
////                                    String mEventName = eventDatabase.child("eventId").child("eventName").getKey();
////
////                                    historyEventName.setText(mEventName);
////                                }
//                                mArrayList.add(ds.getValue(HistoryHelperClass.class));
//                            }
//                            HistoryAdapter historyAdapter = new HistoryAdapter(mArrayList);
//                            historyList.setAdapter(historyAdapter);
//
//                        }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//
//    }



//    ValueEventListener mValueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            if (snapshot.exists()){
//                mArrayList = new ArrayList<>();
//                for(DataSnapshot ds : snapshot.getChildren()){
//                    mArrayList.add(ds.getValue(HistoryHelperClass.class));
//
//                }
//                HistoryAdapter historyAdapter = new HistoryAdapter(mArrayList);
//                historyList.setAdapter(historyAdapter);
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    };



}