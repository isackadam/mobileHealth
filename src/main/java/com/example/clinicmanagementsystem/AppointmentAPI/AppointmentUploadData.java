package com.example.clinicmanagementsystem.AppointmentAPI;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.clinicmanagementsystem.DoctorAPI.DoctorViewMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class AppointmentUploadData implements AppointmentPresenterData, AppointmentViewMessage{

    AppointmentViewMessage appointmentViewMessage;

    public AppointmentUploadData(AppointmentViewMessage appointmentViewMessage){
        this.appointmentViewMessage = appointmentViewMessage;
    }
    @Override
    public void onSuccessUpdate(Activity activity, String doctorEmail, String doctorName,
                                String doctorPhone, String doctorSpecialize, String patientEmail,
                                String patientName, String patientPhone, String patientAddress,
                                String patientAge, String date, String time, String meetingLocation,
                                String feedback, String rate, String token) {
         AppointmentInfo appointmentInfo= new AppointmentInfo(doctorEmail, doctorName,
                 doctorPhone, doctorSpecialize, patientEmail,
                 patientName, patientPhone, patientAddress,
                 patientAge, date, time, meetingLocation,
                 feedback, rate,token);
        FirebaseFirestore.getInstance().collection("AppointmentRecords").document(token).set(appointmentInfo, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            appointmentViewMessage.onUpdateSuccess("Appointment made successfully");
                        }
                        else{
                            appointmentViewMessage.onUpdateFailure("Ohh, Something went wrong, try again!");
                        }
                    }
                });

    }

    @Override
    public void onUpdateFailure(String message) {

        appointmentViewMessage.onUpdateFailure(message);
    }

    @Override
    public void onUpdateSuccess(String message) {

        appointmentViewMessage.onUpdateSuccess(message);
    }
}
