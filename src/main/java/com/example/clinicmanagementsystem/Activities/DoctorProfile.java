package com.example.clinicmanagementsystem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.clinicmanagementsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorProfile extends Activity {
    private String fname, femail, fspecialist, fphone, ftoken;
    private TextView edname, edemail, edspecialist;
    private EditText edphone;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        // fetching the data from the login page
        fname = getIntent().getStringExtra("fname");
        femail = getIntent().getStringExtra("femail");
        fspecialist = getIntent().getStringExtra("fspecialize");
        fphone = getIntent().getStringExtra("fphoneNo");
        ftoken = getIntent().getStringExtra("ftoken");
        //link variable to the UI elements
        edname = findViewById(R.id.setNameProfiled);
        edspecialist = findViewById(R.id.setSpecialistProfiled);
        edemail = findViewById(R.id.setEmailProfiled);
        edphone = findViewById(R.id.setPhoneProfiled);

        //set the variables to the user data
        edname.setText(fname);
        edemail.setText(femail);
        edphone.setText(fphone);
        edspecialist.setText(fspecialist);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoctorProfile.this, DoctorPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("fspecialize",fspecialist);
        intent.putExtra("fphoneNo", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }

    public void onViewAppointmentClick(View view) {
        Intent intent = new Intent(DoctorProfile.this, AppointmentSchedule.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("fspecialize",fspecialist);
        intent.putExtra("fphoneNo", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }

    public void onUpdateProfileClick(View view) {
        String phone = edphone.getText().toString().trim();
        if(!TextUtils.isEmpty(phone)){
            //update record
            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference record = firebaseFirestore.collection("DoctorRecords").document(ftoken);
            record.update("phoneNo",phone)
                    .addOnSuccessListener(new OnSuccessListener< Void >() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DoctorProfile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                            fphone= phone;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DoctorProfile.this, "Something went wrong, check the internet connection",Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            if(TextUtils.isEmpty(phone)){
                edname.setError("Name is required!");
                return;
            }
        }
    }
}
