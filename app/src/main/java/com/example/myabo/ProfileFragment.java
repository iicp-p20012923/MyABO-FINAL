package com.example.myabo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Update;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;


public class ProfileFragment extends Fragment {

    Button profileBtn, attentionNote, privilege;

    TextView fullName, NRIC, totalTimesCount, totalVolumeCount;

    DatabaseReference historyDatabase = FirebaseDatabase.getInstance().getReference().child("histories");
    DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //Button
        profileBtn = v.findViewById(R.id.profile_update_btn);
        attentionNote = (Button) v.findViewById(R.id.btnAttentionNote);
        privilege = (Button) v.findViewById(R.id.btnPrivilege);
        //Text View
        fullName = (TextView) v.findViewById(R.id.profile_full_name);
        NRIC = (TextView) v.findViewById(R.id.profile_nric);
        totalTimesCount = (TextView) v.findViewById(R.id.total_times_count);
        totalVolumeCount = (TextView) v.findViewById(R.id.text_volume);

        historyDatabase.keepSynced(true);


        // get Bundle arguments
        Bundle bundle = getArguments();
        String nameDB = bundle.getString("name");
        String nricDB = bundle.getString("nric");
//        String emailDB = bundle.getString("email");
//        String phoneNumDB = bundle.getString("phoneNum");
        String userIdDB = bundle.getString("userId");

        fullName.setText(nameDB);
        NRIC.setText(nricDB);


        String userIdString = String.valueOf(userIdDB);

        Query query = historyDatabase.orderByChild("userId").equalTo(userIdString);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String maxId = String.valueOf(snapshot.getChildrenCount());
                    totalTimesCount.setText("x" + maxId);

                    int sum = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object bloodAmount = map.get("bloodAmount");
                        int pValue = Integer.parseInt(String.valueOf(bloodAmount));
                        sum += pValue;
                    }
                    Float totalAmnt = (float) sum / 1000;
                    totalVolumeCount.setText(totalAmnt + " L");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        attentionNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AttentionNoteFragment attentionNoteFragment = new AttentionNoteFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, attentionNoteFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().setTitle("Attention Note for blood donors");


            }
        });

        privilege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivilegesFragment privilegesFragment = new PrivilegesFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, privilegesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getActivity().setTitle("Privileges for Blood Donors");
            }
        });

        //Edit Button
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNRIC = NRIC.getText().toString();
                mDatabaseReference.child(getNRIC).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataSnapshot dataSnapshot = task.getResult();
                            String email = String.valueOf(dataSnapshot.child("email").getValue());
                            String name = String.valueOf(dataSnapshot.child("name").getValue());
                            String phoneNo = String.valueOf(dataSnapshot.child("phoneNum").getValue());


                            Intent intent = new Intent((getActivity()).getBaseContext(), EditProfileActivity.class);
                            intent.putExtra("SENDER_KEY", "ProfileFragment");
                            intent.putExtra("emailUpdated", email);
                            intent.putExtra("nameUpdated", name);
                            intent.putExtra("phoneUpdated", phoneNo);
                            intent.putExtra("NRIC", getNRIC);
                            getActivity().startActivity(intent);

                        }
                    }
                });


            }
        });
        return v;
    }


}




