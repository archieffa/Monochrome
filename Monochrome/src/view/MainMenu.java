// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package view;

import model.Database;
import model.GameData;
import viewmodel.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainMenu extends JFrame {
    // Deklarasi variabel-variabel yang dibutuhkan
    private int selectedIndex = -1;  // Indeks baris yang dipilih pada tabel
    private ArrayList<GameData> listScore;  // Daftar skor dari database
    private Database database;  // Objek Database untuk mengakses data

    private JPanel mainPanel;  // Panel utama
    private JLabel titleLabel;  // Label judul
    private JLabel usernameLabel;  // Label untuk input username
    private JTextField usernameField;  // Field untuk input username
    private JTable scoreTable;  // Tabel untuk menampilkan skor
    private JButton playButton;  // Tombol Play
    private JButton quitButton;  // Tombol Quit

    // Metode main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainMenu.setSize(480, 560);
            mainMenu.setLocationRelativeTo(null);
            mainMenu.setVisible(true);
            mainMenu.populateList();  // Mengisi daftar skor saat aplikasi dimulai
        });
    }

    // Konstruktor
    public MainMenu() {
        listScore = new ArrayList<>();  // Inisialisasi daftar skor
        database = new Database();  // Inisialisasi objek Database untuk mengakses data

        // Inisialisasi komponen GUI
        titleLabel = new JLabel("Monochrome - Don't Let Monicha Falls!", JLabel.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 17));

        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        playButton = new JButton("Play");
        quitButton = new JButton("Quit");

        // Menambahkan ActionListener untuk tombol Play
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertOrUpdateData();  // Memasukkan atau memperbarui data pada database
            }
        });

        // Menambahkan ActionListener untuk tombol Quit
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.close();  // Menutup koneksi database sebelum keluar
                System.exit(0);  // Keluar dari aplikasi
            }
        });

        setupUI();  // Menyiapkan antarmuka pengguna

        // Menambahkan mouse listener ke tabel skor
        scoreTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectUsernameFromTable();  // Memilih username dari tabel
            }
        });
    }

    // Metode untuk mengatur model tabel
    public final DefaultTableModel setTable() {
        // Menyiapkan kolom-kolom tabel
        Object[] column = {"Username", "Score", "Up", "Down"};
        DefaultTableModel temp = new DefaultTableModel(null, column);
        try {
            // Mengambil data skor dari database
            ResultSet resultSet = database.selectQuery("SELECT * FROM tscore");
            while (resultSet.next()) {
                // Menambahkan data ke model tabel
                Object[] row = new Object[4];
                row[0] = resultSet.getString("username");
                row[1] = resultSet.getInt("score");
                row[2] = resultSet.getInt("up");
                row[3] = resultSet.getInt("down");
                temp.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    // Metode untuk memasukkan atau memperbarui data pada database
    public void insertOrUpdateData() {
        String username = usernameField.getText();  // Mengambil username dari field input
        if (username.isEmpty()) {
            // Menampilkan pesan error jika field username kosong
            JOptionPane.showMessageDialog(null, "Username is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Menghentikan eksekusi metode
        }

        try {
            // Mengecek apakah username sudah terdaftar di database
            ResultSet resultSet = database.selectQuery("SELECT * FROM tscore WHERE username = '" + username + "'");
            if (resultSet.next()) {
                startGame();  // Memulai permainan jika pemain sudah terdaftar di database
            } else {
                // Jika username belum terdaftar di database, tambahkan ke database dengan skor awal 0
                database.insertUpdateDeleteQuery("INSERT INTO tscore (username, score, up, down) VALUES ('" + username + "', 0, 0, 0)");
                startGame();  // Memulai permainan
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metode untuk memulai permainan
    private void startGame() {
        String username = usernameField.getText();  // Mengambil username dari field input
        if (username.isEmpty()) {
            // Menampilkan pesan error jika username kosong
            JOptionPane.showMessageDialog(null, "Username is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Menghentikan eksekusi metode
        }

        // Membuat frame baru untuk permainan
        JFrame gameFrame = new JFrame("Monochrome - Don't Let Monicha Falls!");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(this.getSize());
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);

        // Membuat panel Monochrome untuk permainan
        Monochrome monochrome = new Monochrome(database, username);
        Game game = new Game(monochrome, database, username);  // Menginisialisasi objek Game dengan database dan username
        monochrome.setGame(game);  // Menetapkan objek Game pada panel Monochrome

        gameFrame.add(monochrome);  // Menambahkan panel Monochrome ke frame permainan
        gameFrame.setVisible(true);  // Menampilkan frame permainan

        monochrome.requestFocus();  // Memfokuskan input ke panel Monochrome
        this.dispose();  // Menutup frame MainMenu
    }

    // Metode untuk mengatur antarmuka pengguna
    private void setupUI() {
        mainPanel = new JPanel(null);  // Menggunakan null layout agar bisa menyesuaikan komponen secara langsung

        titleLabel.setBounds(50, 20, 380, 30);  // Mengatur posisi dan ukuran label judul
        mainPanel.add(titleLabel);

        // Label dan field untuk input username
        usernameLabel.setBounds(50, 60, 80, 30);  // Mengatur posisi dan ukuran label username
        mainPanel.add(usernameLabel);

        usernameField.setBounds(140, 60, 200, 30);  // Mengatur posisi dan ukuran field username
        mainPanel.add(usernameField);

        // Membuat model tabel dan menetapkannya pada JTable
        DefaultTableModel tableModel = setTable();
        scoreTable = new JTable(tableModel);

        // Menetapkan JScrollPane (yang berisi tabel) pada panel utama
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBounds(50, 100, 380, 300);
        mainPanel.add(scrollPane);

        // Tombol Play
        playButton.setBounds(50, 420, 150, 40);  // Mengatur posisi dan ukuran tombol Play
        mainPanel.add(playButton);

        // Tombol Quit
        quitButton.setBounds(280, 420, 150, 40);  // Mengatur posisi dan ukuran tombol Quit
        mainPanel.add(quitButton);

        // Mengatur ukuran frame dan properti lainnya
        setSize(480, 560);
        setResizable(false);  // Mengunci ukuran frame agar tidak dapat diubah
        setLocationRelativeTo(null);  // Menempatkan frame di tengah layar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Menutup aplikasi ketika frame ditutup
        setContentPane(mainPanel);  // Menetapkan panel utama sebagai konten dari frame
    }

    // Metode untuk mengambil data skor dari database
    private void fetchDataFromDatabase() {
        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM tscore");  // Mengambil data skor dari database
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                int up = resultSet.getInt("up");
                int down = resultSet.getInt("down");
                listScore.add(new GameData(username, score, up, down));  // Menambahkan data ke dalam daftar skor
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk mengisi ulang daftar skor dari database
    private void populateList() {
        listScore.clear();  // Menghapus data yang ada dalam daftar skor
        fetchDataFromDatabase();  // Mengambil data skor dari database
    }

    // Metode untuk memilih username dari tabel skor dan mengisinya ke dalam field username
    private void selectUsernameFromTable() {
        int row = scoreTable.getSelectedRow();  // Mengambil indeks baris yang dipilih dalam tabel skor
        if (row != -1) {
            // Mengambil username dari baris yang dipilih dalam tabel skor
            String selectedUsername = (String) scoreTable.getValueAt(row, 0);
            if (usernameField.getText().isEmpty()) {   // Hanya isi field username jika kosong
                usernameField.setText(selectedUsername);  // Mengisi field username dengan username yang dipilih
            }
        }
    }
}