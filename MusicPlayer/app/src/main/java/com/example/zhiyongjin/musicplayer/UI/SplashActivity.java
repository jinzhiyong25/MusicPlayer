package com.example.zhiyongjin.musicplayer.UI;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zhiyongjin.musicplayer.R;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    public static final int CODE = 1001;
    public static final int INTERVAL_TIME = 1000;
    //Total time for the splash page.
    public static final int TOTAL_TIME = 3000;
    private TextView skip;
    private MyHandler mMyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        //handler send message
        mMyHandler = new MyHandler(this);
        Message message = Message.obtain();
        message.what = CODE;
        message.arg1 = TOTAL_TIME;
        mMyHandler.sendMessage(message);

        initEvent();
    }

    //Click Skip to skip splash page
    private void initEvent() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicListActivity.start(SplashActivity.this);
                SplashActivity.this.finish();

                /**
                 * remove message, will help project don't create two MusicList Activity
                 * If don't have this method, and user click the skip button,
                 * it will create two MusicList Activity
                 */
                mMyHandler.removeMessages(CODE);
            }
        });
    }

    private void initView() {
        skip = findViewById(R.id.textview_skip);
    }

    public static class MyHandler extends Handler {

        public final WeakReference<SplashActivity> mWeakReference;

        public MyHandler(SplashActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity splashActivity = mWeakReference.get();
            if (msg.what == CODE) {
                if (splashActivity != null) {
                    //Set textview, update UI
                    int time = msg.arg1;
                    splashActivity.skip.setText(time / INTERVAL_TIME + splashActivity.getString(R.string.seconds_skip));

                    //Send countdown
                    Message message = Message.obtain();
                    message.what = CODE;
                    message.arg1 = time - INTERVAL_TIME;

                    //if time > 0, it will using 1 seconds as interval time, else will go to
                    //MusicList Activity
                    if (time > 0) {
                        sendMessageDelayed(message, INTERVAL_TIME);
                    } else {
                        MusicListActivity.start(splashActivity);
                        splashActivity.finish();
                    }
                }
            }
        }
    }
}
