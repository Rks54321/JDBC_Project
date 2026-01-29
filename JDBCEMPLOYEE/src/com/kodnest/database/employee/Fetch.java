package com.kodnest.database.employee;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Fetch extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField id;
    private JTextArea textArea;
    private JButton btnBack;

    // REQUIRED for BACK navigation
    private JFrame parentFrame;

    // Do NOT run directly in real app
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Fetch(null).setVisible(true);
        });
    }

    // constructor with parent frame
    public Fetch(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 102, 102));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("FETCH EMPLOYEE");
        title.setFont(new Font("Tahoma", Font.BOLD, 16));
        title.setBounds(136, 20, 180, 18);
        contentPane.add(title);

        JLabel lblId = new JLabel("Enter ID:");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblId.setBounds(86, 74, 80, 12);
        contentPane.add(lblId);

        id = new JTextField();
        id.setBounds(212, 70, 96, 25);
        contentPane.add(id);

        JButton fetchButton = new JButton("FETCH");
        fetchButton.setBounds(140, 116, 100, 25);
        fetchButton.setBackground(new Color(0, 102, 255));
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        fetchButton.setOpaque(true);
        fetchButton.setBorderPainted(false);
        contentPane.add(fetchButton);

        textArea = new JTextArea();
        textArea.setBounds(68, 146, 274, 79);
        textArea.setEditable(false);
        contentPane.add(textArea);

        btnBack = new JButton("BACK");
        btnBack.setBounds(327, 233, 84, 20);
        contentPane.add(btnBack);

        // BACK BUTTON LOGIC
        btnBack.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            this.dispose();
        });

        // FETCH LOGIC
        fetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String URL =
                    "jdbc:mysql://localhost:3306/employeedb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                String USER = "root";
                String PASSWORD = "Rakesh143#";

                if (id.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(Fetch.this,
                            "Please enter Employee ID",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int searchId = Integer.parseInt(id.getText().trim());

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    PreparedStatement ps =
                            con.prepareStatement("SELECT * FROM employee WHERE id = ?");
                    ps.setInt(1, searchId);

                    ResultSet rs = ps.executeQuery();
                    textArea.setText("");

                    if (rs.next()) {
                        textArea.setText(
                                "ID: " + rs.getInt("id") + "\n" +
                                "Name: " + rs.getString("name") + "\n" +
                                "Gender: " + rs.getString("gender") + "\n" +
                                "Salary: " + rs.getInt("salary")
                        );
                    } else {
                        textArea.setText("No employee found with ID: " + searchId);
                    }

                    rs.close();
                    ps.close();
                    con.close();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Fetch.this,
                            "ID must be a number",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Fetch.this,
                            "Retrieve failed",
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
