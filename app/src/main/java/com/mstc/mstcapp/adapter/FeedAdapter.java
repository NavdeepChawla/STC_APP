package com.mstc.mstcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.FeedObject;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private final Context context;
    private List<FeedObject> mValues;

    public FeedAdapter(Context context, List<FeedObject> items) {
        mValues = items;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());
        holder.description.setText(mValues.get(position).getLink());

//        new Thread(() -> holder.imageView.post(() -> {
//            String pic = mValues.get(position).getImage();
//            byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
//            Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            holder.imageView.setImageBitmap(picture);
//        })).start();

        if (position % 3 == 0)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryBlue));
        else if (position % 3 == 1)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryRed));
        else
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryYellow));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setList(List<FeedObject> list) {
        this.mValues = list;
        notifyDataSetChanged();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView description;
        public final ImageView imageView;
        public final CardView cardView;
        public FeedObject mItem;

        public FeedViewHolder(View view) {
            super(view);
            mView = view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            imageView = view.findViewById(R.id.image);
            cardView = view.findViewById(R.id.cardView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + description.getText() + "'";
        }
    }
}