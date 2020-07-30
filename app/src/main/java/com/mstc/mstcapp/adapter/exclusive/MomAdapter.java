package com.mstc.mstcapp.adapter.exclusive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.exclusive.MomObject;

import java.util.List;

public class MomAdapter extends RecyclerView.Adapter <MomAdapter.MomView> {
    private List <MomObject> momObjects_list;
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;

    public MomAdapter(List<MomObject> momObjectList) {
        momObjects_list=momObjectList;
    }

    @NonNull
    @Override
    public MomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mom,parent,false);
        return new MomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MomView holder, final int position) {
        holder.momTitle.setText(momObjects_list.get(position).getMomTitle());
        holder.momContent.setText(momObjects_list.get(position).getMomContent());
        final boolean isExpanded = position==mExpandedPosition;
        holder.momContent.setVisibility(isExpanded?View.VISIBLE:View.GONE);

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
        return momObjects_list.size();
    }

    public static class MomView extends RecyclerView.ViewHolder{
        TextView momTitle,momContent;
        public MomView(@NonNull View itemView) {
            super(itemView);
            momTitle=itemView.findViewById(R.id.mom_title);
            momContent=itemView.findViewById(R.id.mom_content);
            momContent.setVisibility(View.GONE);
        }
    }
}
