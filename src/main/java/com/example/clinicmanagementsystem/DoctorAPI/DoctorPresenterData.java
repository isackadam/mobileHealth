package com.example.clinicmanagementsystem.DoctorAPI;

import android.app.Activity;

public interface DoctorPresenterData {

    void onSuccessUpdate(Activity activity, String name,
                         String email, String phoneNo,
                         String specialize, String password,
                         String token);
}
