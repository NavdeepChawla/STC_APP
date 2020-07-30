package com.mstc.mstcapp.adapter.highlights;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.highlights.GithubObject;

import java.util.List;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.myViewHolder> {

    private Context mContext2;
    private List<GithubObject> mData2;
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;

    public GithubAdapter(Context mContext2, List<GithubObject> mData2) {
        this.mContext2 = mContext2;
        this.mData2 = mData2;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v2;
        v2= LayoutInflater.from(mContext2).inflate(R.layout.item_github, parent, false);
        myViewHolder vHolder2 = new myViewHolder(v2);
        return vHolder2;
    }



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        holder.githubProj_link.setText(mData2.get(position).getLink());
        holder.githubProj_title.setText(mData2.get(position).getTitle());
        holder.githubProj_title_second.setText(mData2.get(position).getTitle());

        holder.githubProj_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=mData2.get(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(link)));
                mContext2.startActivity(intent);
            }
        });
        final boolean isExpanded = position==mExpandedPosition;
        holder.githubProj_link.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.githubProj_title_second.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.githubProj_title.setVisibility(!isExpanded?View.VISIBLE:View.GONE);
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
        return mData2.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        private TextView githubProj_link,githubProj_title,githubProj_title_second;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            githubProj_link = (TextView) itemView.findViewById(R.id.tv_repo_link);
            githubProj_title = (TextView) itemView.findViewById(R.id.tv_github_title);
            githubProj_title_second=itemView.findViewById(R.id.tv_github_title_full);

            githubProj_title_second.setVisibility(View.GONE);
            githubProj_link.setVisibility(View.GONE);
        }

    }

}

