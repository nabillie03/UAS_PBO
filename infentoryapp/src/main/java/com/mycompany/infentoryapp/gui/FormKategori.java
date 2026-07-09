package com.mycompany.infentoryapp.gui;

import com.mycompany.infentoryapp.dao.KategoriDAO;
import com.mycompany.infentoryapp.model.Kategori;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormKategori extends JFrame {

    private JTextField txtNama;
    private JTable table;
    private DefaultTableModel model;
    private KategoriDAO dao = new KategoriDAO();
    private int selectedId = -1;

    public FormKategori() {
        setTitle("Data Kategori");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Nama Kategori:"));
        txtNama = new JTextField(20);
        formPanel.add(txtNama);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersih = new JButton("Bersihkan");
        formPanel.add(btnTambah);
        formPanel.add(btnUbah);
        formPanel.add(btnHapus);
        formPanel.add(btnBersih);

        model = new DefaultTableModel(new Object[]{"ID", "Nama Kategori"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> isiFormDariTabel());

        btnTambah.addActionListener(e -> tambah());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnBersih.addActionListener(e -> bersihkanForm());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Kategori> list = dao.getAll();
        for (Kategori k : list) {
            model.addRow(new Object[]{k.getIdKategori(), k.getNamaKategori()});
        }
    }

    private void isiFormDariTabel() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedId = (int) model.getValueAt(row, 0);
            txtNama.setText((String) model.getValueAt(row, 1));
        }
    }

    private void tambah() {
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori wajib diisi!");
            return;
        }
        if (dao.tambah(txtNama.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Kategori berhasil ditambahkan.");
            bersihkanForm();
            loadData();
        }
    }

    private void ubah() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau diubah dari tabel dulu.");
            return;
        }
        if (dao.update(selectedId, txtNama.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Kategori berhasil diubah.");
            bersihkanForm();
            loadData();
        }
    }

    private void hapus() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus dari tabel dulu.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus kategori ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.hapus(selectedId)) {
                JOptionPane.showMessageDialog(this, "Kategori berhasil dihapus.");
                bersihkanForm();
                loadData();
            }
        }
    }

    private void bersihkanForm() {
        txtNama.setText("");
        selectedId = -1;
        table.clearSelection();
    }
}
