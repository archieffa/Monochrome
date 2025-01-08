// Saya Syifa Azzahra mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
// Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
// tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

package viewmodel;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicTheme {
    private Clip clip;

    // Konstruktor untuk memuat file audio dan mempersiapkan clip untuk pemutaran
    public MusicTheme(String filePath) {
        try {
            File file = new File(filePath);  // Membuat objek File dari path file audio
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);  // Mendapatkan InputStream audio
            clip = AudioSystem.getClip();  // Mendapatkan objek Clip untuk memutar audio
            clip.open(audioInputStream);  // Membuka clip dengan audio dari file yang dimuat
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk memulai pemutaran musik
    public void play() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Melakukan pemutaran berulang secara terus menerus
        }
    }

    // Metode untuk menghentikan pemutaran musik
    public void stop() {
        if (clip != null) {
            clip.stop();  // Menghentikan pemutaran musik
        }
    }

    // Metode untuk memeriksa apakah musik sedang diputar atau tidak
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}