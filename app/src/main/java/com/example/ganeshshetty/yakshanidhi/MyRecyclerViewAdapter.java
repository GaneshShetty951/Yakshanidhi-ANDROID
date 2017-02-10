package com.example.ganeshshetty.yakshanidhi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Nilanchala Panigrahy on 10/25/16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<Mela_class> melaclassList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<Mela_class> melaclassList) {
        this.melaclassList = melaclassList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Mela_class melaclass = melaclassList.get(i);

        //Download image using picasso library
        if (!TextUtils.isEmpty(melaclass.getThumbnail())) {
            Picasso.with(mContext).load(melaclass.getThumbnail())
                    .error(R.drawable.flash)
                    .placeholder(R.drawable.flash)
                    .into(customViewHolder.imageView);
        }

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(melaclass.getTitle()));


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(melaclass);
            }
        };
        customViewHolder.imageView.setOnClickListener(listener);
        customViewHolder.textView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != melaclassList ? melaclassList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }


    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}