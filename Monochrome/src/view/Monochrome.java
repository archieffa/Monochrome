// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package view;

import viewmodel.*;
import javax.swing.*;
import java.awt.*;
import model.Database;

public class Monochrome extends JPanel {
    // Deklarasi variabel untuk objek game, controller, dan ukuran frame
    private Game game;
    private BlockController blockController;
    private KeyController keyController;

    int frameWidth = 480;
    int frameHeight = 560;

    // Deklarasi variabel untuk gambar-gambar yang digunakan
    Image backgroundImage;
    Image playerImage;
    Image lowerBlockImage;
    Image upperBlockImage;

    // Deklarasi label untuk skor
    private JLabel scoreLabel;
    private JLabel upperScoreLabel;
    private JLabel lowerScoreLabel;

    // Inisialisasi panel
    public Monochrome(Database database, String username) {
        // Mengatur ukuran dan fokus dari panel
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        setPreferredSize(new Dimension(480, 560));

        // Memuat gambar-gambar dari direktori assets
        backgroundImage = new ImageIcon(getClass().getResource("../assets/white.jpg")).getImage();
        playerImage = new ImageIcon(getClass().getResource("../assets/monicha.png")).getImage();
        lowerBlockImage = new ImageIcon(getClass().getResource("../assets/black.jpg")).getImage();
        upperBlockImage = new ImageIcon(getClass().getResource("../assets/grey.jpg")).getImage();

        // Membuat objek game dengan parameter panel ini, database, dan username
        game = new Game(this, database, username);  // Pass database and username to Game
        blockController = new BlockController(this);
        keyController = new KeyController(this);

        // Menambahkan listener untuk input keyboard
        addKeyListener(keyController);

        // Inisialisasi dan menambahkan label skor ke panel
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        add(scoreLabel);
        scoreLabel.setBounds(10, 10, 100, 50);

        upperScoreLabel = new JLabel("Up: 0");
        upperScoreLabel.setForeground(Color.BLACK);
        upperScoreLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        add(upperScoreLabel);
        upperScoreLabel.setBounds(10, 60, 100, 50);

        lowerScoreLabel = new JLabel("Down: 0");
        lowerScoreLabel.setForeground(Color.BLACK);
        lowerScoreLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        add(lowerScoreLabel);
        lowerScoreLabel.setBounds(10, 110, 100, 50);
    }

    @Override
    public void addNotify() {
        // Memastikan panel memiliki fokus input saat ditambahkan ke container
        super.addNotify();
        requestFocusInWindow();
    }

    protected void paintComponent(Graphics g) {
        // Melakukan override metode paintComponent untuk menggambar komponen
        super.paintComponent(g);
        if (game != null) {
            game.draw(g);  // Memanggil metode draw dari objek game
        }
    }

    // GETTER
    public int getFrameWidth() { return frameWidth; }
    public int getFrameHeight() { return frameHeight; }
    public Game getGame() { return game; }
    public BlockController getBlockController() { return blockController; }
    public KeyController getKeyController() { return keyController; }
    public Image getBackgroundImage() { return backgroundImage; }
    public Image getPlayerImage() { return playerImage; }
    public Image getLowerBlockImage() { return lowerBlockImage; }
    public Image getUpperBlockImage() { return upperBlockImage; }
    public JLabel getScoreLabel() { return scoreLabel; }
    public JLabel getUpperScoreLabel() { return upperScoreLabel; }
    public JLabel getLowerScoreLabel() { return lowerScoreLabel; }

    // SETTER
    public void setGame(Game game) { this.game = game; }
}