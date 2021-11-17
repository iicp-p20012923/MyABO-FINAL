package com.example.myabo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myabo.DatabaseHelper.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register2ndActivity extends AppCompatActivity {

    TextInputLayout password, cfmPassword;
    Button registerBtn;
    long maxId = 0;

    //Firebase database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2nd);

        password = findViewById(R.id.register_password);
        cfmPassword = findViewById(R.id.register_cfmPassword);

        registerBtn = findViewById(R.id.register_create_button);

        //get register step 1 data
        String mFullName = getIntent().getStringExtra("name");
        String mNRIC = getIntent().getStringExtra("nric");
        String mEmail = getIntent().getStringExtra("email");
        String mPhoneNum = getIntent().getStringExtra("phoneNum");




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxId = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validation
                if(!validatePassword() | !validateConfirmedPassword()){
                    return;
                }
                String mPassword = password.getEditText().getText().toString().trim();
                String mCfmPassword = cfmPassword.getEditText().getText().toString().trim();

                //convert userId to string
                long userId = maxId+1;
                String sUserId = Long.toString(userId);

                UserHelperClass userHelper = new UserHelperClass(sUserId,mFullName,mNRIC,mEmail,mPhoneNum,mPassword,mCfmPassword);

                myRef.child(String.valueOf(mNRIC)).setValue(userHelper);

                //show success msg
                //Toast.makeText(getApplicationContext(), (int) userId, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });



    }

    public void callLoginScreen(View view){
        startActivity(new Intent(Register2ndActivity.this, LoginActivity.class));
    }

    public void createAccount(View view){

    }
    
    private boolean validatePassword(){
        String value = password.getEditText().getText().toString().trim();
        String checkPassword = ".{6,}";

        if(value.isEmpty()){
            password.setError("Field is required");
            return false;
        }
        else if(!value.matches(checkPassword)){
            password.setError("Password must be at least 6 characters!");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmedPassword(){
        String value = password.getEditText().getText().toString().trim();
        String cfmPass = cfmPassword.getEditText().getText().toString().trim();
        String checkPassword = ".{6,}";

        if(cfmPass.isEmpty()){
            cfmPassword.setError("Field is required");
            return false;
        }
//        else if(!cfmPass.matches(checkPassword)){
//            cfmPassword.setError("Password must be at least 6 characters!");
//            return false;
//        }
        else if(!cfmPass.equals(value)){
            cfmPassword.setError("Confirm Password does not match!");
            return false;
        }
        else {
            cfmPassword.setError(null);
            cfmPassword.setErrorEnabled(false);
            return true;
        }
    }
}