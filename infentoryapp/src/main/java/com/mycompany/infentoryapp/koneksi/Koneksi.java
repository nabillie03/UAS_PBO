package com.mycompany.infentoryapp.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Class untuk menangani koneksi ke database MySQL (phpMyAdmin/XAMPP).
 * Ganti USER dan PASSWORD sesuai konfigurasi MySQL di komputer kamu.
 * Default XAMPP biasanya: user = "root", password = "" (kosong).
 */
public class Koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/db_inventory?useSSL=false&serverTimezone=Asia/Jakarta";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Driver MySQL tidak ditemukan.\nTambahkan MySQL Connector/J ke Libraries project.\n" + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Koneksi database gagal.\nPastikan XAMPP/MySQL aktif dan database db_inventory sudah dibuat.\n" + e.getMessage());
        }
        return conn;
    }
}
