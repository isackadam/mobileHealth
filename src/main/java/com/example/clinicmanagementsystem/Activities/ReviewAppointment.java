package com.example.clinicmanagementsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.Adapters.RequestedAppointmentAdapter;
import com.example.clinicmanagementsystem.Adapters.ReviewAppointmentAdapter;
import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentFetchData;
import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentInfo;
import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentViewFetchData;
import com.example.clinicmanagementsystem.R;

import java.util.ArrayList;

public class ReviewAppointment extends AppCompatActivity implements AppointmentViewFetchData {

    private String fname, femail, faddress, fphone, fage, ftoken;
    private RecyclerView ListDataView;
    private ReviewAppointmentAdapter mPostsAdapter;
    ArrayList<AppointmentInfo> appointmentInfoArrayList = new ArrayList<>();
    private AppointmentFetchData appointmentFetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        // fetching the data from the login page
        fname = getIntent().getStringExtra("fname");
        femail = getIntent().getStringExtra("femail");
        faddress = getIntent().getStringExtra("faddress");
        fage= getIntent().getStringExtra("fage");
        fphone = getIntent().getStringExtra("fphone");
        ftoken = getIntent().getStringExtra("ftoken");

        ListDataView = findViewById(R.id.appointmentListView);

        appointmentFetchData = new AppointmentFetchData(this,this);
        RecyclerViewMethods();
        appointmentFetchData.onSuccessUpdate(this);


    }
    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        mPostsAdapter = new ReviewAppointmentAdapter(this,appointmentInfoArrayList);
        ListDataView.setAdapter(mPostsAdapter);
        ListDataView.invalidate();
    }

    @Override
    public void onUpdateSuccess(AppointmentInfo message) {
        if(message != null){
            if(message.getPatientEmail().equals(femail)){
                AppointmentInfo appointmentInfo = new AppointmentInfo();
                appointmentInfo.setDoctorEmail(message.getDoctorEmail());
                appointmentInfo.setDoctorName(message.getDoctorName());
                appointmentInfo.setDoctorSpecialize(message.getDoctorSpecialize());
                appointmentInfo.setDoctorPhone(message.getDoctorPhone());

                appointmentInfo.setDate(message.getDate());
                appointmentInfo.setMeetingLocation(message.getMeetingLocation());
                appointmentInfo.setTime(message.getTime());

                appointmentInfo.setPatientEmail(message.getPatientEmail());
                appointmentInfo.setPatientAddress(message.getPatientAddress());
                appointmentInfo.setPatientName(message.getPatientName());
                appointmentInfo.setPatientPhone(message.getPatientPhone());
                appointmentInfo.setPatientAge(message.getPatientAge());

                appointmentInfo.setRate(message.getRate());
                appointmentInfo.setFeedback(message.getFeedback());
                appointmentInfo.setToken(message.getToken());
                appointmentInfoArrayList.add(appointmentInfo);
            }
        }
        mPostsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(ReviewAppointment.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ReviewAppointment.this, PatientPanel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fname", fname);
        intent.putExtra("femail", femail);
        intent.putExtra("faddress",faddress);
        intent.putExtra("fage", fage);
        intent.putExtra("fphone", fphone);
        intent.putExtra("ftoken", ftoken);
        startActivity(intent);
        finish();
    }
}
