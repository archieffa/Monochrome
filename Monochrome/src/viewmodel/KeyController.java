// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package viewmodel;

import view.Monochrome;
import view.MainMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Kelas untuk mengatur input dari keyboard
public class KeyController implements KeyListener {
    private Monochrome monochrome;  // Referensi ke panel Monochrome
    private MainMenu mainMenu;  // Referensi ke panel MainMenu

    // Konstruktor untuk membuat objek KeyController dengan parameter Monochrome
    public KeyController(Monochrome monochrome) {
        this.monochrome = monochrome;
        this.mainMenu = mainMenu;

        monochrome.addKeyListener(this);  // Menambahkan key listener ke panel Monochrome
    }

    // Metode yang dipanggil saat sebuah tombol keyboard ditekan dan kemudian dilepas
    public void keyTyped(KeyEvent e) {

    }

    // Metode yang dipanggil saat sebuah tombol keyboard ditekan
    public void keyPressed(KeyEvent e) {
        if (!monochrome.getGame().getGameOver()) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                // Jika tombol atas ditekan, naikkan posisi player dan mulai permainan jika belum dimulai
                if (!monochrome.getGame().getGameStarted()) {
                    monochrome.getGame().startGame();  // Memulai game
                }
                monochrome.getGame().getPlayer().setVelocityY(-10);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                monochrome.getGame().getPlayer().setVelocityY(10);  // Jika tombol bawah ditekan, turunkan posisi player
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                monochrome.getGame().getPlayer().setPosX(monochrome.getGame().getPlayer().getPosX() - 10);  // Jika tombol kiri ditekan, geser player ke kiri
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                monochrome.getGame().getPlayer().setPosX(monochrome.getGame().getPlayer().getPosX() + 10);  // Jika tombol kanan ditekan, geser player ke kanan
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                // Jika game belum berakhir dan sudah dimulai
                if (monochrome.getGame().getGameStarted() && !monochrome.getGame().getGameOver()) {
                    // Hentikan perulangan game dan blokir timer cooldown
                    monochrome.getGame().gameLoop.stop();
                    monochrome.getGame().blocksCooldown.stop();

                    // Jika dialog konfirmasi belum pernah ditampilkan sebelumnya
                    if (!monochrome.getGame().isConfirmationShown()) {
                        int choice = JOptionPane.showConfirmDialog(monochrome, "Are you sure you want to quit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            // Hentikan game jika player memilih 'Yes'
                            monochrome.getGame().gameOver();
                        } else {
                            // Setel tanda untuk menunjukkan bahwa dialog konfirmasi sudah ditampilkan
                            monochrome.getGame().setConfirmationShown(true);
                        }
                    } else {
                        // Jika dialog konfirmasi sudah ditampilkan sebelumnya dan player memilih 'No', lanjutkan game
                        monochrome.getGame().gameLoop.start();
                        monochrome.getGame().blocksCooldown.start();

                        // Setel kembali tanda confirmationShown saat melanjutkan game
                        monochrome.getGame().setConfirmationShown(false);
                    }
                } else {
                    // Jika game belum dimulai atau sudah berakhir, lanjutkan tanpa menampilkan dialog konfirmasi
                    if (!monochrome.getGame().getGameOver()) {
                        monochrome.getGame().gameLoop.start();
                        monochrome.getGame().blocksCooldown.start();
                    }
                }
            }
        } else {
            // Jika game sudah berakhir
            if (e.getKeyCode() == KeyEvent.VK_R) {
                monochrome.getGame().restartGame();  // Jika tombol 'R' ditekan, mulai ulang permainan
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                // Jika tombol 'Space' ditekan, tampilkan menu utama lagi
//                monochrome.showMainMenu();
                monochrome.setVisible(false);  // Menutup jendela game
                // Menampilkan menu utama kembali
                MainMenu mainMenu = new MainMenu();
                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainMenu.setSize(480, 560);
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setVisible(true);
//                mainMenu.populateList();
            }
        }
    }

    // Metode yang dipanggil saat sebuah tombol keyboard dilepas
    public void keyReleased(KeyEvent e) {

    }
}