package com.example.clinicmanagementsystem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration.Login;
import com.example.clinicmanagementsystem.R;

public class DoctorPanel extends Activity {

    private String fname, femail, fspecialist, fphone, ftoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_panel);
        // fetching the data from the login page
        fname = getIntent().getStringExtra("fname");
        femail = getIntent().getStringExtra("femail");
        fspecialist = getIntent().getStringExtra("fspecialize");
        fphone = getIntent().getStringExtra("fphoneNo");
        ftoken = getIntent().getStringExtra("ftoken");
    }

    public void onRequestedAppointmentClick(View view) {
        Intent intent = new Intent(DoctorPanel.this, RequestedAppointment.class);
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
        Intent intent = new Intent(DoctorPanel.this, AppointmentSchedule.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("fspecialize",fspecialist);
        intent.putExtra("fphoneNo", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }

    public void onViewProfileClick(View view) {
        Intent intent = new Intent(DoctorPanel.this, DoctorProfile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("fspecialize",fspecialist);
        intent.putExtra("fphoneNo", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }

    public void onLogoutCustomerClick(View view) {
        Intent intent = new Intent(DoctorPanel.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoctorPanel.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
