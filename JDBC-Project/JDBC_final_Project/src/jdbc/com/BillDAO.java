package jdbc.com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // Simple generate bill: inserts amount with current date
    public void generateBill(int patientId, double amount) {
        String sql = "INSERT INTO bills (patient_id, amount, billing_date) VALUES (?, ?, CURDATE())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.setDouble(2, amount);
            ps.executeUpdate();
            System.out.println("✅ Bill generated for patient ID " + patientId + " amount ₹" + amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View bills for a patient
    public List<String> viewBillsForPatient(int patientId) {
        List<String> bills = new ArrayList<>();
        String sql = "SELECT id, amount, billing_date FROM bills WHERE patient_id = ? ORDER BY billing_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bills.add("Bill ID: " + rs.getInt(1) + " | Amount: ₹" + rs.getDouble(2) + " | Date: " + rs.getDate(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    // Optional helper: compute total due for patient
    public double getTotalBilledAmount(int patientId) {
        String sql = "SELECT COALESCE(SUM(amount),0) FROM bills WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
