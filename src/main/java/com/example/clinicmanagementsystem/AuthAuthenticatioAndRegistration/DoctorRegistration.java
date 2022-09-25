package com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.clinicmanagementsystem.Activities.AdminPanel;
import com.example.clinicmanagementsystem.DoctorAPI.DoctorUploadData;
import com.example.clinicmanagementsystem.DoctorAPI.DoctorViewMessage;
import com.example.clinicmanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DoctorRegistration extends Activity implements DoctorViewMessage {

    private EditText edName, edEmail, edPassword, edPhone;
    private MaterialSpinner spinnerId;

    private String specialist="";

    // Array list to store the emails
    public ArrayList<String> emailList = new ArrayList<>();

    DoctorUploadData doctorUploadData;

    @Override
    protected void onStart() {
        super.onStart();
        checkEmail();
    }

    // We declare the interface field
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Links the SignUpCustomer XML File
        setContentView(R.layout.activity_doctor_register);
        edName = findViewById(R.id.EditTxtDName);
        edEmail = findViewById(R.id.EditTxtDEmail);
        edPassword = findViewById(R.id.EditTxtDPassword);
        edPhone = findViewById(R.id.EditTxtDPhoneNo);
        spinnerId= findViewById(R.id.spinnerID);
        spinnerId.setItems("Mama na mtoto", "Huduma kwa wazee", "Magonjwa ya kuambukiza, mlipuko", "Ajali", "Huduma ya dharura", "Ushauri Nasaha", "Huduma nyingine");
        spinnerId.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item+ " Selected", Snackbar.LENGTH_LONG).show();
                specialist = item;
            }
        });

        doctorUploadData = new DoctorUploadData(this);
    }

    private void checkEmail(){
        FirebaseFirestore.getInstance().collection("DoctorRecords")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(int i = 0; i < task.getResult().size(); i++){

                        String email = task.getResult().getDocuments().get(i).getString("email");
                        emailList.add(email);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DoctorRegistration.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void onRegisterBtnClick(View view) {
        DataValidation();
    }

    private void initRecord( String name, String email, String phoneNo, String specialize, String password, String token) {
        doctorUploadData.onSuccessUpdate(DoctorRegistration.this, name,
                email,  phoneNo,  specialize, password, token);
    }


    private void DataValidation() {
        String name = edName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(phone)
                && !specialist.equals("")){
            //check email if has been used before
            if(emailList.contains(email)){
                Toast.makeText(DoctorRegistration.this, "The Email has been used before, Please change it", Toast.LENGTH_LONG).show();
            }else{
                //send record to be saved
                initRecord(name, email, phone, specialist, password, timestamp.toString());
            }
        }else{
            if(TextUtils.isEmpty(name)){
                edName.setError("Name is required");
                return;
            }if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edEmail.setError("Enter a valid Email address");
                return;
            }if (TextUtils.isEmpty(password)){
                edPassword.setError("Password is required");
                return;
            }if (TextUtils.isEmpty(phone)){
                edPhone.setError("Phone Number is required");
                return;
            }if (TextUtils.isEmpty(specialist)){
                spinnerId.setError("Specialist is required");
                return;
            }

        }
    }


    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(DoctorRegistration.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateSuccess(String message) {

        Toast.makeText(DoctorRegistration.this, "Doctor added successfully!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(DoctorRegistration.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoctorRegistration.this, AdminPanel.class);
        startActivity(intent);
        finish();
    }
}
