package jdbc.com;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ReportExporter {

    // Export patient appointment history to CSV
    public void exportPatientHistoryToCSV(int patientId, String filePath) {
        String sql = "SELECT a.id, a.appointment_date, a.description, d.name AS doctor, d.specialization " +
                     "FROM appointments a JOIN doctors d ON a.doctor_id = d.id " +
                     "WHERE a.patient_id = ? ORDER BY a.appointment_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             FileWriter fw = new FileWriter(filePath)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            fw.append("AppointmentID,Date,Doctor,Specialization,Description\\n");
            boolean hasRow = false;
            while (rs.next()) {
                hasRow = true;
                int id = rs.getInt("id");
                Date d = rs.getDate("appointment_date");
                String doctor = rs.getString("doctor");
                String spec = rs.getString("specialization");
                String desc = rs.getString("description");
                if (desc == null) desc = "";
                // Escape double quotes by doubling them
                desc = desc.replace("\"", "\"\"");
                // Wrap description in quotes to preserve commas/newlines
                fw.append(id + "," + d + ",\"" + doctor + "\"," + "\"" + spec + "\"," + "\"" + desc + "\"\\n");
            }
            if (hasRow) {
                System.out.println("✅ CSV exported to: " + filePath);
            } else {
                System.out.println("No history found for patient ID: " + patientId);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }//C:\Users\DELL\patient_history.csv

}
