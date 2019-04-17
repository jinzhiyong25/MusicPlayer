package com.example.zhiyongjin.musicplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.zhiyongjin.musicplayer.JsonData.Music;
import com.example.zhiyongjin.musicplayer.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Music.ResultsBean> mDataList;

    public ListViewAdapter(Context context, List<Music.ResultsBean> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_musiclist, null);
            viewHolder.mImageView = convertView.findViewById(R.id.imageview_song_item);
            viewHolder.mSongName = convertView.findViewById(R.id.textview_songname);
            viewHolder.mSingerName = convertView.findViewById(R.id.textview_singername);
            viewHolder.mCollectionName = convertView.findViewById(R.id.textivew_albumname);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.mSongName.setText(mDataList.get(position).getTrackName());
        viewHolder.mSingerName.setText(mDataList.get(position).getArtistName());
        viewHolder.mCollectionName.setText(mDataList.get(position).getCollectionName());
        Glide.with(mContext).load(mDataList.get(position).getArtworkUrl100()).override(80, 80).placeholder(R.mipmap.ic_launcher).into(viewHolder.mImageView);

        return convertView;
    }

    private class ViewHolder {
        ImageView mImageView;
        TextView mSongName;
        TextView mSingerName;
        TextView mCollectionName;
    }
}