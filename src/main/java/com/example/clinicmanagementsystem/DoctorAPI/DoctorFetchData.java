package com.example.clinicmanagementsystem.DoctorAPI;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DoctorFetchData implements DoctorPresenterFetchData{

    private Context context;
    private DoctorViewFetchData doctorViewFetchData;

    public DoctorFetchData(Context context, DoctorViewFetchData doctorViewFetchData){
        this.context =context;
        this.doctorViewFetchData = doctorViewFetchData;
    }

    @Override
    public void onSuccessUpdate(Activity activity) {
        FirebaseFirestore.getInstance().collection("DoctorRecords").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(int i=0; i<task.getResult().size();i++){
                        String name = task.getResult().getDocuments().get(i).getString("name").trim();
                        String email= task.getResult().getDocuments().get(i).getString("email").trim();
                        String phoneNo = task.getResult().getDocuments().get(i).getString("phoneNo").trim();
                        String specialize = task.getResult().getDocuments().get(i).getString("specialize").trim();
                        String password = task.getResult().getDocuments().get(i).getString("password").trim();
                        String token = task.getResult().getDocuments().get(i).getString("token").trim();

                        DoctorInfo doctorInfo= new DoctorInfo(name, email, phoneNo, specialize,password,token);
                        doctorViewFetchData.onUpdateSuccess(doctorInfo);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                doctorViewFetchData.onUpdateFailure(e.getMessage().toString());
            }
        });
    }
}
