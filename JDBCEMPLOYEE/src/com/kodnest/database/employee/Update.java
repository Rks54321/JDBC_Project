package com.kodnest.database.employee;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.sql.*;

public class Update extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idField;
    private JTextField nameField;
    private JTextField genderField;
    private JTextField salaryField;

    // 🔹 REQUIRED for BACK navigation
    private JFrame parentFrame;

    // ❌ Do NOT run directly (kept only for testing)
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Update(null).setVisible(true);
        });
    }

    // 🔹 CONSTRUCTOR WITH PARENT FRAME
    public Update(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("Update Employee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 102, 102));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUpdateEmployee = new JLabel("UPDATE EMPLOYEE");
        lblUpdateEmployee.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUpdateEmployee.setBounds(140, 24, 200, 25);
        contentPane.add(lblUpdateEmployee);

        JLabel lblId = new JLabel("Enter ID:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblId.setBounds(119, 70, 86, 20);
        contentPane.add(lblId);

        idField = new JTextField();
        idField.setBounds(215, 71, 96, 20);
        contentPane.add(idField);

        JLabel lblName = new JLabel("Enter Updated Name:");
        lblName.setBounds(84, 100, 150, 20);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(240, 101, 125, 20);
        contentPane.add(nameField);

        JLabel lblGender = new JLabel("Enter Updated Gender:");
        lblGender.setBounds(84, 130, 150, 20);
        contentPane.add(lblGender);

        genderField = new JTextField();
        genderField.setBounds(240, 131, 125, 20);
        contentPane.add(genderField);

        JLabel lblSalary = new JLabel("Enter Updated Salary:");
        lblSalary.setBounds(84, 160, 150, 20);
        contentPane.add(lblSalary);

        salaryField = new JTextField();
        salaryField.setBounds(240, 161, 125, 20);
        contentPane.add(salaryField);

        JButton btnUpdateEmployee = new JButton("UPDATE EMPLOYEE");
        btnUpdateEmployee.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnUpdateEmployee.setBackground(new Color(0, 153, 51));
        btnUpdateEmployee.setForeground(Color.WHITE);
        btnUpdateEmployee.setOpaque(true);
        btnUpdateEmployee.setBorderPainted(false);
        btnUpdateEmployee.setBounds(111, 204, 200, 25);
        contentPane.add(btnUpdateEmployee);

        JButton btnBack = new JButton("BACK");
        btnBack.setBounds(342, 233, 84, 20);
        contentPane.add(btnBack);

        // 🔥 BACK BUTTON LOGIC (ONLY THIS)
        btnBack.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        // 🔹 UPDATE LOGIC (UNCHANGED)
        btnUpdateEmployee.addActionListener(e -> updateEmployee());
    }

    // 🔹 UPDATE METHOD (UNCHANGED)
    private void updateEmployee() {

        String URL = "jdbc:mysql://localhost:3306/employeedb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String USER = "root";
        String PASSWORD = "Rakesh143#";

        if (idField.getText().trim().isEmpty() ||
            nameField.getText().trim().isEmpty() ||
            genderField.getText().trim().isEmpty() ||
            salaryField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "All fields are required",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idField.getText().trim());
            int salary = Integer.parseInt(salaryField.getText().trim());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE employee SET name=?, gender=?, salary=? WHERE id=?");

            ps.setString(1, nameField.getText().trim());
            ps.setString(2, genderField.getText().trim());
            ps.setInt(3, salary);
            ps.setInt(4, id);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this,
                        "Employee updated successfully");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Employee ID not found",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            ps.close();
            con.close();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID and Salary must be numbers",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Update failed",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
