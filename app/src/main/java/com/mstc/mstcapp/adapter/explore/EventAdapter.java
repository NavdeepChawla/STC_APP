package com.mstc.mstcapp.adapter.explore;

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
import com.mstc.mstcapp.model.explore.EventObject;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private final Context context;
    private List<EventObject> list;

    public EventAdapter(Context context, List<EventObject> items) {
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());
        if (position % 3 == 0)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryRed));
        else if (position % 3 == 1)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryBlue));
        else
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryYellow));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<EventObject> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;
        public final ImageView image;
        public final TextView status;
        public final TextView title;
        public final TextView description;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            status = view.findViewById(R.id.status);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            cardView = view.findViewById(R.id.cardView);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + description.getText() + "'";
        }
    }
}