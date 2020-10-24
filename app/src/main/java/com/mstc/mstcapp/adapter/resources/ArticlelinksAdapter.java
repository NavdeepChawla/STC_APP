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
import com.mstc.mstcapp.model.resources.ArticleLinksObject;

import java.util.Collections;
import java.util.List;

public class ArticlelinksAdapter extends  RecyclerView.Adapter<ArticlelinksAdapter.ArticlelinkView> {

    private List<ArticleLinksObject> articleLinksObject_list;
    private Context articlelinkContext;
    public static int mExpandedPosition=-1;
    public static int previousExpandedPosition=-1;

    public ArticlelinksAdapter(List<ArticleLinksObject> articleLinksObjectList, Context context) {
        articleLinksObject_list=articleLinksObjectList;
        Collections.reverse(articleLinksObject_list);
        articlelinkContext=context;
    }

    @NonNull
    @Override
    public ArticlelinkView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articlelinks,parent,false);
        return new ArticlelinkView(view);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void onBindViewHolder(@NonNull ArticlelinkView holder, final int position) {
        holder.articlelinksTitle.setText(articleLinksObject_list.get(position).getArticlelinksTitle());
        holder.articleslinkTitleSecond.setText(articleLinksObject_list.get(position).getArticlelinksTitle());
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
        holder.articlelinksTitle.setVisibility(!isExpanded?View.VISIBLE:View.GONE);
        holder.articleslinkTitleSecond.setVisibility(isExpanded?View.VISIBLE:View.GONE);
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
        holder.articlelinksLink.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                SpannableString ss = new SpannableString(holder.articlelinksLink.getText().toString());
                ss.setSpan(new UnderlineSpan(),0,ss.length(),0);
                ss.setSpan(new StyleSpan(Typeface.BOLD), 0, ss.length(), 0);
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    holder.articlelinksLink.setText(ss);
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {

                    holder.articlelinksLink.setText(ss.toString());

                }
                return false;
            }
        });
        holder.articlelinksLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=articleLinksObject_list.get(position).getArticlelinksLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse(link)));
                articlelinkContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleLinksObject_list.size();
    }

    public class ArticlelinkView extends RecyclerView.ViewHolder{
        TextView articlelinksTitle,articlelinksLink,articleslinkDesc,articleslinkTitleSecond;
        public ArticlelinkView(@NonNull View itemView) {
            super(itemView);
            articlelinksTitle=itemView.findViewById(R.id.articlelink_title);
            articlelinksLink=itemView.findViewById(R.id.articlelink_link);
            articleslinkDesc=itemView.findViewById(R.id.articlelink_desc);
            articleslinkTitleSecond=itemView.findViewById(R.id.articlelink_title_second);

            articleslinkTitleSecond.setVisibility(View.GONE);
            articleslinkDesc.setVisibility(View.GONE);
            articlelinksLink.setVisibility(View.GONE);
        }
    }
}
