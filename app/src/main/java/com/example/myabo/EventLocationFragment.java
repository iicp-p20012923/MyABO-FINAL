package com.example.myabo;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myabo.DatabaseHelper.EventLocationHelperClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class EventLocationFragment extends Fragment {


    Spinner mSpinner;
    private RecyclerView eventList;
    private DatabaseReference mDatabaseReference;
    ArrayList<EventLocationHelperClass> mArrayList;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_location, container, false);


        mSpinner = (Spinner) v.findViewById(R.id.drop_down);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("events");
        mDatabaseReference.keepSynced(true);


        eventList = (RecyclerView) v.findViewById(R.id.event_list);
        eventList.setHasFixedSize(true);
        eventList.setLayoutManager(new LinearLayoutManager(getContext()));



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.state, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);


        if(mDatabaseReference!= null){
            mDatabaseReference.addListenerForSingleValueEvent(mValueEventListener);
        }
        else{
            // No events at all

//            Query checkNull = mDatabaseReference.orderByChild("location").equalTo(null);
//            mDatabaseReference.addListenerForSingleValueEvent(v.findViewById(R.id.no_event));

        }




        //spinner selection events
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mDatabaseReference == null){
//                    inflater.inflate(R.layout.no_event_list, container, false);

                }
                else{
                    Query query = mDatabaseReference;

                    if (position == 0){
                        query = mDatabaseReference;
                    }
                    else if (position == 1){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Johor");
                    }
                    else if (position == 2){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Kedah");
                    }
                    else if (position == 3){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Kelantan");
                    }
                    else if (position == 4){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Melaka");
                    }
                    else if (position == 5){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Negeri Sembilan");
                    }
                    else if (position == 6){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Pahang");

                    }
                    else if (position == 7){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Pulau Pinang");

                    }
                    else if (position == 8){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Perak");
                    }
                    else if (position == 9){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Perlis");
                    }
                    else if (position == 10){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Sabah");
                    }
                    else if (position == 11){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Sarawak");
                    }
                    else if (position == 12){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Selangor");
                    }
                    else if (position == 13){
                        query = mDatabaseReference
                                .orderByChild("location")
                                .equalTo("Terengganu");

                    }

                    query.addListenerForSingleValueEvent(mValueEventListener);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){

                mArrayList = new ArrayList<>();

                for(DataSnapshot ds : snapshot.getChildren()){
                    mArrayList.add(ds.getValue(EventLocationHelperClass.class));

                }
                EventLocationAdapter eventLocationAdapter = new EventLocationAdapter(mArrayList);
                eventList.setAdapter(eventLocationAdapter);

            }
            else{
                mArrayList.clear();
                eventList.removeAllViews();

                Toast.makeText(getContext(),"No Event",Toast.LENGTH_LONG).show();



            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

}