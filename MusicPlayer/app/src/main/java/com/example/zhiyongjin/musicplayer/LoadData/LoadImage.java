package com.example.zhiyongjin.musicplayer.LoadData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    public static final int TIMEOUT = 5000;
    public static final String GET = "GET";
    private String mUrl;
    private ImageInterface mImageInterface;
    private Context mContext;


    public LoadImage(String mUrl, ImageInterface mImageInterface, Context context) {
        this.mUrl = mUrl;
        this.mImageInterface = mImageInterface;
        this.mContext = context;
    }

    //Asynctask, other thread work
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected Bitmap doInBackground(String... mStrings) {

        InputStream inputStream = null;
        try {
            URL url = new URL(mUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(GET);
            httpURLConnection.setConnectTimeout(TIMEOUT);
            int responseCode = httpURLConnection.getResponseCode();
            inputStream = null;
            Bitmap bitmap = null;
            if (responseCode == 200) {
                inputStream = httpURLConnection.getInputStream();
//                byte[] b = new byte[1024];
                bitmap = BitmapFactory.decodeStream(inputStream);

            }
            return bitmap;

        } catch (IOException mE) {
            mE.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException mE) {
                    mE.printStackTrace();
                }
            }
        }
        return null;
    }

    //update UI
    @Override
    protected void onPostExecute(Bitmap mBitmap) {
        super.onPostExecute(mBitmap);
        mImageInterface.imageInterface(mBitmap);
    }

    public interface ImageInterface {
        void imageInterface(Bitmap mBitmap);
    }
}
