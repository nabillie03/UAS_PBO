package com.mycompany.infentoryapp.dao;

import com.mycompany.infentoryapp.koneksi.Koneksi;
import com.mycompany.infentoryapp.model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class BarangDAO {

    private static final String BASE_QUERY =
        "SELECT b.id_barang, b.kode_barang, b.nama_barang, b.id_kategori, " +
        "k.nama_kategori, b.stok, b.harga " +
        "FROM barang b LEFT JOIN kategori k ON b.id_kategori = k.id_kategori ";

    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        String sql = BASE_QUERY + "ORDER BY b.nama_barang";
        try (Statement st = Koneksi.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data barang: " + e.getMessage());
        }
        return list;
    }

    public List<Barang> cari(String keyword) {
        List<Barang> list = new ArrayList<>();
        String sql = BASE_QUERY + "WHERE b.nama_barang LIKE ? OR b.kode_barang LIKE ? ORDER BY b.nama_barang";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mencari barang: " + e.getMessage());
        }
        return list;
    }

    public boolean tambah(Barang b) {
        String sql = "INSERT INTO barang (kode_barang, nama_barang, id_kategori, stok, harga) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setInt(3, b.getIdKategori());
            ps.setInt(4, b.getStok());
            ps.setDouble(5, b.getHarga());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menambah barang: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Barang b) {
        String sql = "UPDATE barang SET kode_barang=?, nama_barang=?, id_kategori=?, stok=?, harga=? WHERE id_barang=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setInt(3, b.getIdKategori());
            ps.setInt(4, b.getStok());
            ps.setDouble(5, b.getHarga());
            ps.setInt(6, b.getIdBarang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah barang: " + e.getMessage());
            return false;
        }
    }

    public boolean hapus(int id) {
        String sql = "DELETE FROM barang WHERE id_barang = ?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus barang: " + e.getMessage());
            return false;
        }
    }

    private Barang mapRow(ResultSet rs) throws SQLException {
        return new Barang(
                rs.getInt("id_barang"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getInt("id_kategori"),
                rs.getString("nama_kategori"),
                rs.getInt("stok"),
                rs.getDouble("harga")
        );
    }
}
