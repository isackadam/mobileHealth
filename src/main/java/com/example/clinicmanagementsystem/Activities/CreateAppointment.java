package com.example.clinicmanagementsystem.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentUploadData;
import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentViewMessage;
import com.example.clinicmanagementsystem.R;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.sql.Timestamp;
import java.util.Calendar;

public class CreateAppointment extends Activity implements AppointmentViewMessage {

    private TextView edDate;
    private MaterialSpinner edspinnerLocation,  edspinnerSpecialist, edspinnerTime;
    private String specialist, location, date="", time;

    private int difference;
    private String fname, femail, faddress, fphone, fage, ftoken;
    AppointmentUploadData appointmentUploadData;

    // We declare the interface field
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_appointment);
        edDate = findViewById(R.id.EditTxtDate);
        edspinnerTime = findViewById(R.id.spinnerTime);
        edspinnerLocation= findViewById(R.id.spinnerLocation);
        edspinnerSpecialist= findViewById(R.id.spinnerSpecialize);
        // fetching the data from the login page
        fname = getIntent().getStringExtra("fname");
        femail = getIntent().getStringExtra("femail");
        faddress = getIntent().getStringExtra("faddress");
        fage= getIntent().getStringExtra("fage");
        fphone = getIntent().getStringExtra("fphone");
        ftoken = getIntent().getStringExtra("ftoken");

        //get the time

        edspinnerTime.setItems("08:00 am", "09:00 am", "10:00 am", "11:00 am", "2:00 pm", "03:00 pm", "04:00 pm", "05:00 pm");
        edspinnerTime.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item+ " Selected", Snackbar.LENGTH_LONG).show();
                time = item;
            }
        });
        //get the date
        Calendar calendar = Calendar.getInstance();
        final int Cyear = calendar.get(Calendar.YEAR);
        final int Cmonth = calendar.get(Calendar.MONTH);
        final int Cday = calendar.get(Calendar.DAY_OF_MONTH);

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(
                        CreateAppointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        int currentDate = ((Cyear*12*365)+(Cmonth*30)+Cday);
                        int pickDate = ((year*12*365)+(month*30)+day);
                        difference = pickDate - currentDate;
                        month=month+1;
                        date = day+"/"+month+"/"+year;
                        edDate.setText(date);
                    }
                },Cyear,Cmonth,Cday);
                datePickerDialog.show();
            }
        });
        //specialist
        edspinnerSpecialist.setItems("Mama na mtoto", "Huduma kwa wazee", "Magonjwa ya kuambukiza, mlipuko", "Ajali", "Huduma ya dharura", "Ushauri Nasaha", "Huduma nyingine"                                                                 );
        edspinnerSpecialist.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item+ " Selected", Snackbar.LENGTH_LONG).show();
                specialist = item;
            }
        });
        //location
        edspinnerLocation.setItems("At Clinic", "At Home");
        edspinnerLocation.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item+ " Selected", Snackbar.LENGTH_LONG).show();
                location = item;
            }
        });
        appointmentUploadData = new AppointmentUploadData(this);
    }
    private void initRecord( String doctorEmail, String doctorName,
                             String doctorPhone, String doctorSpecialize,
                             String patientEmail, String patientName,
                             String patientPhone, String patientAddress,
                             String patientAge, String date, String time,
                             String meetingLocation, String feedback,
                             String rate, String token) {
        appointmentUploadData.onSuccessUpdate(CreateAppointment.this,  doctorEmail, doctorName,
                doctorPhone,doctorSpecialize, patientEmail,
                patientName, patientPhone, patientAddress,patientAge,date,
                time, meetingLocation, feedback, rate,token);
    }
    private void DataValidation() {
        String doctorEmail = "";
        String doctorName = "";
        String doctorPhone = "";
        String feedback = "No feedback yet";
        String rate = "No rate yet";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(!TextUtils.isEmpty(time)
                && !TextUtils.isEmpty(date)
                && !specialist.isEmpty()
                && !location.isEmpty()&& difference >=0){
            //send record to be saved
            initRecord(doctorEmail,doctorName,doctorPhone,specialist,
                    femail, fname,fphone,faddress,fage,
                    date, time, location, feedback, rate, timestamp.toString());
        }else{
            if (difference <0){
                edDate.setError("The date cannot be older than the current date!");
                Toast.makeText(CreateAppointment.this, "Date cannot be older than the current date!", Toast.LENGTH_SHORT).show();
                return;
            }if(date.isEmpty()){
                edDate.setError("Date is required!");
                return;}
            if(TextUtils.isEmpty(time)){
                edspinnerTime.setError("Time is required!");
                return;
            }
            if (specialist.isEmpty()){
                edspinnerSpecialist.setError("Specialist is required!");
                return;
            }if (location.isEmpty()){
                edspinnerLocation.setError("Location NO is required!");
                return;
            }

        }
    }

    public void onRequestBtnClick(View view) {
        DataValidation();
    }

    @Override
    public void onUpdateFailure(String message) {
        Toast.makeText(CreateAppointment.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateSuccess(String message) {
        Toast.makeText(CreateAppointment.this, "Requested successfully!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(CreateAppointment.this, PatientPanel.class);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreateAppointment.this, PatientPanel.class);
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
