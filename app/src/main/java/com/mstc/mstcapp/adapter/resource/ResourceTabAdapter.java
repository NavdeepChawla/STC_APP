package com.mstc.mstcapp.adapter.resource;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.resources.ResourceModel;
import com.mstc.mstcapp.util.Constants;

import java.util.List;

public class ResourceTabAdapter extends RecyclerView.Adapter<ResourceTabAdapter.ResourceViewHolder> {

    private final Context context;
    private List<ResourceModel> list;

    public ResourceTabAdapter(Context context, List<ResourceModel> items) {
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resource, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ResourceViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());
        holder.share.setOnClickListener(v -> shareResource(list.get(position).getDescription(), list.get(position).getLink(), list.get(position).getDomain()));
    }

    private void shareResource(String description, String link, String domain) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, description + "\n" + link + "\n\n To get more info about " + domain + " download the STC App today\n" + Constants.PLAY_STORE_URL);
        context.startActivity(Intent.createChooser(intent, "Share Using"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ResourceModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ResourceViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView description;
        public final ImageButton share;
        public ResourceViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            share = view.findViewById(R.id.share);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + description.getText() + "'";
        }
    }
}