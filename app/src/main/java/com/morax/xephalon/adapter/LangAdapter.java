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

import com.morax.xephalon.DocumentationActivity;
import com.morax.xephalon.LanguageActivity;
import com.morax.xephalon.R;
import com.morax.xephalon.model.Documentation;
import com.morax.xephalon.model.Language;

import java.util.List;

import io.noties.markwon.Markwon;

public class LangAdapter extends RecyclerView.Adapter<LangAdapter.ViewHolder> {
    private Context context;
    private List<Language> langList;

    public LangAdapter(Context context, List<Language> langList) {
        this.context = context;
        this.langList = langList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.lang_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Language lang = langList.get(position);
        String langName = lang.getName();
        int thumbnail = lang.getLogo();
        holder.ivLangLogo.setImageResource(thumbnail);
        holder.tvLangName.setText(langName);
        holder.langItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LanguageActivity.class);
                intent.putExtra("model", lang);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return langList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivLangLogo;
        public TextView tvLangName;
        public CardView langItemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLangLogo = itemView.findViewById(R.id.iv_logo);
            tvLangName = itemView.findViewById(R.id.tv_lang_name);
            langItemLayout = itemView.findViewById(R.id.lang_item_layout);
        }

    }
}
