package com.mstc.mstcapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.FeedObject;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter <FeedAdapter.FeedView> {
    List<FeedObject> mData_feed;
    Context mContext;
    int position;

    public FeedAdapter(List<FeedObject> mData, Context context) {
        mData_feed = mData;
        mContext = context;
        position=0;
    }

    @NonNull
    @Override
    public FeedView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //fills the view with card view layout made (item_feed)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedView(view);
    }
    //clickable to browser with image

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final FeedView holder, final int position) {
        //used to set everything in the feed page
        holder.desc_textView.setText(mData_feed.get(position).getFeedDesc());
        holder.title_textView.setText(mData_feed.get(position).getFeedTitle());
        this.position=position;
        new Thread(new Runnable() {
            @Override
            public void run() {
                holder.feed_imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= mData_feed.get(position).getFeedPicture();
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        holder.feed_imageView.setImageBitmap(picture);                    }
                });
            }
        }).start();
        //holder.feed_imageView.setImageResource(mData_feed.get(position).getFeedPicture());
        holder.link_textView.setText(mData_feed.get(position).getFeedLink());
        holder.link_textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                SpannableString ss = new SpannableString(holder.link_textView.getText().toString());
                ss.setSpan(new UnderlineSpan(),0,ss.length(),0);
                ss.setSpan(new StyleSpan(Typeface.BOLD), 0, ss.length(), 0);
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    holder.link_textView.setText(ss);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {

                    holder.link_textView.setText(ss.toString());

                }
                return false;
            }
        });
        holder.link_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = mData_feed.get(position).getFeedLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(link)));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData_feed.size();
    }



    public static class FeedView extends RecyclerView.ViewHolder {

        ImageView feed_imageView;
        TextView desc_textView, title_textView, link_textView;

        public FeedView(@NonNull View itemView) {
            super(itemView);
            feed_imageView = (ImageView) itemView.findViewById(R.id.feed_image);
            desc_textView = itemView.findViewById(R.id.feed_description);
            title_textView = itemView.findViewById(R.id.feed_title);
            link_textView = itemView.findViewById(R.id.feed_link);
        }
    }
}
