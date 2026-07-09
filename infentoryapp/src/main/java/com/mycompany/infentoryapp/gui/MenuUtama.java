package com.mycompany.infentoryapp.gui;

import com.mycompany.infentoryapp.model.User;
import java.awt.*;
import javax.swing.*;

public class MenuUtama extends JFrame {

    private User loggedUser;

    public MenuUtama(User user) {
        this.loggedUser = user;
        setTitle("Menu Utama - Aplikasi Inventory");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblWelcome = new JLabel("Selamat datang, " + loggedUser.getUsername() + " (" + loggedUser.getLevel() + ")");
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(lblWelcome);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnKategori = new JButton("Data Kategori");
        JButton btnBarang = new JButton("Data Barang");
        JButton btnLaporan = new JButton("Laporan Stok (Opsional)");
        JButton btnLogout = new JButton("Logout");

        for (JButton b : new JButton[]{btnKategori, btnBarang, btnLaporan, btnLogout}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(250, 35));
            panel.add(b);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        btnKategori.addActionListener(e -> new FormKategori().setVisible(true));
        btnBarang.addActionListener(e -> new FormBarang().setVisible(true));
        btnLaporan.addActionListener(e -> new FormLaporan().setVisible(true));
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        add(panel);
    }
}
