package com.kodnest.banking;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Fetchalldata extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;

    private List<Color> rowColors = new ArrayList<>();
    private Random random = new Random();

    // JDBC DETAILS
    private static final String URL =
        "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rakesh143#";

    private JButton btnBack;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Fetchalldata frame = new Fetchalldata();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Fetchalldata() {
        setTitle("FETCH ALL ACCOUNTS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 102, 102));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("FETCH ALL ACCOUNT");
        title.setFont(new Font("Tahoma", Font.BOLD, 16));
        title.setBounds(150, 10, 250, 25);
        contentPane.add(title);

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Balance"}, 0);

        table = new JTable(model);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 8));

        // 🎨 SAME RENDERER STYLE (UNCHANGED)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (row < rowColors.size()) {
                    c.setBackground(rowColors.get(row));
                }

                c.setForeground(Color.WHITE);
                setHorizontalAlignment(CENTER);
                setOpaque(true);

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 60, 400, 210);
        contentPane.add(scrollPane);

        btnBack = new JButton("BACK");
        btnBack.setBackground(new Color(204, 153, 51));
        btnBack.setBounds(366, 283, 110, 20);
        contentPane.add(btnBack);

        // 🔙 BACK BUTTON ONLY (ADDED)
        btnBack.addActionListener(e -> {
            new Banking().setVisible(true);
            dispose();
        });

        SwingUtilities.invokeLater(this::fetchAllAccounts);
    }

    // 🔥 FETCH ALL DATA (UNCHANGED)
    private void fetchAllAccounts() {

        model.setRowCount(0);
        rowColors.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement ps =
                con.prepareStatement("SELECT accno, username, balance FROM ucucubank");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("accno"),
                        rs.getString("username"),
                        rs.getDouble("balance")
                });

                rowColors.add(generateRandomColor());
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to fetch accounts",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 🎨 RANDOM COLOR GENERATOR (UNCHANGED)
    private Color generateRandomColor() {
        return new Color(
                random.nextInt(150),
                random.nextInt(150),
                random.nextInt(150)
        );
    }
}
