package com.mstc.mstcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.ResourceModel;

import java.util.List;


public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourcesView> {
    private final List<ResourceModel> domains;
    private final Context context;

    public ResourceAdapter(Context mContext, List<ResourceModel> domain) {
        domains = domain;
        context = mContext;//constructor used for initialising the list in the the view
    }

    @NonNull
    @Override
    public ResourcesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //fills the view with card view layout made (item_resources_domain)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource, parent, false);
        return new ResourcesView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResourcesView holder, final int position) {
        //domain title and card
        holder.title.setText(domains.get(position).getTitle());
        new Thread(() -> holder.image.post(() -> Glide.with(context).load(domains.get(position).getImage()).into(holder.image))).start();

    }

    @Override
    public int getItemCount() {
        return domains.size(); //size of the list
    }

    public static class ResourcesView extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public ResourcesView(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }
}
