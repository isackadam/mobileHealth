package com.example.clinicmanagementsystem.PatientAPI;

import android.app.Activity;

public interface PatientPresenterData {
    void onSuccessUpdate(Activity activity, String name, String email, String password, String age,
                         String address, String phone, String token);
}
