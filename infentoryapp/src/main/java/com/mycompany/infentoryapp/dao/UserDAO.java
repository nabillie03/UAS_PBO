package com.mycompany.infentoryapp.dao;

import com.mycompany.infentoryapp.koneksi.Koneksi;
import com.mycompany.infentoryapp.model.User;
import java.sql.*;
import javax.swing.JOptionPane;

public class UserDAO {

    /**
     * Cek username & password ke database.
     * Return object User jika cocok, null jika tidak ditemukan.
     */
    public User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id_user"), rs.getString("username"),
                            rs.getString("password"), rs.getString("level"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal login: " + e.getMessage());
        }
        return null;
    }
}
