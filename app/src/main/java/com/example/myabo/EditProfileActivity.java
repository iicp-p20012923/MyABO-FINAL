package com.example.myabo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class EditProfileActivity extends AppCompatActivity {

    String fullname, email, phonenum, nric;
    EditText fullName, emailTxt, phoneNum;
    Button back, save;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");

        mReference = FirebaseDatabase.getInstance().getReference("users");


        fullName = findViewById(R.id.editTxt_name);
        emailTxt = findViewById(R.id.editTxt_email);
        phoneNum = findViewById(R.id.editTxt_phoneNo);
        save = findViewById(R.id.save_button);
        back = findViewById(R.id.bckBtn);

        //Determine who started this activity
        final String sender = this.getIntent().getExtras().getString("SENDER_KEY");
        //if its the fragment, then receive data
        if (sender != null) {
            receiveData();



        }


        //back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to profile fragment
                finish();
            }
        });


    }

    private void receiveData() {
        //receive data via intent
        Intent intent = getIntent();
        nric = intent.getStringExtra("NRIC");
        fullname = intent.getStringExtra("nameUpdated");
        email = intent.getStringExtra("emailUpdated");
        phonenum = intent.getStringExtra("phoneUpdated");

        //set data to textview;
        fullName.setText(fullname);
        emailTxt.setText(email);
        phoneNum.setText(phonenum);

    }



    //save button onClick
    public void update(View view) throws InterruptedException {
        String phonenumVal = phoneNum.getText().toString();
        String emailVal = emailTxt.getText().toString();
        if (phonenumVal.isEmpty() | emailVal.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty Field!", Toast.LENGTH_SHORT).show();

        }
        else if(!validateEmail() | !validatePhone()){
            return;
        }
        else if (isEmailChanged() || isPhoneNumChanged()) {
            phonenum = phonenumVal;
            email = emailVal;

            Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getApplicationContext(), "Data is same and cannot be updated", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isPhoneNumChanged() {
        String value = phoneNum.getText().toString().trim();
        if (!value.isEmpty()) {
            if (!phonenum.equals(value)) {

                mReference.child(nric).child("phoneNum").setValue(value);
                phonenum = value;
                return true;
            } else {

                return false;
            }
        }
        return false;

    }

    private boolean isEmailChanged() {
        String value = emailTxt.getText().toString().trim();
        if (!value.isEmpty()) {
            if (!email.equals(value)) {

                mReference.child(nric).child("email").setValue(value);
                email = value;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean validateEmail(){
        String value = emailTxt.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(!value.matches(checkEmail)){
            Toast.makeText(getApplicationContext(), "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validatePhone(){
        String value = phoneNum.getText().toString().trim();
        String checkPhone = "^[+]?[0-9]{10,11}$";

        if(!value.matches(checkPhone)){
            Toast.makeText(getApplicationContext(), "Invalid Phone Number! (Ex. 0163332000)", Toast.LENGTH_LONG).show();

            return false;
        }
        else {
            return true;
        }
    }
}