// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package model;

import java.awt.*;

public class Block {
    // Deklarasi variabel-variabel untuk menyimpan data balok
    private int posX;  // Posisi X dari balok
    private int posY;  // Posisi Y dari balok
    private int width;  // Lebar balok
    private int height;  // Panjang balok
    private Image image;  // Gambar yang merepresentasikan balok
    private int velocityX;  // Kecepatan gerak balok pada sumbu X
    private boolean passed;  // Menyimpan status apakah balok sudah dilewati oleh player
    private int randomScore;  // Skor acak yang diberikan saat balok dilewati
    private boolean isUpperBlock;  // Menyimpan status apakah balok ini adalah balok atas

    // Inisialisasi data balok
    public Block(int posX, int posY, int width, int height, Image image, boolean isUpperPipe) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityX = -4;
        this.passed = false;
        this.isUpperBlock = isUpperPipe;
        this.randomScore = randomScore;
    }

    // SETTER
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setRandomScore(int randomScore) {
        this.randomScore = randomScore;
    }

    public void setUpperBlock(boolean upperBlock) {
        isUpperBlock = upperBlock;
    }

    // GETTER
    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public boolean isPassed() {
        return passed;
    }

    public int getRandomScore() {
        return randomScore;
    }

    public boolean isUpperBlock() {
        return isUpperBlock;
    }
}