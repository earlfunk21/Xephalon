package com.morax.xephalon.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.morax.xephalon.BookmarkActivity;
import com.morax.xephalon.DocumentationActivity;
import com.morax.xephalon.R;
import com.morax.xephalon.model.Bookmark;
import com.morax.xephalon.model.Documentation;

import java.util.List;

import io.noties.markwon.Markwon;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private Context context;
    private List<Bookmark> bookmarkList;

    public BookmarkAdapter(Context context, List<Bookmark> bookmarkList) {
        this.context = context;
        this.bookmarkList = bookmarkList;
    }

    public void setBookmarkList(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    public Bookmark getBookMarkAt(int position){
        return bookmarkList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.docs_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = bookmarkList.get(position);
        Documentation documentation = bookmark.documentation;
        String title = documentation.getTitle();
        String lang = documentation.getLang();
        int thumbnail = documentation.getThumbnail();

        holder.getTvTitle().setText(title);
        final Markwon markwon = Markwon.create(context);
        markwon.setMarkdown(holder.getTvMarkdown(), documentation.getMarkdown());
        holder.getIvThumbnail().setImageResource(thumbnail);
        holder.getTvLang().setText(lang);
        holder.getDocsItemLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DocumentationActivity.class);
                intent.putExtra("model", documentation);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivThumbnail;
        private TextView tvTitle, tvMarkdown, tvLang;
        private CardView docsItemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMarkdown = itemView.findViewById(R.id.tv_markdown);
            tvLang = itemView.findViewById(R.id.tv_lang);
            docsItemLayout = itemView.findViewById(R.id.docs_item_layout);
        }

        public ImageView getIvThumbnail() {
            return ivThumbnail;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvMarkdown() {
            return tvMarkdown;
        }

        public TextView getTvLang() {
            return tvLang;
        }

        public CardView getDocsItemLayout() {
            return docsItemLayout;
        }
    }
}
