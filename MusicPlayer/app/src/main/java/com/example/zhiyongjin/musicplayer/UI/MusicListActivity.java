package com.example.zhiyongjin.musicplayer.UI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.zhiyongjin.musicplayer.Adapter.ListViewAdapter;
import com.example.zhiyongjin.musicplayer.JsonData.Music;
import com.example.zhiyongjin.musicplayer.LoadData.LoadData;
import com.example.zhiyongjin.musicplayer.R;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {
    private ListView mListView;
    private ListViewAdapter mListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);

        //request permission if SDK version greater than 22
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        //initial UI
        initView();

        //Set ListView adapter
        new LoadData(this, new LoadData.DataInterface() {
            @Override
            public void dataInterface(List<Music.ResultsBean> mList) {
                mListViewAdapter = new ListViewAdapter(MusicListActivity.this, mList);
                mListView.setAdapter(mListViewAdapter);

                //Set Item Click Listener, go to Detail Page
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(MusicListActivity.this, "Click item" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MusicListActivity.this, MusicDetailActivity.class);
                        intent.putExtra("Position", position);
                        startActivity(intent);
                    }
                });
            }
        }).execute();
    }

    private void initView() {
        mListView = findViewById(R.id.music_list);
    }

    //start MusicListActivity using Intent(From Splash page)
    public static void start(Context context) {
        Intent intent = new Intent(context, MusicListActivity.class);
        context.startActivity(intent);
    }
}