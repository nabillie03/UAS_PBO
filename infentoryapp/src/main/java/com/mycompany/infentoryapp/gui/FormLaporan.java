package com.mycompany.infentoryapp.gui;

import com.mycompany.infentoryapp.koneksi.Koneksi;
import java.awt.*;
import java.awt.print.PrinterException;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Fitur OPSIONAL (poin 4 di requirement) supaya nilai bisa naik ke A.
 * Menampilkan rekap jumlah stok barang per kategori, dan bisa dicetak (print).
 */
public class FormLaporan extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FormLaporan() {
        setTitle("Laporan Rekap Stok per Kategori");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadLaporan();
    }

    private void initComponents() {
        model = new DefaultTableModel(new Object[]{"Kategori", "Jumlah Jenis Barang", "Total Stok"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);

        JButton btnCetak = new JButton("Cetak / Print");
        btnCetak.addActionListener(e -> cetak());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnCetak);

        setLayout(new BorderLayout());
        add(new JLabel("  Laporan Rekap Stok Barang per Kategori", SwingConstants.LEFT), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadLaporan() {
        model.setRowCount(0);
        String sql = "SELECT k.nama_kategori, COUNT(b.id_barang) AS jumlah_jenis, " +
                     "COALESCE(SUM(b.stok), 0) AS total_stok " +
                     "FROM kategori k LEFT JOIN barang b ON k.id_kategori = b.id_kategori " +
                     "GROUP BY k.id_kategori, k.nama_kategori ORDER BY k.nama_kategori";
        try (Statement st = Koneksi.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("nama_kategori"),
                        rs.getInt("jumlah_jenis"),
                        rs.getInt("total_stok")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat laporan: " + e.getMessage());
        }
    }

    private void cetak() {
        try {
            table.print();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak: " + e.getMessage());
        }
    }
}
