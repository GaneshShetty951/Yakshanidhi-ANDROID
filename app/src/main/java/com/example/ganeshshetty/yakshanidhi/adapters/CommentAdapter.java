package com.example.ganeshshetty.yakshanidhi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganesh Shetty on 14-05-2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {
    private List<Comment> comments;
    private Context context;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Comment comment=comments.get(position);
        holder.nameText.setText(comment.getName());
        holder.timeText.setText(comment.getCommentedAt());
        holder.commentText.setText(comment.getCommentText());
    }

    public void add(ArrayList<Comment> datas){
        comments.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText,timeText,commentText;
        public CustomViewHolder(View itemView) {
            super(itemView);
            nameText=(TextView)itemView.findViewById(R.id.name);
            timeText=(TextView)itemView.findViewById(R.id.time);
            commentText=(TextView)itemView.findViewById(R.id.comment);
        }
    }
}
