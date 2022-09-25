package com.example.clinicmanagementsystem.AppointmentAPI;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AppointmentFetchData implements AppointmentPresenterFetchData {
    Context context;
    AppointmentViewFetchData appointmentViewFetchData;

    public AppointmentFetchData(Context context, AppointmentViewFetchData appointmentViewFetchData){
        this.context = context;
        this.appointmentViewFetchData = appointmentViewFetchData;
    }

    @Override
    public void onSuccessUpdate(Activity activity) {
        FirebaseFirestore.getInstance().collection("AppointmentRecords").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(int i=0; i<task.getResult().size();i++){
                        String doctorEmail =task.getResult().getDocuments().get(i).getString("doctorEmail").trim();
                        String doctorName =task.getResult().getDocuments().get(i).getString("doctorName").trim();
                        String doctorPhone =task.getResult().getDocuments().get(i).getString("doctorPhone").trim();
                        String doctorSpecialize =task.getResult().getDocuments().get(i).getString("doctorSpecialize").trim();
                        String patientEmail =task.getResult().getDocuments().get(i).getString("patientEmail").trim();
                        String patientName =task.getResult().getDocuments().get(i).getString("patientName").trim();
                        String patientPhone =task.getResult().getDocuments().get(i).getString("patientPhone").trim();
                        String patientAddress =task.getResult().getDocuments().get(i).getString("patientAddress").trim();
                        String patientAge =task.getResult().getDocuments().get(i).getString("patientAge").trim();
                        String date = task.getResult().getDocuments().get(i).getString("date").trim();
                        String time = task.getResult().getDocuments().get(i).getString("time").trim();
                        String meetingLocation =task.getResult().getDocuments().get(i).getString("meetingLocation").trim();
                        String feedback =task.getResult().getDocuments().get(i).getString("feedback");
                        String rate=task.getResult().getDocuments().get(i).getString("rate");
                        String token=task.getResult().getDocuments().get(i).getString("token").trim();
                        AppointmentInfo appointmentInfo=new AppointmentInfo( doctorEmail,  doctorName,
                                 doctorPhone,  doctorSpecialize,
                                 patientEmail,  patientName,
                                 patientPhone,  patientAddress,
                                 patientAge,  date,  time,
                                 meetingLocation,  feedback,
                                 rate,  token);
                        appointmentViewFetchData.onUpdateSuccess(appointmentInfo);
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                appointmentViewFetchData.onUpdateFailure(e.getMessage().toString());
            }
        });
    }
}
