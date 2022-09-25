package com.example.clinicmanagementsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentInfo;
import com.example.clinicmanagementsystem.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ScheduledAppointmentAdapter extends RecyclerView.Adapter<ScheduledAppointmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AppointmentInfo> appintmentList = new ArrayList<>();


    private FirebaseFirestore firebaseFirestore;
    public ScheduledAppointmentAdapter(Context context, ArrayList<AppointmentInfo> appintmentList) {
        this.context = context;
        this.appintmentList = appintmentList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointment_schedule, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledAppointmentAdapter.ViewHolder holder, int position) {

        holder.edSpecialist.setText(appintmentList.get(position).getDoctorSpecialize());

        holder.edTime.setText(appintmentList.get(position).getTime());
        holder.edDate.setText(appintmentList.get(position).getDate());
        holder.edlocation.setText(appintmentList.get(position).getMeetingLocation());

        holder.edpEmail.setText(appintmentList.get(position).getPatientEmail());
        holder.edpName.setText(appintmentList.get(position).getPatientName());
        holder.edpPhone.setText(appintmentList.get(position).getPatientPhone());
        holder.edpAddress.setText(appintmentList.get(position).getPatientAddress());
        holder.edpAge.setText(appintmentList.get(position).getPatientAge());

        if(!appintmentList.get(position).getDoctorEmail().equals("")){
            holder.edstatus.setText("Accepted");
            holder.eddName.setText(appintmentList.get(position).getDoctorName());
            holder.eddEmail.setText(appintmentList.get(position).getDoctorEmail());
            holder.eddPhone.setText(appintmentList.get(position).getDoctorPhone());
            holder.rates.setText(appintmentList.get(position).getRate());
            holder.edfeedback.setText(appintmentList.get(position).getFeedback());
        }

    }

    @Override
    public int getItemCount() {
        return appintmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView edSpecialist, eddName, eddEmail, eddPhone, edTime, edDate,
                edlocation, edstatus,edpName, edpEmail, edpPhone, edpAddress, edpAge, rates;
        private TextView edfeedback;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edSpecialist = itemView.findViewById(R.id.viewDSpecialist3);
            eddName = itemView.findViewById(R.id.viewDName3);
            eddEmail = itemView.findViewById(R.id.viewDEmail3);
            eddPhone = itemView.findViewById(R.id.viewDPhone3);

            edDate = itemView.findViewById(R.id.viewDate3);
            edTime = itemView.findViewById(R.id.viewTime3);
            edlocation = itemView.findViewById(R.id.viewLocation3);

            edpName = itemView.findViewById(R.id.viewPName3);
            edpEmail = itemView.findViewById(R.id.viewPEmail3);
            edstatus = itemView.findViewById(R.id.viewStatus3);
            edpPhone = itemView.findViewById(R.id.viewPatientPhone3);
            edpAddress = itemView.findViewById(R.id.viewPAddress3);
            edpAge = itemView.findViewById(R.id.viewAge3);

            edfeedback= itemView.findViewById(R.id.viewfeedback3);
            rates = itemView.findViewById(R.id.viewRates3);

        }
    }
}
