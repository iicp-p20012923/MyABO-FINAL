package com.example.myabo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Delete;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //Initialize variable
    MeowBottomNavigation mBottomNavigation;
    private DatabaseReference mDatabaseReference;
    String mFullName, mEmail, mIc, mPhoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent data
        String userId = getIntent().getStringExtra("userId");
        //String stringUserId = String.valueOf(userId);
        Log.d("userId",userId);
        String fullName = getIntent().getStringExtra("name");
        String nric = getIntent().getStringExtra("nric");
        String email = getIntent().getStringExtra("email");
        String phoneNum = getIntent().getStringExtra("phoneNum");


        //Assign variable
        mBottomNavigation = findViewById(R.id.bottom_navigation);

        //Add menu item
        mBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_history));
        mBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        mBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_profile));

        mBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()) {
                    case 1:
                        //Initialize history fragment
                        fragment = new HistoryFragment();
                        setTitle("History");

                        Bundle bundleH = new Bundle();
                        bundleH.putString("userId", userId);
                        fragment.setArguments(bundleH);

                        break;
                    case 2:
                        //Initialize home fragment
                        fragment = new EventLocationFragment();
                        setTitle("Event Location");
                        break;
                    case 3:
                        //Initialize profile fragment
                        fragment = new ProfileFragment();
                        setTitle("Profile");
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", userId);

                        bundle.putString("name", fullName);
                        bundle.putString("nric", nric);
                        bundle.putString("email", email);
                        bundle.putString("phoneNum", phoneNum);

                        fragment.setArguments(bundle);

                        break;

                }
                //Load fragment
                loadFragment(fragment);

            }
        });

        //Set Home initially selected
        mBottomNavigation.show(2, true);

        mBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Do something when click
            }
        });

        mBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Do something when re-click
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new EventLocationFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


    }

    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Are you sure you want to log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


        }
        return super.onOptionsItemSelected(item);
    }


}