package com.example.clinicmanagementsystem.PatientAPI;

public interface PatientViewMessage {
    void onUpdateFailure(String message);
    void onUpdateSuccess(String message);
}
