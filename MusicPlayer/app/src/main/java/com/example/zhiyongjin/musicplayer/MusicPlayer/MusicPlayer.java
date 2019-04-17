package com.example.zhiyongjin.musicplayer.MusicPlayer;

import android.media.MediaPlayer;

import java.io.IOException;

public class MusicPlayer {

    private static MusicPlayer sMusicPlayer;
    private MediaPlayer mediaPlayer;

    private MusicPlayer() {
        mediaPlayer = new MediaPlayer();
    }
    public static MusicPlayer getInstance() {
        if (sMusicPlayer == null) {
            sMusicPlayer = new MusicPlayer();
        }
        return sMusicPlayer;
    }

    public void play(final String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stop() {
        mediaPlayer.stop();
    }
}