// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package viewmodel;

import model.Block;
import view.Monochrome;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class BlockController {
    private Monochrome monochrome;  // Referensi ke panel game
    private final int[] blockValues = {10, 20, 30, 40, 50};  // Nilai-nilai yang mungkin untuk skor blok

    // lebar dan tinggi frame game
    int frameWidth = 480;
    int frameHeight = 560;

    private int blockStartPosX = 360;  // Posisi awal X untuk balok
    private int blockStartPosY = 0;  // Posisi awal Y untuk balok
    private int blockWidth = 64;  // Lebar balok
    private int blockHeight = 512;  // Tinggi balok
    private ArrayList<Block> blocks;  // Daftar balok yang muncul
    private Timer blocksCooldown;  // Timer untuk mengatur waktu munculnya balok-balok baru
    private boolean placingUpperBlock = true;  // Status untuk menentukan balok atas atau bawah
    private int originalBlocksCooldownDelay;  // Simpan delay untuk reset nanti

    // Konstruktor untuk membuat objek BlockController dengan parameter Monochrome
    public BlockController(Monochrome monochrome) {
        this.monochrome = monochrome;

        blocks = new ArrayList<Block>();  // Inisialisasi daftar balok

        // Timer untuk mengatur waktu munculnya balok-balok baru
        blocksCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeBlocks();  // Panggil method placeBlocks() untuk menempatkan balok-balok baru
            }
        });
        blocksCooldown.start();  // Mulai timer

        originalBlocksCooldownDelay = blocksCooldown.getDelay();  // Simpan delay
    }

    // Method untuk menempatkan balok baru di layar
    public void placeBlocks() {
        int randomPosY;
        int openingSpace = frameHeight / 4;  // Ruang pembukaan antara balok atas dan bawah
        int minHorizontalSpacing = frameWidth / 2;  // Jarak horizontal minimum antara dua balok

        // Menentukan posisi Y berdasarkan apakah balok itu akan ditempatkan di atas atau di bawah
        if (placingUpperBlock) {
            randomPosY = (int) (blockStartPosY - blockHeight / 4 - Math.random() * (blockHeight / 2));
        } else {
            randomPosY = (int) (blockStartPosY + openingSpace + Math.random() * (blockHeight / 2));
        }

        // Pilih nilai skor balok secara acak
        int randomIndex = new Random().nextInt(blockValues.length);
        int randomScore = blockValues[randomIndex];

        // Penentuan panjang balok berdasarkan posisi Y dan apakah itu balok atas atau bawah
        int blockLength = Math.abs(randomPosY - (placingUpperBlock ? 0 : frameHeight));

        // Buat objek balok baru sesuai dengan jenisnya
        Block newBlock;
        if (placingUpperBlock) {
            newBlock = new Block(blockStartPosX, randomPosY, blockWidth, blockHeight, monochrome.getUpperBlockImage(), true);
        } else {
            newBlock = new Block(blockStartPosX, randomPosY, blockWidth, blockHeight, monochrome.getLowerBlockImage(), false);
        }

        // Tetapkan nilai skor acak untuk balok baru
        newBlock.setRandomScore(randomScore);
        blocks.add(newBlock);  // Tambahkan balok baru ke dalam daftar balok

        // Mengatur posisi X untuk balok berikutnya
        blockStartPosX += minHorizontalSpacing;

        // Ganti status untuk menentukan jenis balok berikutnya (atas atau bawah)
        placingUpperBlock = !placingUpperBlock;

        // Panggil repaint setelah menambahkan balok
        monochrome.repaint();
    }

    // Method untuk mereset posisi dan status balok
    public void resetBlocksPosition() {
        blocks.clear();  // Hapus semua balok yang ada

        // Reset posisi awal balok ke posisi default
        blockStartPosX = 360;
        blockStartPosY = 0;
        placingUpperBlock = true; // Set kembali status untuk meletakkan balok atas pertama kali

        // Reset kecepatan balok
        blocksCooldown.setDelay(originalBlocksCooldownDelay);  // Reset delay ke nilai aslinya
        blocksCooldown.setInitialDelay(originalBlocksCooldownDelay);  // Reset delay awal
        blocksCooldown.restart();  // Mulai timer kembali
    }

    // Method untuk mendapatkan daftar balok
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    // Method untuk mendapatkan timer balok
    public Timer getBlocksCooldown() {
        return blocksCooldown;
    }
}