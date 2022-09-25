package com.example.clinicmanagementsystem.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration.PatientRegistration;
import com.example.clinicmanagementsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientProfile extends Activity {

    private FirebaseFirestore firebaseFirestore;
    private String fname, femail, faddress, fphone, fage, ftoken;
    private TextView edname, edemail;
    private EditText  edaddress, edphone, edage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        // fetching the data from the login page
        fname = getIntent().getStringExtra("fname");
        femail = getIntent().getStringExtra("femail");
        faddress = getIntent().getStringExtra("faddress");
        fage= getIntent().getStringExtra("fage");
        fphone = getIntent().getStringExtra("fphone");
        ftoken = getIntent().getStringExtra("ftoken");

        //link variable to the UI elements
        edname = findViewById(R.id.setNameProfile);
        edaddress = findViewById(R.id.setAddressProfile);
        edage = findViewById(R.id.setAgeProfile);
        edemail = findViewById(R.id.setEmailProfile);
        edphone = findViewById(R.id.setPhoneProfile);

        //set the variables to the user data
        edname.setText(fname);
        edemail.setText(femail);
        edphone.setText(fphone);
        edage.setText(fage);
        edaddress.setText(faddress);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PatientProfile.this, PatientPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("faddress",faddress);
        intent.putExtra("fage", fage);
        intent.putExtra("fphone", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }

    public void onViewAppointmentClick(View view) {
        Intent intent = new Intent(PatientProfile.this, ReviewAppointment.class);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("faddress",faddress);
        intent.putExtra("fage", fage);
        intent.putExtra("fphone", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }

    public void onUpdateProfileClick(View view) {
        String name = edname.getText().toString().trim();
        String phone = edphone.getText().toString().trim();
        String age = edage.getText().toString().trim();
        String address = edaddress.getText().toString().trim();
        if(!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(phone)
                && !TextUtils.isEmpty(age)
                && !TextUtils.isEmpty(address)){
            //update record
            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference record = firebaseFirestore.collection("PatientRecords").document(ftoken);
            record.update("name", name, "address", address, "phone",phone, "age", age)
                    .addOnSuccessListener(new OnSuccessListener< Void >() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PatientProfile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                            fname= name;
                            fage= age;
                            faddress= address;
                            fphone= phone;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PatientProfile.this, "Something went wrong, check the internet connection",Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            if(TextUtils.isEmpty(name)){
                edname.setError("Name is required!");
                return;
            }if (TextUtils.isEmpty(phone)){
                edphone.setError("Phone NO is required!");
                return;
            }if (TextUtils.isEmpty(age)){
                edage.setError("Age is required!");
                return;
            }
            if (TextUtils.isEmpty(address)){
                edaddress.setError("Address is required!");
                return;
            }

        }

    }
}
