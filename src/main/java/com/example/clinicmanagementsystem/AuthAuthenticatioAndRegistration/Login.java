package com.example.clinicmanagementsystem.AuthAuthenticatioAndRegistration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.clinicmanagementsystem.Activities.AdminPanel;
import com.example.clinicmanagementsystem.Activities.DoctorPanel;
import com.example.clinicmanagementsystem.Activities.PatientPanel;
import com.example.clinicmanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends Activity {

    ProgressDialog mProgressDialog;
    Button btnLogin;
    EditText edtEmail, edtPassword;
    FirebaseFirestore firebaseFirestore;
    boolean CheckUser = false;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.EditTxtAdminEmailLogin);
        edtPassword = findViewById(R.id.EditxtAdminPasswordLogin);
        btnLogin = findViewById(R.id.BtnLogin);
        mProgressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog.setMessage("Please wait, Logging in..");
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginDetails();
            }
        });
    }

    private void AdminLogin(String email, String password) {
        //we take the email and password to check if the usr is admin or not
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Success Login as Admin", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, AdminPanel.class);
                    startActivity(intent);
                    finish();
                }else {
                    DoctorLogin(email,password);
                }
            }
        });
    }

    public void DoctorLogin(String email, String password) {

        FirebaseAuth.getInstance().signOut();
        firebaseFirestore.collection("DoctorRecords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (int i=0; i<task.getResult().getDocuments().size(); i++)
                            {
                                // fetch data
                                String fname = task.getResult().getDocuments().get(i).getString("name").trim();
                                String femail= task.getResult().getDocuments().get(i).getString("email").trim();
                                String fphoneNo = task.getResult().getDocuments().get(i).getString("phoneNo").trim();
                                String fspecialize = task.getResult().getDocuments().get(i).getString("specialize").trim();
                                String fpassword = task.getResult().getDocuments().get(i).getString("password").trim();
                                String ftoken = task.getResult().getDocuments().get(i).getString("token").trim();

                                if (femail.equals(email) && fpassword.equals(password)){
                                    Toast.makeText(Login.this, "Welcome back, "+ fname,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, DoctorPanel.class);
                                    intent.putExtra("fname", fname);
                                    intent.putExtra("femail", femail);
                                    intent.putExtra("fspecialize",fspecialize);
                                    intent.putExtra("fphoneNo", fphoneNo);
                                    intent.putExtra("ftoken", ftoken);
                                    startActivity(intent);
                                    finish();
                                    CheckUser = true;
                                }
                            }
                            if (CheckUser == false){
                                PatientLogin(email, password);
                            }
                        }else {
                            Toast.makeText(Login.this, "Error, Check Internet connection!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public void PatientLogin(String email, String password) {

        FirebaseAuth.getInstance().signOut();
        firebaseFirestore.collection("PatientRecords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (int i=0; i<task.getResult().getDocuments().size(); i++)
                            {
                                // fetch data
                                String fname = task.getResult().getDocuments().get(i).getString("name").trim();
                                String femail = task.getResult().getDocuments().get(i).getString("email").trim();
                                String fpassword = task.getResult().getDocuments().get(i).getString("password").trim();
                                String fage = task.getResult().getDocuments().get(i).getString("age").trim();
                                String faddress = task.getResult().getDocuments().get(i).getString("address").trim();
                                String fphone = task.getResult().getDocuments().get(i).getString("phone").trim();
                                String ftoken = task.getResult().getDocuments().get(i).getString("token").trim();

                                if (femail.equals(email) && fpassword.equals(password)){
                                    Toast.makeText(Login.this, "Welcome back, "+ fname,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, PatientPanel.class);
                                    intent.putExtra("fname", fname);
                                    intent.putExtra("femail", femail);
                                    intent.putExtra("faddress",faddress);
                                    intent.putExtra("fage", fage);
                                    intent.putExtra("fphone", fphone);
                                    intent.putExtra("ftoken", ftoken);
                                    startActivity(intent);
                                    finish();
                                    CheckUser = true;
                                }
                            }
                            if (CheckUser == false){
                                Toast.makeText(Login.this, "Wrong Email or Password !",Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(Login.this, "Error, Check Internet connection",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    private void checkLoginDetails() {
        if(!TextUtils.isEmpty(edtEmail.getText().toString()) && !TextUtils.isEmpty(edtPassword.getText().toString())){
            Object login = new Object();
            login = login;
            initLogin(edtEmail.getText().toString(), edtPassword.getText().toString());
            AdminLogin(edtEmail.getText().toString(), edtPassword.getText().toString());
        }else{
            if(TextUtils.isEmpty(edtEmail.getText().toString())){
                edtEmail.setError("Please enter a valid email!");
                return;
            }if(TextUtils.isEmpty(edtPassword.getText().toString())){
                edtPassword.setError("Please enter password!");
            }
        }
    }

    private void initLogin(String toString, String toString1) {
    }

    public void onSignupPatient(View view) {
        Intent intent = new Intent(Login.this, PatientRegistration.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void showDoctor(View view) {
        Intent intent = new Intent(Login.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void showAdmin(View view) {
        Intent intent = new Intent(Login.this, DoctorRegistration.class);



        
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

//    public void showDoctor(View view)
//    {
//        String button_text;
//        button_text =((Button) view).getText().toString();
//        if(button_text.equals("Administration"))
//        {
//            Intent intent = new Intent(this, AdminPanel.class);
//            startActivity(intent);
//        }
//        else if (button_text.equals("Doctor"))
//        {
//            Intent intent = new Intent(this, DoctorRegistration.class);
//            startActivity(intent);
//        }
//    }
}
