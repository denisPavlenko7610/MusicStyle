package com.denis7610.musicstyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdView adView;
    private ImageButton playButton;
    private boolean isPlaying = false;
    private ImageButton nextButton;
    private ImageButton previewButton;
    private SoundPool soundPool;
    private int[] instrumentalSounds = new int[12];
    List<Integer> listOfMusic = new ArrayList<>();
    MediaPlayer mediaPlayer;
    float volumeInstruments = 0.7f;
    private List<String> musicText;
    private TextView playerTextMusic;

    private int musicIndex = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        createSoundPool();

        //adMob
        adMobCreate();

    }

    private void createSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();

        //sound1 = soundPool.load(this,R.raw.aa_kick,1);
        for (int i = 0; i < 12; i++) {
            instrumentalSounds[i] = soundPool.load(this, listOfMusic.get(i), 1);
        }
    }

    private void initComponents() {
        playButton = findViewById(R.id.playButton);
        musicText = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.musicNames)));
        playerTextMusic = findViewById(R.id.textView2);

        changePlayerText();

        //add all music
        for (Field field : R.raw.class.getFields()) {
            try {
                listOfMusic.add(field.getInt(field));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        mediaPlayer = MediaPlayer.create(this, listOfMusic.get(musicIndex));
    }

    private void changePlayerText() {
        playerTextMusic.setText("#" + (musicIndex - 11) + " " + musicText.get(musicIndex - 12));
    }

    private void adMobCreate() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void licenseInfo(View view) {
        Intent intent = new Intent(this, LicenseInfo.class);
        startActivity(intent);
    }

    public void playInstrumentalSound(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                soundPool.play(instrumentalSounds[0], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton2:
                soundPool.play(instrumentalSounds[1], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton3:
                soundPool.play(instrumentalSounds[2], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton4:
                soundPool.play(instrumentalSounds[3], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton5:
                soundPool.play(instrumentalSounds[4], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton6:
                soundPool.play(instrumentalSounds[5], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton7:
                soundPool.play(instrumentalSounds[6], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton8:
                soundPool.play(instrumentalSounds[7], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton9:
                soundPool.play(instrumentalSounds[8], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton10:
                soundPool.play(instrumentalSounds[9], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton11:
                soundPool.play(instrumentalSounds[10], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
            case R.id.imageButton12:
                soundPool.play(instrumentalSounds[11], volumeInstruments, volumeInstruments, 0, 0, 1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playButtonClick(View view) {
        createMediaPlayer();
    }

    private void createMediaPlayer() {
        if (!isPlaying) {
            isPlaying = true;
            mediaPlayer = MediaPlayer.create(this, listOfMusic.get(musicIndex));
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.start();
            playButton.setImageResource(R.drawable.ic_stop_black_24dp);
            changePlayerText();
        } else {
            isPlaying = false;
            mediaPlayer.stop();
            mediaPlayer.release();
            playButton.setImageResource(R.drawable.ic_play_circle);
            changePlayerText();
        }
    }

    public void nextMusic(View view) {
        if (musicIndex < listOfMusic.size()-1) {
            musicIndex++;
            isPlaying = false;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                createMediaPlayer();
            }
            changePlayerText();
        } else {
            musicIndex = 12;
            isPlaying = false;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                createMediaPlayer();
            }
            changePlayerText();
        }
    }

    public void previewMusic(View view) {
        if (musicIndex > 12) {
            musicIndex--;
            isPlaying = false;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                createMediaPlayer();
            }
            changePlayerText();
        } else {
            musicIndex = 12;
            changePlayerText();
        }
    }


}
