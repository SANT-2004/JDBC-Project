package jdbc.com;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientDAO patientDAO = new PatientDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        BillDAO billDAO = new BillDAO();
        ReportExporter exporter = new ReportExporter();

        while (true) {
            System.out.println("\\n=== HOSPITAL MANAGEMENT ===");
            System.out.println("1. Add Patient");
            System.out.println("2. List Patients");
            System.out.println("3. Add Doctor");
            System.out.println("4. List Doctors");
            System.out.println("5. Book Appointment");
            System.out.println("6. View Patient History");
            System.out.println("7. Generate Bill");
            System.out.println("8. View Bills");
            System.out.println("9. Export Patient History to CSV");
            System.out.println("10. Exit");
            System.out.print("Enter choice: ");
            String input = sc.nextLine().trim();
            int ch = 0;
            try { ch = Integer.parseInt(input); } catch (Exception e) { ch = -1; }

            switch (ch) {
                case 1:
                    System.out.print("Name: ");
                    String pname = sc.nextLine().trim();
                    System.out.print("Age: ");
                    int page = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Gender: ");
                    String pgender = sc.nextLine().trim();
                    System.out.print("Phone: ");
                    String pphone = sc.nextLine().trim();
                    Patient p = new Patient(pname, page, pgender, pphone);
                    patientDAO.addPatient(p);
                    break;

                case 2:
                    List<Patient> patients = patientDAO.getAllPatients();
                    System.out.println("\\n--- Patients ---");
                    patients.forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Doctor Name: ");
                    String dname = sc.nextLine().trim();
                    System.out.print("Specialization: ");
                    String spec = sc.nextLine().trim();
                    System.out.print("Phone: ");
                    String dphone = sc.nextLine().trim();
                    Doctor doc = new Doctor(dname, spec, dphone);
                    doctorDAO.addDoctor(doc);
                    break;

                case 4:
                    List<Doctor> doctors = doctorDAO.getAllDoctors();
                    System.out.println("\\n--- Doctors ---");
                    doctors.forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("Patient ID: ");
                    int pid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Doctor ID: ");
                    int did = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = sc.nextLine().trim();
                    System.out.print("Description: ");
                    String desc = sc.nextLine().trim();
                    appointmentDAO.bookAppointment(pid, did, date, desc);
                    break;

                case 6:
                    System.out.print("Patient ID: ");
                    int histPid = Integer.parseInt(sc.nextLine().trim());
                    List<String> hist = appointmentDAO.viewPatientHistory(histPid);
                    System.out.println("\\n--- Appointment History ---");
                    if (hist.isEmpty()) System.out.println("No records found.");
                    else hist.forEach(System.out::println);
                    break;

                case 7:
                    System.out.print("Patient ID: ");
                    int billPid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Amount (e.g., 1500.00): ");
                    double amt = Double.parseDouble(sc.nextLine().trim());
                    billDAO.generateBill(billPid, amt);
                    break;

                case 8:
                    System.out.print("Patient ID: ");
                    int viewPid = Integer.parseInt(sc.nextLine().trim());
                    List<String> bills = billDAO.viewBillsForPatient(viewPid);
                    System.out.println("\\n--- Bills ---");
                    if (bills.isEmpty()) System.out.println("No bills found.");
                    else bills.forEach(System.out::println);
                    break;

                case 9:
                    System.out.print("Patient ID: ");
                    int csvPid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("CSV file path (full path): ");
                    String path = sc.nextLine().trim();
                    exporter.exportPatientHistoryToCSV(csvPid, path);
                    break;

                case 10:
                    System.out.println("Exiting... Bye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice — try again.");
            }
        }
    }
}
