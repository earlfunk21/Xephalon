package com.morax.xephalon.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.morax.xephalon.DocumentationActivity;
import com.morax.xephalon.R;
import com.morax.xephalon.model.DocsModel;

import java.util.List;

public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.ViewHolder> {
    private Context context;
    private List<DocsModel> docsModelList;

    public DocsAdapter(Context context, List<DocsModel> docsModelList) {
        this.context = context;
        this.docsModelList = docsModelList;
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
        DocsModel docsModel = docsModelList.get(position);
        String title = docsModel.getTitle();
        String docs = docsModel.getDocs();
        int thumbnail = docsModel.getThumbnail();

        holder.getTvTitle().setText(title);
        holder.getTvDocs().setText(docs);
        holder.getIvThumbnail().setImageResource(thumbnail);
        holder.getDocsItemLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DocumentationActivity.class);
                intent.putExtra("model", docsModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return docsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivThumbnail;
        private TextView tvTitle, tvDocs;
        private LinearLayout docsItemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDocs = itemView.findViewById(R.id.tv_docs);
            docsItemLayout = itemView.findViewById(R.id.docs_item_layout);
        }

        public ImageView getIvThumbnail() {
            return ivThumbnail;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvDocs() {
            return tvDocs;
        }

        public LinearLayout getDocsItemLayout() {
            return docsItemLayout;
        }
    }
}
