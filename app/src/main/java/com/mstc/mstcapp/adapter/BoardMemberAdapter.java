package com.mstc.mstcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.explore.BoardMemberModel;

import java.util.List;

public class BoardMemberAdapter extends RecyclerView.Adapter<BoardMemberAdapter.BoardViewHolder> {

    private final Context context;
    private List<BoardMemberModel> list;

    public BoardMemberAdapter(Context context, List<BoardMemberModel> items) {
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_board_member, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BoardViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.position.setText(list.get(position).getPosition());
        holder.phrase.setText(list.get(position).getPhrase());
        if (position % 3 == 0)
            holder.image.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryRed));
        else if (position % 3 == 1)
            holder.image.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryBlue));
        else
            holder.image.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTertiaryYellow));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<BoardMemberModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView name;
        public final TextView position;
        public final TextView phrase;

        public BoardViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            position = view.findViewById(R.id.position);
            phrase = view.findViewById(R.id.phrase);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + position.getText() + "'";
        }
    }
}