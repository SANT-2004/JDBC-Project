package jdbc.com;

import java.sql.Date;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;
    private String description;

    public Appointment(int id, int patientId, int doctorId, Date appointmentDate, String description) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    public Appointment(int patientId, int doctorId, Date appointmentDate, String description) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public Date getAppointmentDate() { return appointmentDate; }
    public String getDescription() { return description; }

    public void setId(int id) { this.id = id; }
}
