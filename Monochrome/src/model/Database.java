// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
    // Deklarasi objek koneksi dan statement
    private Connection connection;
    private Statement statement;

    // Inisialisasi koneksi ke database
    public Database() {
        try {
            // Explicitly load the MySQL driver (optional for modern JDBC but safe)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat objek Properties
            Properties props = new Properties();
            props.put("user", "root");  // Menambahkan nama pengguna ke properti
            props.put("password", "");   // Menambahkan kata sandi ke properti

            // Membuat koneksi dengan menggunakan URL database dan properti
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmd_dpbo", props);
            // Membuat objek Statement untuk menjalankan query SQL
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Menjalankan query SELECT dan mengembalikan ResultSet
    public ResultSet selectQuery(String sql){
        try{
            // Menjalankan query SELECT
            statement.executeQuery(sql);
            // Mengembalikan hasil query dalam bentuk ResultSet
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Menjalankan query INSERT, UPDATE, atau DELETE
    public int insertUpdateDeleteQuery(String sql) {
        try {
            // Menjalankan query INSERT, UPDATE, atau DELETE
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Menangani kesalahan yang terjadi saat menjalankan query
            throw new RuntimeException(e);
        }
    }

    // Mendapatkan objek statement
    public Statement getStatement() {
        return statement;
    }

    // Menutup koneksi database
    public void close() {
        try {
            if (statement != null) {
                statement.close();  // Menutup objek Statement jika tidak null
            }
            if (connection != null) {
                connection.close();  // Menutup objek Connection jika tidak null
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);  // Menangani kesalahan yang terjadi saat menutup koneksi
        }
    }
}