package com.example.clinicmanagementsystem.PatientAPI;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class PatientUploadData implements PatientPresenterData, PatientViewMessage{
    PatientViewMessage patientViewMessage;

    public PatientUploadData(PatientViewMessage patientViewMessage ) {
        this.patientViewMessage = patientViewMessage;
    }


    @Override
    public void onSuccessUpdate(Activity activity, String name, String email, String password, String age, String address, String phone, String token) {
        PatientInfo patientInfo = new PatientInfo( name, email,  password, age,  address,  phone, token);

        FirebaseFirestore.getInstance().collection("PatientRecords").document(token).set(patientInfo, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    patientViewMessage.onUpdateSuccess("You have registered successfully");
                }
                else{
                    patientViewMessage.onUpdateFailure("Ohh, Something went wrong, try again");
                }
            }
        });
    }

    @Override
    public void onUpdateFailure(String message) {
        patientViewMessage.onUpdateFailure(message);
    }

    @Override
    public void onUpdateSuccess(String message) {
        patientViewMessage.onUpdateSuccess(message);
    }
}
