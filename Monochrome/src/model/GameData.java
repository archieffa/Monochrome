// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package model;

public class GameData {
    // Deklarasi variabel-variabel untuk menyimpan data game
    private String username;  // Menyimpan username
    private int score;  // Menyimpan skor keseluruhan
    private int up;  // Menyimpan skor untuk balok atas
    private int down;  // Menyimpan skor untuk balok bawah

    // Inisialisasi data game
    public GameData(String username, int score, int up, int down) {
        this.username = username;
        this.score = score;
        this.up = up;
        this.down = down;
    }

    // SETTER
    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    // GETTER
    public String getUsername() {
        return this.username;
    }

    public int getScore() {
        return this.score;
    }

    public int getUp() {
        return this.up;
    }

    public int getDown() {
        return this.down;
    }
}