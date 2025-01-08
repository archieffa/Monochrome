// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package viewmodel;

import model.Database;
import model.Block;
import model.Player;
import view.Monochrome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ListIterator;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Game extends Component implements ActionListener {
    // Deklarasi variabel-variabel yang dibutuhkan
    private Monochrome monochrome;  // Referensi ke panel game
    private Database database;  // Objek database untuk menyimpan skor pemain
    private String username;  // Username pemain

    // Ukuran frame game
    int frameWidth = 480;
    int frameHeight = 560;

    // Variabel-variabel terkait player
    // Posisi awal player
    private int playerStartPosX = 480 / 8;
    private int playerStartPosY = 560 / 2;
    // Ukuran player
    private int playerWidth = 35;
    private int playerHeight = 55;

    // Timer untuk loop game dan penempatan balok
    private Player player;  // Objek player
    public Timer gameLoop;  // Timer untuk loop game
    private int gravity = 1;  // Gravitasi
    public Timer blocksCooldown;  // Timer untuk penempatan balok-balok

    // Status permainan
    private boolean gameStarted = false;  // Status permainan dimulai
    private boolean gameOver = false;  // Status permainan berakhir

    // Skor player
    private float score = 0;  // Skor keseluruhan
    private float upperScore = 0;  // Skor untuk balok atas
    private float lowerScore = 0;  // Skor untuk balok bawah

    private MusicTheme backgroundMusic;  // Objek musik latar
    private boolean confirmationShown = false; // Variabel untuk menandai apakah dialog konfirmasi sudah ditampilkan

    // Konstruktor untuk inisialisasi
    public Game(Monochrome monochrome, Database database, String username) {
        this.monochrome = monochrome;
        this.database = database;
        this.username = username;

        // Inisialisasi pemain dengan posisi dan gambar
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, monochrome.getPlayerImage());

        // Mulai loop game dan penempatan balok
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        blocksCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monochrome.getBlockController().placeBlocks();
            }
        });
        blocksCooldown.start();

        // Memuat musik latar
        backgroundMusic = new MusicTheme("src/assets/HoldOnTight.wav");
        // Memainkan musik latar
//        backgroundMusic.play(true);
    }

    // Metode untuk menggambar elemen-elemen game
    public void draw(Graphics g) {
        g.drawImage(monochrome.getBackgroundImage(), 0, 0, frameWidth, frameHeight, null);
        g.drawImage(monochrome.getPlayerImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        // Gambar dan tampilkan balok-balok
        for (Block block : monochrome.getBlockController().getBlocks()) {
            // Jika player berada di bawah balok atas, gambar player terlebih dahulu
            if (!block.isUpperBlock() && player.getPosY() + player.getHeight() > block.getPosY() + block.getHeight()) {
                g.drawImage(monochrome.getPlayerImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);
            }
            g.drawImage(block.getImage(), block.getPosX(), block.getPosY(), block.getWidth(), block.getHeight(), null);
        }

        // Gambar player setelah menggambar balok
        g.drawImage(monochrome.getPlayerImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

    }

    // Metode untuk menggerakkan elemen-elemen game
    public void move() {
        if (gameStarted) {
            // Update posisi vertikal player berdasarkan gaya gravitasi
            player.setVelocityY(player.getVelocityY() + gravity);
            player.setPosY(player.getPosY() + player.getVelocityY());
            player.setPosY(Math.max(player.getPosY(), 0));  // Pastikan posisi vertikal player tidak melebihi batas atas layar

            // Iterasi balok-balok yang ada
            ListIterator<Block> iterator = monochrome.getBlockController().getBlocks().listIterator();
            while (iterator.hasNext()) {
                Block block = iterator.next();
                // Update posisi horizontal balok
                block.setPosX(block.getPosX() + block.getVelocityX());
                // Hapus balok jika sudah keluar dari layar
                if (block.getPosX() + block.getWidth() < 0) {
                    iterator.remove();
                }
            }

            // Deteksi tabrakan antara player dan balok
            for (Block block : monochrome.getBlockController().getBlocks()) {
                if (isCollision(player, block)) {
                    player.setVelocityY(0);  // Set kecepatan vertikal player ke 0 saat terjadi tabrakan

                    // Penyesuaian posisi player saat terjadi tabrakan
                    if (block.isUpperBlock()) {
                        // Jika balok adalah balok atas, posisikan player tepat di bawah pipa
                        player.setPosY(block.getPosY() + block.getHeight() - player.getHeight());
                    } else {
                        // Jika balok adalah balok bawah, posisikan player tepat di atas pipa
                        player.setPosY(block.getPosY() - player.getHeight());
                    }

                    // Perhitungan dan penyesuaian skor
                    if (!block.isPassed()) {
                        block.setPassed(true);
                        score += block.getRandomScore();  // Tambahkan skor berdasarkan nilai random dari balok
                        monochrome.getScoreLabel().setText("Score: " + (int) score);  // Update tampilan skor di layar

                        if (block.isUpperBlock()) {
                            // Jika balok adalah balok atas, tambahkan skor ke upperScore
                            upperScore += block.getRandomScore();
                            monochrome.getUpperScoreLabel().setText("Up: " + (int) upperScore);
                        } else {
                            // Jika balok adalah balok bawah, tambahkan skor ke lowerScore
                            lowerScore += block.getRandomScore();
                            monochrome.getLowerScoreLabel().setText("Down: " + (int) lowerScore);
                        }
                    }
                }
            }

            // Game berakhir jika player mencapai batas bawah layar
            if (player.getPosY() + player.getHeight() >= frameHeight) {
                gameOver();
                return;
            }
        }
    }

    // Metode untuk mendeteksi tabrakan antara player dan balok
    private boolean isCollision(Player player, Block block) {
        // Membuat objek Rectangle untuk player dengan posisi dan ukuran player
        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
        // Membuat objek Rectangle untuk balok dengan posisi dan ukuran balok
        Rectangle blockRect = new Rectangle(block.getPosX(), block.getPosY(), block.getWidth(), block.getHeight());

        return playerRect.intersects(blockRect);  // Mengecek apakah Rectangle player dan Rectangle balok saling berpotongan (bertabrakan)
    }

    // Metode untuk memulai game
    public void startGame() {
        blocksCooldown.start();  // Memulai timer untuk cooldown balok
        gameLoop.start();  // Memulai loop game
        gameStarted = true;  // Menandai bahwa game telah dimulai
        backgroundMusic.play();  // Memulai pemutaran musik latar
    }

    // Metode untuk menampilkan layar game berakhir
    public void gameOver() {
        // Menghentikan game loop dan cooldown balok
        gameLoop.stop();
        blocksCooldown.stop();
        gameOver = true;  // Menandai bahwa game telah berakhir

        saveScoreToDatabase();  // Menyimpan skor ke database

        // Membuat panel pesan untuk menampilkan pesan game over
        JPanel messagePanel = new JPanel(new GridLayout(9, 1));
        messagePanel.setPreferredSize(new Dimension(200, 150));

        // Membuat label "Game Over!" dan menambahkan ke panel pesan
        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel space1 = new JLabel(" ");  // Label kosong untuk memberi spasi antar elemen

        // Membuat label skor akhir dan menambahkan ke panel pesan
        JLabel finalScoreLabel = new JLabel("Score: " + (int) score, SwingConstants.CENTER);
        JLabel finalUpperScoreLabel = new JLabel("Up: " + (int) upperScore, SwingConstants.CENTER);
        JLabel finalLowerScoreLabel = new JLabel("Down: " + (int) lowerScore, SwingConstants.CENTER);

        JLabel space2 = new JLabel(" ");  // Label kosong untuk memberi spasi antar elemen

        // Membuat label instruksi untuk memulai ulang permainan dan mengakhiri permainan
        JLabel restartLabel = new JLabel("Press 'R' to restart or", SwingConstants.CENTER);
        JLabel endGameLabel = new JLabel("'Space' to end the game", SwingConstants.CENTER);
        restartLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        endGameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Menambahkan semua label ke panel pesan
        messagePanel.add(gameOverLabel);
        messagePanel.add(space1);
        messagePanel.add(finalScoreLabel);
        messagePanel.add(finalUpperScoreLabel);
        messagePanel.add(finalLowerScoreLabel);
        messagePanel.add(space2);
        messagePanel.add(restartLabel);
        messagePanel.add(endGameLabel);

        // Menampilkan dialog pesan dengan panel pesan
        JOptionPane.showMessageDialog(this, messagePanel, "Message", JOptionPane.PLAIN_MESSAGE);
        backgroundMusic.stop();  // Menghentikan musik latar
    }

    // Metode untuk menyimpan skor ke database saat game berakhir
    private void saveScoreToDatabase() {
        try {
            // Ambil skor saat ini dari database
            // Query SQL untuk mendapatkan skor saat ini dari database berdasarkan username
            String query = "SELECT score, up, down FROM tscore WHERE username = '" + username + "'";
            ResultSet resultSet = database.selectQuery(query);  // Eksekusi query dan simpan hasilnya dalam ResultSet

            // Variabel untuk menyimpan skor saat ini
            int currentScore = 0;
            int currentUpperScore = 0;
            int currentLowerScore = 0;

            // Jika hasil query tidak kosong, ambil nilai-nilai skor dari database
            if (resultSet.next()) {
                currentScore = resultSet.getInt("score");
                currentUpperScore = resultSet.getInt("up");
                currentLowerScore = resultSet.getInt("down");
            }

            // Tambahkan skor baru dari game yang baru saja selesai ke skor yang ada di database
            int newScore = currentScore + (int) score;
            int newUpperScore = currentUpperScore + (int) upperScore;
            int newLowerScore = currentLowerScore + (int) lowerScore;

            // Memperbarui skor di database dengan skor yang baru
            String updateScoreSQL = String.format(
                    "UPDATE tscore SET score = %d, up = %d, down = %d WHERE username = '%s'",
                    newScore, newUpperScore, newLowerScore, username
            );
            database.insertUpdateDeleteQuery(updateScoreSQL);  // Eksekusi query update untuk menyimpan skor baru ke database

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode actionPerformed untuk menangani event dari timer
    public void actionPerformed(ActionEvent e) {
        move();
        monochrome.repaint();
    }

    public void restartGame() {
        gameOver = false;
        gameStarted = false;
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        monochrome.getBlockController().getBlocks().clear();

        monochrome.getBlockController().getBlocksCooldown().stop();
        monochrome.getBlockController().resetBlocksPosition();
        monochrome.getBlockController().getBlocksCooldown().start();
        gameLoop.start();

        score = 0;
        upperScore = 0;
        lowerScore = 0;
        monochrome.getScoreLabel().setText("Score: " + (int) score);
        monochrome.getUpperScoreLabel().setText("Up: " + (int) upperScore);
        monochrome.getLowerScoreLabel().setText("Down: " + (int) lowerScore);

        // Reset the confirmationShown flag when restarting the game
        setConfirmationShown(false);
    }

    // Setter untuk variabel confirmationShown
    public void setConfirmationShown(boolean shown) {
        this.confirmationShown = shown;
    }

    // GETTER
    public Player getPlayer() { return player; }
    public boolean getGameOver() { return gameOver; }
    public boolean getGameStarted() { return gameStarted; }
    // Getter untuk variabel confirmationShown
    public boolean isConfirmationShown() {
        return confirmationShown;
    }
}