package com.mstc.mstcapp.adapter.resources;

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
import com.mstc.mstcapp.model.resources.ArticleLinksObject;

import java.security.acl.LastOwnerException;
import java.util.List;

public class ArticlelinksAdapter extends  RecyclerView.Adapter<ArticlelinksAdapter.ArticlelinkView> {
    List<ArticleLinksObject> articleLinksObject_list;
    Context articlelinkContext;
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;

    public ArticlelinksAdapter(List<ArticleLinksObject> articleLinksObjectList, Context context) {
        articleLinksObject_list=articleLinksObjectList;
        articlelinkContext=context;
    }

    @NonNull
    @Override
    public ArticlelinkView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articlelinks,parent,false);
        return new ArticlelinkView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlelinkView holder, final int position) {
        holder.articlelinksTitle.setText(articleLinksObject_list.get(position).getArticlelinksTitle());
        holder.articlelinksLink.setText(articleLinksObject_list.get(position).getArticlelinksLink());
        holder.articleslinkDesc.setText(articleLinksObject_list.get(position).getArticlelinksDesc());
        holder.articlelinksLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=articleLinksObject_list.get(position).getArticlelinksLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(link)));
                articlelinkContext.startActivity(intent);
            }
        });
        final boolean isExpanded = position==mExpandedPosition;
        holder.articleslinkDesc.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.articlelinksLink.setVisibility(isExpanded?View.VISIBLE:View.GONE);
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
        return articleLinksObject_list.size();
    }

    public class ArticlelinkView extends RecyclerView.ViewHolder{
        TextView articlelinksTitle,articlelinksLink,articleslinkDesc;
        public ArticlelinkView(@NonNull View itemView) {
            super(itemView);
            articlelinksTitle=itemView.findViewById(R.id.articlelink_title);
            articlelinksLink=itemView.findViewById(R.id.articlelink_link);
            articleslinkDesc=itemView.findViewById(R.id.articlelink_desc);

            articleslinkDesc.setVisibility(View.GONE);
            articlelinksLink.setVisibility(View.GONE);
        }
    }
}
