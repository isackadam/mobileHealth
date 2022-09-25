package com.example.clinicmanagementsystem.DoctorAPI;

public interface DoctorViewFetchData {

    void onUpdateSuccess(DoctorInfo message);
    void onUpdateFailure(String message);
}
