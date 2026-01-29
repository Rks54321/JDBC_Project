package com.kodnest.database.employee;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class insertemployee extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField id;
    private JTextField name;
    private JTextField gender;
    private JTextField salary;

    private JFrame parentFrame;

    private static final String URL =
            "jdbc:mysql://localhost:3306/employeedb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rakesh143#";

    public insertemployee(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("Add Employee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 102, 102));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("ADD EMPLOYEE");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(160, 22, 200, 20);
        contentPane.add(lblTitle);

        JLabel lblId = new JLabel("Enter ID:");
        lblId.setBounds(50, 75, 80, 14);
        contentPane.add(lblId);

        JLabel lblName = new JLabel("Enter Name:");
        lblName.setBounds(50, 100, 80, 14);
        contentPane.add(lblName);

        JLabel lblGender = new JLabel("Enter Gender:");
        lblGender.setBounds(50, 125, 100, 14);
        contentPane.add(lblGender);

        JLabel lblSalary = new JLabel("Enter Salary:");
        lblSalary.setBounds(50, 150, 80, 14);
        contentPane.add(lblSalary);

        id = new JTextField();
        id.setBounds(170, 72, 120, 20);
        contentPane.add(id);

        name = new JTextField();
        name.setBounds(170, 97, 120, 20);
        contentPane.add(name);

        gender = new JTextField();
        gender.setBounds(170, 122, 120, 20);
        contentPane.add(gender);

        salary = new JTextField();
        salary.setBounds(170, 147, 120, 20);
        contentPane.add(salary);

        JButton btnInsert = new JButton("ADD EMPLOYEE");
        btnInsert.setBounds(140, 190, 170, 25);
        btnInsert.setBackground(new Color(0, 153, 51));
        btnInsert.setForeground(Color.WHITE);
        btnInsert.setOpaque(true);
        btnInsert.setBorderPainted(false);
        contentPane.add(btnInsert);

        JButton btnBack = new JButton("BACK");
        btnBack.setBounds(320, 230, 90, 20);
        btnBack.setBackground(new Color(255, 255, 255));
        btnBack.setForeground(Color.BLACK);
        contentPane.add(btnBack);

        // 🔹 INSERT LOGIC
        btnInsert.addActionListener(e -> insertEmployee());

        // 🔹 BACK BUTTON LOGIC
        btnBack.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });
    }

    private void insertEmployee() {
        try {
            int inid = Integer.parseInt(id.getText().trim());
            String inname = name.getText().trim();
            String ingender = gender.getText().trim();
            int insalary = Integer.parseInt(salary.getText().trim());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO employee (id, name, gender, salary) VALUES (?,?,?,?)");

            ps.setInt(1, inid);
            ps.setString(2, inname);
            ps.setString(3, ingender);
            ps.setInt(4, insalary);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Employee added successfully!");

            id.setText("");
            name.setText("");
            gender.setText("");
            salary.setText("");

            ps.close();
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Insert failed",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
