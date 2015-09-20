package com.example.shika.matarialdesign.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shika.matarialdesign.Utils.InformationList;
import com.example.shika.matarialdesign.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by shika on 8/11/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    Context context;
    private List<InformationList> listItem= Collections.emptyList();
    public RecyclerViewAdapter(Context context , List<InformationList> data){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        listItem=data;
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View mView=layoutInflater.inflate(R.layout.list_drawer_item , viewGroup , false);
        ViewHolder holder=new ViewHolder(mView);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int i) {

        viewHolder.mTextView.setText(listItem.get(i).mTextView);
        viewHolder.mImageView.setImageResource(listItem.get(i).ImageViewId);

    }
    private void delete(int position){
        listItem.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.reyclerimageview);
            mTextView = (TextView) itemView.findViewById(R.id.recyclertextview);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            delete(getPosition());
            //context.startActivity(new Intent(context , QrCodeActivity.class));
        }
    }
}
