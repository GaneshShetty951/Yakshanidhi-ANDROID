package com.example.ganeshshetty.yakshanidhi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganeshshetty.yakshanidhi.R;
import com.example.ganeshshetty.yakshanidhi.itemclicklistener.PrasanghaOnItemClickListener;
import com.example.ganeshshetty.yakshanidhi.model.Prasangha_class;

import java.util.List;

/**
 * Created by Ganesh Shetty on 15-02-2017.
 */

public class PrasanghaAdapter extends RecyclerView.Adapter<PrasanghaAdapter.CustomViewHolder> {
    private List<Prasangha_class> prasangha_list;
    private Context pContext;
    private PrasanghaOnItemClickListener prasanghaOnItemClickListener;
    public PrasanghaAdapter(Context context, List<Prasangha_class> prasangha_classList) {
        this.pContext=context;
        this.prasangha_list=prasangha_classList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_prasangha, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Prasangha_class prasangha_class=prasangha_list.get(position);

        holder.nameView.setText(Html.fromHtml(prasangha_class.getName()));
        holder.yearView.setText(Html.fromHtml(" "+prasangha_class.getYear()));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prasanghaOnItemClickListener.onItemClick(prasangha_class);
            }
        };

        holder.nameView.setOnClickListener(listener);
    }


    @Override
    public int getItemCount() {
        return (null != prasangha_list ? prasangha_list.size() : 0);
    }


    public void setPrasanghaOnItemClickListener(PrasanghaOnItemClickListener prasanghaOnItemClickListener) {
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView nameView,yearView;

        public CustomViewHolder(View view) {
            super(view);
            this.nameView=(TextView) view.findViewById(R.id.name_prasangha);
            this.yearView = (TextView) view.findViewById(R.id.year_prasangha);
        }
        
    }

}
