package com.example.clinicmanagementsystem.AppointmentAPI;

public class AppointmentInfo {
    private String doctorEmail, doctorName,
            doctorPhone,doctorSpecialize, patientEmail,
            patientName, patientPhone, patientAddress,patientAge,date,
            time, meetingLocation, feedback, rate,token;

    public AppointmentInfo(String doctorEmail, String doctorName,
                           String doctorPhone, String doctorSpecialize,
                           String patientEmail, String patientName,
                           String patientPhone, String patientAddress,
                           String patientAge, String date, String time,
                           String meetingLocation, String feedback,
                           String rate, String token) {
        this.doctorEmail = doctorEmail;
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.doctorSpecialize = doctorSpecialize;
        this.patientEmail = patientEmail;
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.patientAddress = patientAddress;
        this.patientAge = patientAge;
        this.date = date;
        this.time = time;
        this.meetingLocation = meetingLocation;
        this.feedback = feedback;
        this.rate = rate;
        this.token = token;
    }

    public AppointmentInfo() {

    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorSpecialize() {
        return doctorSpecialize;
    }

    public void setDoctorSpecialize(String doctorSpecialize) {
        this.doctorSpecialize = doctorSpecialize;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
