package com.example.shika.matarialdesign.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.shika.matarialdesign.Network.VolleySingleton;
import com.example.shika.matarialdesign.R;
import com.example.shika.matarialdesign.Utils.Movie;
import com.example.shika.matarialdesign.animation.AnimationUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by shika on 8/23/2015.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.mViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<Movie> moviesArrayList;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;
    private int mPreviousPosition = 0;


    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public AdapterBoxOffice(Context context , ArrayList<Movie> mlist){
        layoutInflater=LayoutInflater.from(context);
        volleySingleton=VolleySingleton.getInstance();
        imageLoader=volleySingleton.getImageLoader();
        moviesArrayList=mlist;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.item_box_office , parent , false);

        mViewHolder holder=new mViewHolder(view);

        return holder;
    }
  /*  public void setMovieList(ArrayList<Movies> mlist){
        moviesArrayList=mlist;
        notifyItemRangeChanged(0 , moviesArrayList.size());
    }*/

    @Override
    public void onBindViewHolder(final mViewHolder holder, int position) {

        Movie curentMovie=moviesArrayList.get(position);

        holder.title.setText(curentMovie.getTitle());
        String mDate=mFormatter.format(curentMovie.getReleaseDateTheater());
        holder.date.setText(mDate);
        holder.ratingBar.setRating(curentMovie.getAudienceScore()/20);

        if (position > mPreviousPosition) {
            AnimationUtils.animateSunblind(holder, true);
//            AnimationUtils.animateSunblind(holder, true);
//            AnimationUtils.animate1(holder, true);
           AnimationUtils.animate(holder,true);
        } else {
//            AnimationUtils.animateSunblind(holder, false);
//            AnimationUtils.animateSunblind(holder, false);
//            AnimationUtils.animate1(holder, false);
            AnimationUtils.animate(holder, false);
        }
        mPreviousPosition = position;

        String url=curentMovie.getUrlThumbnail();
        if (url !=null) {
            imageLoader.get(url , new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {

                    holder.urlThumbnail.setImageBitmap(imageContainer.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return 25;
    }

    static class mViewHolder extends RecyclerView.ViewHolder{
        private ImageView urlThumbnail;
        private TextView  title;
        private TextView  date;
        private RatingBar ratingBar;

        public mViewHolder(View itemView) {
            super(itemView);

            urlThumbnail=(ImageView) itemView.findViewById(R.id.urlThumbnail);
            title= (TextView) itemView.findViewById(R.id.movieTitle);
            date=(TextView) itemView.findViewById(R.id.movieDate);
            ratingBar=(RatingBar) itemView.findViewById(R.id.ratingBar);



        }
    }
}
