package com.mycompany.infentoryapp.gui;

import com.mycompany.infentoryapp.dao.BarangDAO;
import com.mycompany.infentoryapp.dao.KategoriDAO;
import com.mycompany.infentoryapp.model.Barang;
import com.mycompany.infentoryapp.model.Kategori;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormBarang extends JFrame {

    private JTextField txtKode, txtNama, txtStok, txtHarga, txtCari;
    private JComboBox<Kategori> cmbKategori;
    private JTable table;
    private DefaultTableModel model;
    private BarangDAO barangDAO = new BarangDAO();
    private KategoriDAO kategoriDAO = new KategoriDAO();
    private int selectedId = -1;

    public FormBarang() {
        setTitle("Data Barang");
        setSize(750, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadKategori();
        loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Kode Barang:"), gbc);
        gbc.gridx = 1; txtKode = new JTextField(12); formPanel.add(txtKode, gbc);

        gbc.gridx = 2; gbc.gridy = 0; formPanel.add(new JLabel("Nama Barang:"), gbc);
        gbc.gridx = 3; txtNama = new JTextField(15); formPanel.add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Kategori:"), gbc);
        gbc.gridx = 1; cmbKategori = new JComboBox<>(); formPanel.add(cmbKategori, gbc);

        gbc.gridx = 2; gbc.gridy = 1; formPanel.add(new JLabel("Stok:"), gbc);
        gbc.gridx = 3; txtStok = new JTextField(8); formPanel.add(txtStok, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Harga:"), gbc);
        gbc.gridx = 1; txtHarga = new JTextField(12); formPanel.add(txtHarga, gbc);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersih = new JButton("Bersihkan");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnTambah); btnPanel.add(btnUbah); btnPanel.add(btnHapus); btnPanel.add(btnBersih);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        formPanel.add(btnPanel, gbc);

        // panel pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCari = new JTextField(15);
        JButton btnCari = new JButton("Cari");
        JButton btnTampilSemua = new JButton("Tampil Semua");
        searchPanel.add(new JLabel("Cari (kode/nama):"));
        searchPanel.add(txtCari);
        searchPanel.add(btnCari);
        searchPanel.add(btnTampilSemua);

        model = new DefaultTableModel(new Object[]{"ID", "Kode", "Nama Barang", "Kategori", "Stok", "Harga"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> isiFormDariTabel());

        btnTambah.addActionListener(e -> tambah());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnBersih.addActionListener(e -> bersihkanForm());
        btnCari.addActionListener(e -> cari());
        btnTampilSemua.addActionListener(e -> loadData());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadKategori() {
        cmbKategori.removeAllItems();
        for (Kategori k : kategoriDAO.getAll()) {
            cmbKategori.addItem(k);
        }
    }

    private void loadData() {
        model.setRowCount(0);
        List<Barang> list = barangDAO.getAll();
        for (Barang b : list) {
            model.addRow(new Object[]{b.getIdBarang(), b.getKodeBarang(), b.getNamaBarang(),
                    b.getNamaKategori(), b.getStok(), b.getHarga()});
        }
    }

    private void cari() {
        model.setRowCount(0);
        List<Barang> list = barangDAO.cari(txtCari.getText().trim());
        for (Barang b : list) {
            model.addRow(new Object[]{b.getIdBarang(), b.getKodeBarang(), b.getNamaBarang(),
                    b.getNamaKategori(), b.getStok(), b.getHarga()});
        }
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedId = (int) model.getValueAt(row, 0);
            txtKode.setText((String) model.getValueAt(row, 1));
            txtNama.setText((String) model.getValueAt(row, 2));
            String namaKategori = (String) model.getValueAt(row, 3);
            for (int i = 0; i < cmbKategori.getItemCount(); i++) {
                if (cmbKategori.getItemAt(i).getNamaKategori().equals(namaKategori)) {
                    cmbKategori.setSelectedIndex(i);
                    break;
                }
            }
            txtStok.setText(String.valueOf(model.getValueAt(row, 4)));
            txtHarga.setText(String.valueOf(model.getValueAt(row, 5)));
        }
    }

    private Barang ambilInput() {
        Barang b = new Barang();
        b.setKodeBarang(txtKode.getText().trim());
        b.setNamaBarang(txtNama.getText().trim());
        Kategori k = (Kategori) cmbKategori.getSelectedItem();
        if (k != null) b.setIdKategori(k.getIdKategori());
        b.setStok(Integer.parseInt(txtStok.getText().trim()));
        b.setHarga(Double.parseDouble(txtHarga.getText().trim()));
        return b;
    }

    private boolean validasi() {
        if (txtKode.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty()
                || txtStok.getText().trim().isEmpty() || txtHarga.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return false;
        }
        if (cmbKategori.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Kategori belum tersedia, tambahkan kategori dulu.");
            return false;
        }
        try {
            Integer.parseInt(txtStok.getText().trim());
            Double.parseDouble(txtHarga.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus angka bulat, harga harus angka.");
            return false;
        }
        return true;
    }

    private void tambah() {
        if (!validasi()) return;
        if (barangDAO.tambah(ambilInput())) {
            JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan.");
            bersihkanForm();
            loadData();
        }
    }

    private void ubah() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau diubah dari tabel dulu.");
            return;
        }
        if (!validasi()) return;
        Barang b = ambilInput();
        b.setIdBarang(selectedId);
        if (barangDAO.update(b)) {
            JOptionPane.showMessageDialog(this, "Barang berhasil diubah.");
            bersihkanForm();
            loadData();
        }
    }

    private void hapus() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus dari tabel dulu.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus barang ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (barangDAO.hapus(selectedId)) {
                JOptionPane.showMessageDialog(this, "Barang berhasil dihapus.");
                bersihkanForm();
                loadData();
            }
        }
    }

    private void bersihkanForm() {
        txtKode.setText(""); txtNama.setText(""); txtStok.setText(""); txtHarga.setText("");
        selectedId = -1;
        table.clearSelection();
    }
}
