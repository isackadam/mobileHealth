package com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.clinicmanagementsystem.PatientAPI.PatientUploadData;
import com.example.clinicmanagementsystem.PatientAPI.PatientViewMessage;
import com.example.clinicmanagementsystem.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class PatientRegistration extends Activity implements PatientViewMessage {

    private static final Pattern Password_Pattern = Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            // "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");
    private EditText edName, edEmail, edPassword, edPhone, edAge, edAddress;
    private ImageView getlocation;
    // Array list to store the emails
    public ArrayList<String> emailList = new ArrayList<>();
    private String userLocation="";

    FusedLocationProviderClient fusedLocationProviderClient;
    PatientUploadData patientUploadData;

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
        setContentView(R.layout.activity_patient_register);
        edName = findViewById(R.id.EditTxtPName);
        edEmail = findViewById(R.id.EditTxtPEmail);
        edPassword = findViewById(R.id.EditTxtPPassword);
        edPhone = findViewById(R.id.EditTxtPPhoneNo);
        edAddress = findViewById(R.id.EditTxtPAddress);
        edAge = findViewById(R.id.EditTxtPAge);
        getlocation = findViewById(R.id.edlocationImage);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(PatientRegistration.this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {

                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if(location != null){
                                Geocoder geocoder= new Geocoder(PatientRegistration.this, Locale.getDefault());
                                try {
                                    List<Address> addresses= geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                                    userLocation = addresses.get(0).getAddressLine(0)+", "+
                                            addresses.get(0).getLocality() + ", "+
                                            addresses.get(0).getCountryName();
                                    System.out.println(userLocation);
                                    edAddress.setText(userLocation);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
                                Toast.makeText(PatientRegistration.this, "Could not found the location",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                    ActivityCompat.requestPermissions(PatientRegistration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        });

        patientUploadData = new PatientUploadData(this);


    }


    private void checkEmail(){
        FirebaseFirestore.getInstance().collection("PatientRecords")
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
                Toast.makeText(PatientRegistration.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initRecord( String name, String email, String password, String age, String address, String phone, String token) {
        patientUploadData.onSuccessUpdate(PatientRegistration.this,  name,  email,  password,  age,  address,  phone,  token);
    }

    private void DataValidation() {
        String name = edName.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String age = edAge.getText().toString().trim();
        String address = edAddress.getText().toString().trim();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !TextUtils.isEmpty(password) && Password_Pattern.matcher(password).matches()
                && !TextUtils.isEmpty(phone)
                && !TextUtils.isEmpty(age)
                && !TextUtils.isEmpty(address)){
            //check email if has been used before
            if(emailList.contains(email)){
                Toast.makeText(PatientRegistration.this, "The Email has been used before, Please change it", Toast.LENGTH_LONG).show();
            }else{
                //send record to be saved
                initRecord(name, email, password, age, address, phone, timestamp.toString());
            }
        }else{
            if(TextUtils.isEmpty(name)){
                edName.setError("Name is required!");
                return;
            }if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edEmail.setError("Enter a valid Email address!");
                return;
            }if (TextUtils.isEmpty(password)){
                edPassword.setError("Password is required!");
                return;
            }
            if (!Password_Pattern.matcher(password).matches()){
                edPassword.setError("Weak Password!, Password must contain at least 4 character and at least 1 special character with no white space allowed");
                return;
            }if (TextUtils.isEmpty(phone)){
                edPhone.setError("Phone NO is required!");
                return;
            }if (TextUtils.isEmpty(age)){
                edAge.setError("Age is required!");
                return;
            }
            if (TextUtils.isEmpty(address)){
                edAddress.setError("Address is required!");
                return;
            }

        }
    }

    public void onRegisterPatientBtnClick(View view) {
        DataValidation();
    }

    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(PatientRegistration.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateSuccess(String message) {
        Toast.makeText(PatientRegistration.this, "Registered successfully!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(PatientRegistration.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PatientRegistration.this, Login.class);
        startActivity(intent);
        finish();
    }
}
