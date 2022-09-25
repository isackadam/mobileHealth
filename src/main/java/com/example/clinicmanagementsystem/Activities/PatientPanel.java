package com.example.clinicmanagementsystem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration.Login;
import com.example.clinicmanagementsystem.R;

public class PatientPanel extends Activity {

    private String fname, femail, faddress, fphone, fage, ftoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_panel);
        // fetching the data from the login page
        fname = getIntent().getStringExtra("fname");
        femail = getIntent().getStringExtra("femail");
        faddress = getIntent().getStringExtra("faddress");
        fage= getIntent().getStringExtra("fage");
        fphone = getIntent().getStringExtra("fphone");
        ftoken = getIntent().getStringExtra("ftoken");
    }

    public void onCreateAppointmentClick(View view) {
        Intent intent = new Intent(PatientPanel.this, CreateAppointment.class);
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
        Intent intent = new Intent(PatientPanel.this, ReviewAppointment.class);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("faddress",faddress);
        intent.putExtra("fage", fage);
        intent.putExtra("fphone", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }


    public void onViewProfileClick(View view) {
        Intent intent = new Intent(PatientPanel.this, PatientProfile.class);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("faddress",faddress);
        intent.putExtra("fage", fage);
        intent.putExtra("ftoken", ftoken);
        intent.putExtra("fphone", fphone);
        startActivity(intent);
        finish();
    }

    public void onLogoutCustomerClick(View view) {
        Intent intent = new Intent(PatientPanel.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PatientPanel.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
