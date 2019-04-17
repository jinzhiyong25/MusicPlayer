package com.example.zhiyongjin.musicplayer.LoadData;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.zhiyongjin.musicplayer.JsonData.Music;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class LoadData extends AsyncTask<String, Void, String> {

    private Context mContext;
    private InputStream mInputStream;
    private DataInterface mDataInterface;
    private List<Music.ResultsBean> mMusicList;

    public LoadData(Context mContext, DataInterface mDataInterface) {
        this.mContext = mContext;
        this.mDataInterface = mDataInterface;
    }

    @Override
    protected String doInBackground(String... mStrings) {
        //Load data from JSONDATA.txt file.
        try {
            mInputStream = mContext.getAssets().open("JSONDATA.txt");
            //Get file's byte length
            int length = mInputStream.available();
            //Create byte list
            byte[] buffer = new byte[length];
            //Write result to buffer:
            mInputStream.read(buffer);
            mInputStream.close();
            String result = new String(buffer);
            return result;

        } catch (IOException mE) {
            mE.printStackTrace();
            Log.e(TAG, "Error");
        }

        return null;
    }

    //Update UI
    @Override
    protected void onPostExecute(String mS) {
        super.onPostExecute(mS);
        DataParse(mS);
    }

    /**
     * Parse Json LoadData
     *
     * @param result
     * @return
     */

    //Parse Json data
    private List<Music.ResultsBean> DataParse(String result) {
        mMusicList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int position = 0; position < jsonArray.length(); position++) {

                JSONObject object = jsonArray.getJSONObject(position);
                Music.ResultsBean resultsBean = new Music.ResultsBean();

                //Singer Name
                resultsBean.setArtistName(object.getString("artistName"));
                //Music Name
                resultsBean.setTrackName(object.getString("trackName"));
                //Albums Name
                resultsBean.setCollectionName(object.getString("collectionCensoredName"));
                //Image URL
                resultsBean.setArtworkUrl100(object.getString("artworkUrl100"));
                //Release Data
                resultsBean.setReleaseDate(object.getString("releaseDate"));
                //Song Price
                resultsBean.setTrackPrice(object.getDouble("trackPrice"));
                //Country
                resultsBean.setCountry(object.getString("country"));
                //Currency
                resultsBean.setCurrency(object.getString("currency"));
                //Music
                resultsBean.setPreviewUrl(object.getString("previewUrl"));

                mMusicList.add(resultsBean);
            }
            mDataInterface.dataInterface(mMusicList);

        } catch (JSONException mE) {
            mE.printStackTrace();
        }


        return null;
    }

    public interface DataInterface {
        void dataInterface(List<Music.ResultsBean> mList);
    }

}
