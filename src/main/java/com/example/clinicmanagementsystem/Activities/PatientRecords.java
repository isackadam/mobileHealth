package com.example.clinicmanagementsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.Adapters.PatientRecordsAdapter;
import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentFetchData;
import com.example.clinicmanagementsystem.PatientAPI.PatientFetchData;
import com.example.clinicmanagementsystem.PatientAPI.PatientInfo;
import com.example.clinicmanagementsystem.PatientAPI.PatientViewFetchData;
import com.example.clinicmanagementsystem.R;

import java.util.ArrayList;

public class PatientRecords extends AppCompatActivity implements PatientViewFetchData {

    private RecyclerView ListDataView;
    private PatientRecordsAdapter mPostsAdapter;
    ArrayList<PatientInfo> patientInfoArrayList = new ArrayList<>();
    private PatientFetchData patientFetchData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ListDataView = findViewById(R.id.userListView);
        patientFetchData = new PatientFetchData(this,this);
        RecyclerViewMethods();
        patientFetchData.onSuccessUpdate(this);


    }
    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        mPostsAdapter = new PatientRecordsAdapter(this,patientInfoArrayList);
        ListDataView.setAdapter(mPostsAdapter);
        ListDataView.invalidate();
    }

    @Override
    public void onUpdateSuccess(PatientInfo message) {
        if(message != null){
            PatientInfo patientInfo = new PatientInfo();
            patientInfo.setEmail(message.getEmail());
            patientInfo.setName(message.getName());
            patientInfo.setAddress(message.getAddress());
            patientInfo.setAge(message.getAge());
            patientInfo.setPhone(message.getPhone());
            patientInfo.setToken(message.getToken());
            patientInfoArrayList.add(patientInfo);
        }
        mPostsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(PatientRecords.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PatientRecords.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
