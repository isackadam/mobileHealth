package com.example.clinicmanagementsystem.DoctorAPI;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class DoctorUploadData implements DoctorPresenterData, DoctorViewMessage{
    private DoctorViewMessage doctorViewMessage;


    public DoctorUploadData(DoctorViewMessage doctorViewMessage){
        this.doctorViewMessage = doctorViewMessage;
    }


    @Override
    public void onSuccessUpdate(Activity activity, String name, String email, String phoneNo, String specialize, String password, String token) {
        DoctorInfo doctorInfo = new DoctorInfo( name,  email,  phoneNo,  specialize, password, token);
        FirebaseFirestore.getInstance().collection("DoctorRecords").document(token).set(doctorInfo, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    doctorViewMessage.onUpdateSuccess("Doctor has been registered");
                }
                else{
                    doctorViewMessage.onUpdateFailure("Ohh Something went wrong, try again later");
                }
            }
        });
    }

    @Override
    public void onUpdateFailure(String message) {

        doctorViewMessage.onUpdateFailure(message);
    }

    @Override
    public void onUpdateSuccess(String message) {

        doctorViewMessage.onUpdateSuccess(message);
    }
}
