package com.kodnest.database.employee;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Delete extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;

    // 🔹 REQUIRED for BACK navigation
    private JFrame parentFrame;

    // ❌ Do NOT run directly
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Delete(null).setVisible(true);
        });
    }

    // 🔹 CONSTRUCTOR WITH PARENT FRAME
    public Delete(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("Delete Employee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 102, 51));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDeleteEmployee = new JLabel("DELETE EMPLOYEE");
        lblDeleteEmployee.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDeleteEmployee.setBounds(140, 25, 200, 25);
        contentPane.add(lblDeleteEmployee);

        JLabel lblNewLabel = new JLabel("Enter ID:");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel.setBounds(110, 80, 80, 20);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(228, 80, 100, 22);
        contentPane.add(textField);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setOpaque(true);
        btnDelete.setBorderPainted(false);
        btnDelete.setBounds(150, 140, 150, 30);
        contentPane.add(btnDelete);

        JButton btnBack = new JButton("BACK");
        btnBack.setBounds(342, 233, 84, 20);
        contentPane.add(btnBack);

        // 🔥 BACK BUTTON LOGIC (ONLY THIS)
        btnBack.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        // 🔹 DELETE BUTTON LOGIC (UNCHANGED)
        btnDelete.addActionListener((ActionEvent e) -> deleteEmployee());
    }

    // 🔹 DELETE METHOD (UNCHANGED)
    private void deleteEmployee() {

        String URL = "jdbc:mysql://localhost:3306/employeedb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String USER = "root";
        String PASSWORD = "Rakesh143#";

        if (textField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter Employee ID",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(textField.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete employee ID " + id + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement ps =
                    con.prepareStatement("DELETE FROM employee WHERE id = ?");
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                        "Employee deleted successfully");
                textField.setText("");
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
                    "ID must be a number",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Delete failed",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
