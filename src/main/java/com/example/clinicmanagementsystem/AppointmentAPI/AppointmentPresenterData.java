package com.example.clinicmanagementsystem.AppointmentAPI;

import android.app.Activity;

public interface AppointmentPresenterData {

    void onSuccessUpdate(Activity activity, String doctorEmail, String doctorName,
                         String doctorPhone, String doctorSpecialize,
                         String patientEmail, String patientName,
                         String patientPhone, String patientAddress,
                         String patientAge, String date, String time,
                         String meetingLocation, String feedback,
                         String rate, String token);
}
