package com.mstc.mstcapp.adapter.highlights;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.highlights.EventObject;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.myViewHolder> {

    Context mContext;
    List<EventObject> mData;

    public EventAdapter(Context mContext, List<EventObject> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.items_event,parent,false);
        myViewHolder vHolder=new myViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {

        holder.eventTitle.setText(mData.get(position).getEventTitle());
        holder.eventDesc.setText(mData.get(position).getEventDesc());
        new Thread(new Runnable() {
            @Override
            public void run() {
                holder.eventPicture.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(mContext).load(mData.get(position).getEventPicture()).into(holder.eventPicture);
                    }
                });
            }
        }).start();
        //holder.image_event.setImageResource(mData.get(position).getEventPicture());
        holder.eventLink.setText(mData.get(position).getEventLink());
        holder.eventLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=mData.get(position).getEventLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(link)));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        private TextView eventTitle,eventDesc,eventLink;
        private ImageView eventPicture;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle=(TextView)itemView.findViewById(R.id.eventTitle);
            eventDesc=(TextView)itemView.findViewById(R.id.eventDesc);
            eventPicture=(ImageView)itemView.findViewById(R.id.eventPicture);
            eventLink=(TextView)itemView.findViewById(R.id.eventLink);
        }

    }
}
