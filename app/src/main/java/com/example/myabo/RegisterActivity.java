package com.example.myabo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myabo.DatabaseHelper.UserHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    //Variables
    Button next, login;

    //Data variables
    TextInputLayout fullName, NRIC, email, phoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        next = findViewById(R.id.register_next_button);
        login = findViewById(R.id.register_login_button);

        //data
        fullName = findViewById(R.id.register_fullName);
        NRIC = findViewById(R.id.register_nric);
        email = findViewById(R.id.register_email);
        phoneNum = findViewById(R.id.register_phoneNum);

    }

    public void callLoginScreen(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public void callNextRegisterScreen(View view){
        //validation
        if(!validateFullName() | !validateEmail() | !validateNRIC() | !validatePhone()){
            return;
        }

        String mFullName = fullName.getEditText().getText().toString().trim();
        String mNRIC = NRIC.getEditText().getText().toString().trim();
        String mEmail = email.getEditText().getText().toString().trim();
        String mPhoneNum = phoneNum.getEditText().getText().toString().trim();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.orderByChild("NRIC").equalTo(mNRIC).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //it means user already registered
                    //Add code to show your prompt
                    phoneNum.setError("NRIC already registered");
                }
                else{
                    phoneNum.setError(null);
                    phoneNum.setErrorEnabled(false);

                    Intent intent = new Intent(getApplicationContext(), Register2ndActivity.class);

                    intent.putExtra("name", mFullName);
                    intent.putExtra("nric", mNRIC);
                    intent.putExtra("email", mEmail);
                    intent.putExtra("phoneNum", mPhoneNum);

                    startActivity(intent);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean validateFullName(){
        String value = fullName.getEditText().getText().toString().trim();

        if(value.isEmpty()){
            fullName.setError("Field is required");
            return false;
        }else{
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNRIC(){
        String value = NRIC.getEditText().getText().toString().trim();

        if(value.isEmpty()){
            NRIC.setError("Field is required");
            return false;
        }
        else if(value.length() != 12){
            NRIC.setError("Must be 12 numbers");
            return false;
        }
        else {
            NRIC.setError(null);
            NRIC.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String value = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(value.isEmpty()){
            email.setError("Field is required");
            return false;
        }
        else if(!value.matches(checkEmail)){
            email.setError("Invalid Email!");
            return false;
        }
        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String value = phoneNum.getEditText().getText().toString().trim();
        String checkPhone = "^[+]?[0-9]{10,11}$";

        if(value.isEmpty()){
            phoneNum.setError("Field is required");
            return false;
        }
        else if(!value.matches(checkPhone)){
            phoneNum.setError("Invalid Phone Number! (Ex. 0163332000)");
            return false;
        }
        else {
            phoneNum.setError(null);
            phoneNum.setErrorEnabled(false);
            return true;
        }
    }



}