package com.example.clinicmanagementsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.Activities.DoctorAppointmentRecords;
import com.example.clinicmanagementsystem.Activities.DoctorRecords;
import com.example.clinicmanagementsystem.Activities.PatientAppointmentRecords;
import com.example.clinicmanagementsystem.Activities.PatientRecords;
import com.example.clinicmanagementsystem.DoctorAPI.DoctorInfo;
import com.example.clinicmanagementsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DoctorRecordsAdapter extends RecyclerView.Adapter<DoctorRecordsAdapter.ViewHolder> {
    private Context context;
    private String femail;
    private ArrayList<DoctorInfo> doctorInfos = new ArrayList<>();


    private FirebaseFirestore firebaseFirestore;
    public DoctorRecordsAdapter(Context context, ArrayList<DoctorInfo> doctorInfos) {
        this.context = context;
        this.doctorInfos = doctorInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_doctor_records, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorRecordsAdapter.ViewHolder holder, int position) {

        holder.edemail.setText(doctorInfos.get(position).getEmail());
        holder.edname.setText(doctorInfos.get(position).getName());
        holder.edphone.setText(doctorInfos.get(position).getPhoneNo());
        holder.edspecialist.setText(doctorInfos.get(position).getSpecialize());
        String tok= doctorInfos.get(position).getToken();

        //handle delete
        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference record = firebaseFirestore.collection("DoctorRecords").document(tok);
                record.delete().addOnSuccessListener(new OnSuccessListener< Void >() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Doctor Record Deleted !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(v.getContext(), DoctorRecords.class);
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

        //handle view
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femail = doctorInfos.get(position).getEmail();
                System.out.println("1 "+femail);
                Intent intent = new Intent(v.getContext(), DoctorAppointmentRecords.class);
                intent.putExtra("femail", femail);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView edname, edemail, edspecialist, edphone;
        private Button delete,view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edname = itemView.findViewById(R.id.setNameProfiled2);
            edemail = itemView.findViewById(R.id.setEmailProfiled2);
            edspecialist = itemView.findViewById(R.id.setSpecialistProfiled2);
            edphone = itemView.findViewById(R.id.setPhoneProfiled2);

            delete = itemView.findViewById(R.id.deletedProfile);
            view = itemView.findViewById(R.id.viewAppointD);
        }
    }
}
