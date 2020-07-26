package com.mstc.mstcapp.adapter.resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;

import org.w3c.dom.Text;

import java.util.List;

public class ResourcesFolderAdapter extends RecyclerView.Adapter<ResourcesFolderAdapter.ResourcesFolderView> {
    List <ResourcesFolderObject> resourcesFolderObjects_list;
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;

    public ResourcesFolderAdapter(List<ResourcesFolderObject> resourcesFolderObjectsList) {
        resourcesFolderObjects_list=resourcesFolderObjectsList;
    }

    @NonNull
    @Override
    public ResourcesFolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resourcesfolder,parent,false);
        return new ResourcesFolderView(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ResourcesFolderView holder, final int position) {
        holder.resourcesfolderTitle.setText(resourcesFolderObjects_list.get(position).getResourcesfolderTitle());
        holder.resourcefolderTitleSecond.setText(resourcesFolderObjects_list.get(position).getResourcesfolderTitle());
        holder.resourcesfolderLink.setText(resourcesFolderObjects_list.get(position).getResourcefolderLink());
        holder.resourcefolderDesc.setText(resourcesFolderObjects_list.get(position).getResourcefolderDesc());
        final boolean isExpanded = position==mExpandedPosition;
        holder.resourcesfolderTitle.setVisibility(!isExpanded?View.VISIBLE:View.GONE);
        holder.resourcefolderTitleSecond.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.resourcefolderDesc.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.resourcesfolderLink.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourcesFolderObjects_list.size();
    }

    public class ResourcesFolderView  extends RecyclerView.ViewHolder {
         public TextView resourcesfolderTitle,resourcesfolderLink,resourcefolderDesc,resourcefolderTitleSecond;

        public ResourcesFolderView(@NonNull View itemView) {
            super(itemView);
            resourcesfolderTitle=itemView.findViewById(R.id.resourcefolder_title);
            resourcesfolderLink=itemView.findViewById(R.id.resourcefolder_link);
            resourcefolderDesc=itemView.findViewById(R.id.resourcefolder_desc);
            resourcefolderTitleSecond=itemView.findViewById(R.id.resourcefolder_title_second);

            resourcefolderTitleSecond.setVisibility(View.GONE);
            resourcefolderDesc.setVisibility(View.GONE);
            resourcesfolderLink.setVisibility(View.GONE);
        }
    }
}
