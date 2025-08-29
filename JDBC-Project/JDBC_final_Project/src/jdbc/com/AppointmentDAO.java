package jdbc.com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public void bookAppointment(int patientId, int doctorId, String dateStr, String desc) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setDate(3, java.sql.Date.valueOf(dateStr)); // expects YYYY-MM-DD
            ps.setString(4, desc);
            ps.executeUpdate();
            System.out.println("✅ Appointment booked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View appointment history for a patient
    public List<String> viewPatientHistory(int patientId) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT a.id, a.appointment_date, a.description, d.name, d.specialization " +
                     "FROM appointments a JOIN doctors d ON a.doctor_id = d.id " +
                     "WHERE a.patient_id = ? ORDER BY a.appointment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date dt = rs.getDate(2);
                String desc = rs.getString(3);
                String docName = rs.getString(4);
                String spec = rs.getString(5);
                history.add("Appointment ID: " + id + ", Date: " + dt + ", Doctor: " + docName + " (" + spec + "), Notes: " + desc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    // Optional: list all appointments (for admin view)
    public List<String> listAllAppointments() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT a.id, p.name AS patient, d.name AS doctor, a.appointment_date, a.description " +
                     "FROM appointments a JOIN patients p ON a.patient_id = p.id " +
                     "JOIN doctors d ON a.doctor_id = d.id ORDER BY a.appointment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add("ID: " + rs.getInt(1) + " | Patient: " + rs.getString("2") + " | Doctor: " + rs.getString("3")
                         + " | Date: " + rs.getDate("appointment_date") + " | " + rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

