// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package model;

import java.awt.*;

public class Player {
    // Deklarasi variabel-variabel untuk menyimpan data player
    private int posX;  // Posisi X dari player
    private int posY;  // Posisi Y dari player
    private int width;  // Lebar player
    private int height;  // Tinggi player
    private Image image;  // Gambar yang merepresentasikan player
    private int velocityY;  // Kecepatan gerak player pada sumbu Y
    private int velocityX;  // Kecepatan gerak player pada sumbu X

    // Inisialisasi data player
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;

        this.velocityY = -0;
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

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    // GETTER
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
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

    public int getVelocityY() {
        return velocityY;
    }

    public int getVelocityX() {
        return velocityX;
    }
}