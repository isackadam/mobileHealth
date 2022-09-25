package com.example.clinicmanagementsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentInfo;
import com.example.clinicmanagementsystem.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class RequestedAppointmentAdapter extends RecyclerView.Adapter<RequestedAppointmentAdapter.ViewHolder> {
    private Context context;
    private String fname, femail, fspecialist, fphone;
    private ArrayList<AppointmentInfo> appintmentList = new ArrayList<>();


    private FirebaseFirestore firebaseFirestore;
    public RequestedAppointmentAdapter(Context context, ArrayList<AppointmentInfo> appintmentList,
                                       String fname, String femail, String fspecialist,String fphone) {
        this.context = context;
        this.appintmentList = appintmentList;
        this.femail = femail;
        this.fname = fname;
        this.fphone = fphone;
        this.fspecialist= fspecialist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointment_requested, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedAppointmentAdapter.ViewHolder holder, int position) {
        holder.edSpecialist.setText(fspecialist);
        holder.edTime.setText(appintmentList.get(position).getTime());
        holder.edDate.setText(appintmentList.get(position).getDate());
        holder.edlocation.setText(appintmentList.get(position).getMeetingLocation());

        holder.edpEmail.setText(appintmentList.get(position).getPatientEmail());
        holder.edpName.setText(appintmentList.get(position).getPatientName());
        holder.edpPhone.setText(appintmentList.get(position).getPatientPhone());
        holder.edpAddress.setText(appintmentList.get(position).getPatientAddress());
        holder.edpAge.setText(appintmentList.get(position).getPatientAge());

        String tok= appintmentList.get(position).getToken();

        holder.editRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String status= "Accepted";
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference record = firebaseFirestore.collection("AppointmentRecords").document(tok);
                record.update("doctorName", fname, "doctorEmail", femail,
                        "doctorPhone",fphone)
                        .addOnSuccessListener(new OnSuccessListener< Void >() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onSuccess(Void aVoid) {
                                holder.edstatus.setText(status);
                                holder.eddEmail.setText(femail);
                                holder.eddPhone.setText(fphone);
                                holder.eddName.setText(fname);
                                holder.editRequest.setText("Accepted");
                                holder.editRequest.setBackgroundColor(R.color.Green);
                                Toast.makeText(v.getContext(), "Appointment has been accepted", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(v.getContext(), "Something went wrong, check the internet connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return appintmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView edSpecialist, eddName, eddEmail, eddPhone, edTime, edDate,
                edlocation, edstatus,edpName, edpEmail, edpPhone, edpAddress, edpAge;
        private Button editRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edSpecialist = itemView.findViewById(R.id.viewDSpecialist);
            eddName = itemView.findViewById(R.id.viewDName);
            eddEmail = itemView.findViewById(R.id.viewDEmail);
            eddPhone = itemView.findViewById(R.id.viewDPhone);

            edDate = itemView.findViewById(R.id.viewDate);
            edTime = itemView.findViewById(R.id.viewTime);
            edlocation = itemView.findViewById(R.id.viewLocation);

            edpName = itemView.findViewById(R.id.viewPName);
            edpEmail = itemView.findViewById(R.id.viewPEmail);
            edstatus = itemView.findViewById(R.id.viewStatus);
            edpPhone = itemView.findViewById(R.id.viewPatientPhone);
            edpAddress = itemView.findViewById(R.id.viewPAddress);
            edpAge = itemView.findViewById(R.id.viewAge);

            editRequest= itemView.findViewById(R.id.EditStatus);
        }
    }
}
