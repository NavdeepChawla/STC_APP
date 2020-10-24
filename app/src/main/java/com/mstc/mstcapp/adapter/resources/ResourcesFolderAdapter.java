package com.mstc.mstcapp.adapter.resources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;

import java.util.Collections;
import java.util.List;

public class ResourcesFolderAdapter extends RecyclerView.Adapter<ResourcesFolderAdapter.ResourcesFolderView> {

    private List <ResourcesFolderObject> resourcesFolderObjects_list;
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;
    private Context context;

    public ResourcesFolderAdapter(List<ResourcesFolderObject> resourcesFolderObjectsList,Context context1) {
        resourcesFolderObjects_list=resourcesFolderObjectsList;
        Collections.reverse(resourcesFolderObjects_list);
        context=context1;
    }

    @NonNull
    @Override
    public ResourcesFolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resourcesfolder,parent,false);
        return new ResourcesFolderView(view);

    }

    @SuppressLint("ClickableViewAccessibility")
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
        holder.resourcesfolderLink.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                SpannableString ss = new SpannableString(holder.resourcesfolderLink.getText().toString());
                ss.setSpan(new UnderlineSpan(),0,ss.length(),0);
                ss.setSpan(new StyleSpan(Typeface.BOLD), 0, ss.length(), 0);
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    holder.resourcesfolderLink.setText(ss);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {

                    holder.resourcesfolderLink.setText(ss.toString());

                }
                return false;
            }
        });
        holder.resourcesfolderLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=resourcesFolderObjects_list.get(position).getResourcefolderLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(link)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourcesFolderObjects_list.size();
    }

    public static class ResourcesFolderView  extends RecyclerView.ViewHolder {
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
