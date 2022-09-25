package com.example.clinicmanagementsystem.PatientAPI;

public interface PatientViewFetchData {

    void onUpdateSuccess(PatientInfo message);
    void onUpdateFailure(String message);

}

