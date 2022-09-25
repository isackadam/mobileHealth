package com.example.clinicmanagementsystem.AppointmentAPI;


public interface AppointmentViewFetchData {
    void onUpdateSuccess(AppointmentInfo message);
    void onUpdateFailure(String message);
}
