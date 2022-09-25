package com.example.clinicmanagementsystem.PatientAPI;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PatientFetchData implements PatientPresenterFetchData{

    Context context;
    PatientViewFetchData patientViewFetchData ;

    public PatientFetchData(Context context, PatientViewFetchData patientViewFetchData){
        this.context =context;
        this.patientViewFetchData = patientViewFetchData;
    }
    @Override
    public void onSuccessUpdate(Activity activity) {
        FirebaseFirestore.getInstance().collection("PatientRecords").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(int i=0; i<task.getResult().size();i++){
                        String name = task.getResult().getDocuments().get(i).getString("name").trim();
                        String email = task.getResult().getDocuments().get(i).getString("email").trim();
                        String password = task.getResult().getDocuments().get(i).getString("password").trim();
                        String age = task.getResult().getDocuments().get(i).getString("age").trim();
                        String address = task.getResult().getDocuments().get(i).getString("address").trim();
                        String phone = task.getResult().getDocuments().get(i).getString("phone").trim();
                        String token = task.getResult().getDocuments().get(i).getString("token").trim();

                        PatientInfo patientInfo = new PatientInfo(name, email,password,age,address,phone,token);
                        patientViewFetchData.onUpdateSuccess(patientInfo);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                patientViewFetchData.onUpdateFailure(e.getMessage().toString());
            }
        });
    }
}
