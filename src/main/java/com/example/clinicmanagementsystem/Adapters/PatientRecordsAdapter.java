package com.example.clinicmanagementsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.Activities.AdminPanel;
import com.example.clinicmanagementsystem.Activities.PatientAppointmentRecords;
import com.example.clinicmanagementsystem.Activities.PatientRecords;
import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentInfo;
import com.example.clinicmanagementsystem.PatientAPI.PatientInfo;
import com.example.clinicmanagementsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PatientRecordsAdapter extends RecyclerView.Adapter<PatientRecordsAdapter.ViewHolder> {
    private Context context;
    private String femail;
    private ArrayList<PatientInfo>  patientInfoList = new ArrayList<>();


    private FirebaseFirestore firebaseFirestore;
    public PatientRecordsAdapter(Context context, ArrayList<PatientInfo> patientInfoList) {
        this.context = context;
        this.patientInfoList = patientInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_patient_records, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientRecordsAdapter.ViewHolder holder, int position) {

        holder.edemail.setText(patientInfoList.get(position).getEmail());
        holder.edname.setText(patientInfoList.get(position).getName());
        holder.edphone.setText(patientInfoList.get(position).getPhone());
        holder.edaddress.setText(patientInfoList.get(position).getAddress());
        holder.edage.setText(patientInfoList.get(position).getAge());
        String tok= patientInfoList.get(position).getToken();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference record = firebaseFirestore.collection("PatientRecords").document(tok);
                record.delete().addOnSuccessListener(new OnSuccessListener< Void >() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Patient Record Deleted !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(v.getContext(), PatientRecords.class);
                                v.getContext().startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Something went wrong, check the internet connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femail = patientInfoList.get(position).getEmail();
                System.out.println("1 "+femail);
                Intent intent = new Intent(v.getContext(), PatientAppointmentRecords.class);
                intent.putExtra("femail", femail);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return patientInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView edname, edemail, edaddress, edphone, edage;
        private Button delete,view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edname = itemView.findViewById(R.id.setNameProfile2);
            edemail = itemView.findViewById(R.id.setEmailProfile2);
            edaddress = itemView.findViewById(R.id.setAddressProfile2);
            edage = itemView.findViewById(R.id.setAgeProfile2);
            edphone = itemView.findViewById(R.id.setPhoneProfile2);

            delete = itemView.findViewById(R.id.deletePProfile);
            view = itemView.findViewById(R.id.viewAppointP2);
        }
    }
}
