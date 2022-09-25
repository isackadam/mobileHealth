package com.example.clinicmanagementsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.Adapters.DoctorRecordsAdapter;
import com.example.clinicmanagementsystem.Adapters.PatientRecordsAdapter;
import com.example.clinicmanagementsystem.DoctorAPI.DoctorFetchData;
import com.example.clinicmanagementsystem.DoctorAPI.DoctorInfo;
import com.example.clinicmanagementsystem.DoctorAPI.DoctorViewFetchData;
import com.example.clinicmanagementsystem.PatientAPI.PatientFetchData;
import com.example.clinicmanagementsystem.R;

import java.util.ArrayList;

public class DoctorRecords extends AppCompatActivity implements DoctorViewFetchData {

    private RecyclerView ListDataView;
    private DoctorRecordsAdapter mPostsAdapter;
    ArrayList<DoctorInfo> doctorInfos = new ArrayList<>();
    private DoctorFetchData doctorFetchData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ListDataView = findViewById(R.id.userListView);
        doctorFetchData = new DoctorFetchData(this,this);
        RecyclerViewMethods();
        doctorFetchData.onSuccessUpdate(this);


    }
    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        mPostsAdapter = new DoctorRecordsAdapter(this, doctorInfos);
        ListDataView.setAdapter(mPostsAdapter);
        ListDataView.invalidate();
    }

    @Override
    public void onUpdateSuccess(DoctorInfo message) {
        if(message != null){
            DoctorInfo doctorInfo = new DoctorInfo();
            doctorInfo.setEmail(message.getEmail());
            doctorInfo.setName(message.getName());
            doctorInfo.setSpecialize(message.getSpecialize());
            doctorInfo.setPhoneNo(message.getPhoneNo());
            doctorInfo.setToken(message.getToken());
            doctorInfos.add(doctorInfo);
        }
        mPostsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(DoctorRecords.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoctorRecords.this, AdminPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
