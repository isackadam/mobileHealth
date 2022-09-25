package com.example.clinicmanagementsystem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration.DoctorRegistration;
import com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration.Login;
import com.example.clinicmanagementsystem.R;

public class AdminPanel extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
    }

    public void onAddDoctorClick(View view) {
        Intent intent = new Intent(AdminPanel.this, DoctorRegistration.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onDoctorRecordsClick(View view) {
        Intent intent = new Intent(AdminPanel.this, DoctorRecords.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onPatientRecordsClick(View view) {
        Intent intent = new Intent(AdminPanel.this, PatientRecords.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onAppointmentRecordsClick(View view) {
        Intent intent = new Intent(AdminPanel.this, AppointmentRecords.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void onLogoutAdminClick(View view) {
        Intent intent = new Intent(AdminPanel.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AdminPanel.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
