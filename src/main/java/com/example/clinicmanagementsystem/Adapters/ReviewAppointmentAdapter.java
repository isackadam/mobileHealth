package com.example.clinicmanagementsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinicmanagementsystem.AppointmentAPI.AppointmentInfo;
import com.example.clinicmanagementsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class ReviewAppointmentAdapter extends RecyclerView.Adapter<ReviewAppointmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AppointmentInfo> appintmentList = new ArrayList<>();


    private FirebaseFirestore firebaseFirestore;
    public ReviewAppointmentAdapter(Context context, ArrayList<AppointmentInfo> appintmentList) {
        this.context = context;
        this.appintmentList = appintmentList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointment_review, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAppointmentAdapter.ViewHolder holder, int position) {

        holder.edSpecialist.setText(appintmentList.get(position).getDoctorSpecialize());

        holder.edTime.setText(appintmentList.get(position).getTime());
        holder.edDate.setText(appintmentList.get(position).getDate());
        holder.edlocation.setText(appintmentList.get(position).getMeetingLocation());

        holder.edpEmail.setText(appintmentList.get(position).getPatientEmail());
        holder.edpName.setText(appintmentList.get(position).getPatientName());
        holder.edpPhone.setText(appintmentList.get(position).getPatientPhone());
        holder.edpAddress.setText(appintmentList.get(position).getPatientAddress());
        holder.edpAge.setText(appintmentList.get(position).getPatientAge());

        holder.rates.setText(appintmentList.get(position).getRate());
        holder.edfeedback.setText(appintmentList.get(position).getFeedback());

        if(!appintmentList.get(position).getDoctorEmail().equals("")){
            holder.edstatus.setText("Accepted");
            holder.eddName.setText(appintmentList.get(position).getDoctorName());
            holder.eddEmail.setText(appintmentList.get(position).getDoctorEmail());
            holder.eddPhone.setText(appintmentList.get(position).getDoctorPhone());
        }
        String tok= appintmentList.get(position).getToken();
        holder.bupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference record = firebaseFirestore.collection("AppointmentRecords").document(tok);
                record.update("feedback", holder.edfeedback.getText().toString(), "rate", holder.rate)
                        .addOnSuccessListener(new OnSuccessListener< Void >() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onSuccess(Void aVoid) {
                                holder.rates.setText(holder.rate);
                                Toast.makeText(v.getContext(), "Appointment has been updated", Toast.LENGTH_SHORT).show();

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
                edlocation, edstatus,edpName, edpEmail, edpPhone, edpAddress, edpAge, rates;
        private EditText edfeedback;
        private String rate="";
        private Button bupdate;
        private MaterialSpinner edspinnerRate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edSpecialist = itemView.findViewById(R.id.viewDSpecialist2);
            eddName = itemView.findViewById(R.id.viewDName2);
            eddEmail = itemView.findViewById(R.id.viewDEmail2);
            eddPhone = itemView.findViewById(R.id.viewDPhone2);

            edDate = itemView.findViewById(R.id.viewDate2);
            edTime = itemView.findViewById(R.id.viewTime2);
            edlocation = itemView.findViewById(R.id.viewLocation2);

            edpName = itemView.findViewById(R.id.viewPName2);
            edpEmail = itemView.findViewById(R.id.viewPEmail2);
            edstatus = itemView.findViewById(R.id.viewStatus2);
            edpPhone = itemView.findViewById(R.id.viewPatientPhone2);
            edpAddress = itemView.findViewById(R.id.viewPAddress2);
            edpAge = itemView.findViewById(R.id.viewAge2);

            edfeedback= itemView.findViewById(R.id.viewfeedback);
            rates = itemView.findViewById(R.id.viewRates);
            edspinnerRate = itemView.findViewById(R.id.viewRate);
            bupdate= itemView.findViewById(R.id.viewUpdate);

            edspinnerRate.setItems("1", "2", "3", "4", "5");
            edspinnerRate.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    Snackbar.make(view, item+ " Selected", Snackbar.LENGTH_LONG).show();
                    rate = item;
                }
            });
        }
    }
}
