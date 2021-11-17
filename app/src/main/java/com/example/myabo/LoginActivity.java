package com.example.myabo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    //Data variables
    TextInputLayout nric, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //data
        nric = findViewById(R.id.login_nric);
        password = findViewById(R.id.login_password);


    }

    public void callRegisterScreen(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void loginAccount(View view){
        if(!validateNRIC() | !validatePassword()){
            return;
        }

        String mNRIC = nric.getEditText().getText().toString().trim();
        String mPassword = password.getEditText().getText().toString().trim();

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("users");
        //db phone = phone entered
        Query checkUser = dbReference.orderByChild("nric").equalTo(mNRIC);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String passwordDB = snapshot.child(mNRIC).child("password").getValue(String.class);

                    if (passwordDB.equals(mPassword)) {
                        String userIdDB = snapshot.child(mNRIC).child("userId").getValue(String.class);
                        String nameDB = snapshot.child(mNRIC).child("name").getValue(String.class);
                        String nricDB = snapshot.child(mNRIC).child("nric").getValue(String.class);
                        String emailDB = snapshot.child(mNRIC).child("email").getValue(String.class);
                        String phoneNumDB = snapshot.child(mNRIC).child("phoneNum").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        intent.putExtra("userId", userIdDB);
                        intent.putExtra("name", nameDB);
                        intent.putExtra("nric", nricDB);
                        intent.putExtra("email", emailDB);
                        intent.putExtra("phoneNum", phoneNumDB);

                        Toast.makeText(getApplicationContext(), "Login Successful ", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong password! ", Toast.LENGTH_SHORT).show();
                        //password.requestFocus();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Account not exists ", Toast.LENGTH_SHORT).show();
                    //phoneNum.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean validateNRIC(){
        String value = nric.getEditText().getText().toString().trim();

        if(value.isEmpty()){
            nric.setError("Field is required");
            return false;
        }
        else if(value.length() != 12){
            nric.setError("Must be 12 numbers");
            return false;
        }
        else {
            nric.setError(null);
            nric.setErrorEnabled(false);
            return true;
        }
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

}

