package com.example.zhiyongjin.musicplayer.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhiyongjin.musicplayer.JsonData.Music;
import com.example.zhiyongjin.musicplayer.LoadData.LoadData;
import com.example.zhiyongjin.musicplayer.LoadData.LoadImage;
import com.example.zhiyongjin.musicplayer.MusicPlayer.MusicPlayer;
import com.example.zhiyongjin.musicplayer.R;

import java.util.List;

public class MusicDetailActivity extends AppCompatActivity {
    private ImageView mImageView;
    private List<Music.ResultsBean> mList;
    TextView singerName, songName, collectionName, songPrice, country, releaseData;
    Button playButton, stopButton;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        Intent intent = getIntent();
        mPosition = intent.getIntExtra("Position", 0);

        //request permission
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        initView();

        //load data, and use the position which from intent to target specific item.
        new LoadData(this, new LoadData.DataInterface() {
            @Override
            public void dataInterface(List<Music.ResultsBean> list) {
                mList = list;
                Music.ResultsBean music = mList.get(mPosition);
                TransferView(music);
            }
        }).execute();
        initEvent();
    }

    //Click to play music
    private void initEvent() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.getInstance().play(mList.get(mPosition).getPreviewUrl());
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.getInstance().stop();
            }
        });
    }

    //set data into TextView
    private void TransferView(Music.ResultsBean mResultsBean) {
        songName.setText(mResultsBean.getTrackName());
        singerName.setText(mResultsBean.getArtistName());
        collectionName.setText(mResultsBean.getCollectionName());
        songPrice.setText(mResultsBean.getTrackPrice() + " " + mResultsBean.getCurrency());
        country.setText(mResultsBean.getCountry());
        releaseData.setText(mResultsBean.getReleaseDate());
        new LoadImage(mResultsBean.getArtworkUrl100(), new LoadImage.ImageInterface() {
            @Override
            public void imageInterface(Bitmap mBitmap) {
                mImageView.setImageBitmap(mBitmap);
            }
        }, MusicDetailActivity.this).execute();
    }

    private void initView() {
        mImageView = findViewById(R.id.image_detail);
        singerName = findViewById(R.id.textview_singername_detail);
        songName = findViewById(R.id.textview_songname_detail);
        collectionName = findViewById(R.id.textview_collectionname_detail);
        songPrice = findViewById(R.id.textview_songprice_detail);
        playButton = findViewById(R.id.play_button_detail);
        stopButton = findViewById(R.id.stop_button_detail);
        country = findViewById(R.id.textview_country_detail);
        releaseData = findViewById(R.id.textview_releasedata_detail);
    }
}
