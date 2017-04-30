package com.example.ganeshshetty.yakshanidhi.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.details.ShowActivity;
import com.example.ganeshshetty.yakshanidhi.model.Show_class;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Ganesh Shetty on 26-04-2017.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<Show_class> shows;

    public ShowAdapter(Context context, ArrayList<Show_class> shows) {
        this.context = context;
        this.shows = shows;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_card, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final Show_class show=shows.get(position);
        holder.mela_name.setText(show.getMela_name().toString());
        holder.prasangha_name.setText(show.getPrasangha_name().toString());
        Picasso.with(context).load(context.getString(R.string.url)+"/mela_images/"+show.getMela_pic()).placeholder(R.drawable.yakshanidhi).into(holder.mela_pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShowActivity.class);
                intent.putExtra("show",shows.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView mela_pic;
        TextView prasangha_name,mela_name;
        public CustomViewHolder(View itemView) {
            super(itemView);
            mela_name=(TextView)itemView.findViewById(R.id.name_mela);
            mela_pic=(ImageView)itemView.findViewById(R.id.mela_pic);
            prasangha_name=(TextView)itemView.findViewById(R.id.name_prasangha);
        }
    }
}
