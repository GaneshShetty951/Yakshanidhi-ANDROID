package com.example.ganeshshetty.yakshanidhi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.itemclicklistener.ArtistOnItemClickListener;
import com.example.ganeshshetty.yakshanidhi.model.Artist_class;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ganesh Shetty on 16-02-2017.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.CustomViewHolder>{
    private List<Artist_class> artist_classList;
    private Context pContext;
    private ArtistOnItemClickListener artistOnItemClickListener;

    public ArtistAdapter(Context context, List<Artist_class> artist_classList) {
        this.pContext = context;
        this.artist_classList = artist_classList;
    }

    public ArtistOnItemClickListener getArtistOnItemClickListener() {
        return artistOnItemClickListener;
    }

    public void setArtistOnItemClickListener(ArtistOnItemClickListener artistOnItemClickListener) {
        this.artistOnItemClickListener = artistOnItemClickListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_artist_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Artist_class artist_class=artist_classList.get(position);

        holder.nameView.setText(Html.fromHtml(artist_class.getFirst_name()));
        if (!TextUtils.isEmpty(artist_class.getPic())) {
            Picasso.with(pContext).load(artist_class.getPic())
                    .error(R.drawable.flash)
                    .placeholder(R.drawable.flash)
                    .into(holder.picView);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                artistOnItemClickListener.onItemClick(artist_class);
            }
        };

        holder.nameView.setOnClickListener(listener);
        holder.picView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return artist_classList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView nameView;
        protected ImageView picView;
        public CustomViewHolder(View view) {
            super(view);
            this.nameView=(TextView) view.findViewById(R.id.name);
            this.picView = (ImageView) view.findViewById(R.id.pic);
        }

    }
}
