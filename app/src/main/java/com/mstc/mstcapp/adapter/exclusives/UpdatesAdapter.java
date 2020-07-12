package com.mstc.mstcapp.adapter.exclusives;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.exclusives.UpdatesObject;

import java.util.ArrayList;
import java.util.List;

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.UpdatesView> {

    List<UpdatesObject> updatesObjects_list=new ArrayList<>();
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;

    public UpdatesAdapter(List<UpdatesObject> updatesObjects_list) {
        this.updatesObjects_list = updatesObjects_list;
    }

    @NonNull
    @Override
    public UpdatesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_updates,parent,false);
        return new UpdatesView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdatesView holder, final int position) {
        holder.updatesTitle.setText(updatesObjects_list.get(position).getUpdatesTitle());
        holder.updatesContent.setText(updatesObjects_list.get(position).getUpdatesContent());
        final boolean isExpanded = position==mExpandedPosition;
        holder.updatesContent.setVisibility(isExpanded?View.VISIBLE:View.GONE);
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
        return updatesObjects_list.size();
    }

    public class UpdatesView extends RecyclerView.ViewHolder{
        ImageView updatesDatebg;
        TextView updatesTitle,updatesContent,updatesDate,updatesMonth;
        public UpdatesView(@NonNull View itemView) {
            super(itemView);

            updatesTitle=itemView.findViewById(R.id.updates_title);
            updatesContent=itemView.findViewById(R.id.updates_content);
            updatesContent.setVisibility(View.GONE);
        }
    }
}
