package com.example.shika.matarialdesign.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import static com.example.shika.matarialdesign.Extra.Keys.EndpointBoxOffice.*;

import com.example.shika.matarialdesign.Extra.MovieSorter;
import com.example.shika.matarialdesign.Extra.SortListener;
import com.example.shika.matarialdesign.MyApplication;
import com.example.shika.matarialdesign.Network.VolleySingleton;
import com.example.shika.matarialdesign.R;
import com.example.shika.matarialdesign.Utils.Movie;
import com.example.shika.matarialdesign.adapters.AdapterBoxOffice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BoxOfficeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BoxOfficeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoxOfficeFragment extends Fragment implements SortListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_MOVIES ="state_list" ;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private VolleySingleton volleySingleton;
    RequestQueue requestQueue;
    private OnFragmentInteractionListener mListener;
    public static final String Url_boxoffic = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey="+MyApplication.API_KEY_ROTTEN_TOMATOES;

    AdapterBoxOffice adapterBoxOffice;
    RecyclerView recyclerView;
    ArrayList<Movie> list=new ArrayList<>();

    DateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");

    MovieSorter mSorter=new MovieSorter();

    public static String getRequestUrl(int limit){
        return Url_boxoffic+"&limit="+limit;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoxOfficeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoxOfficeFragment newInstance(String param1, String param2) {
        BoxOfficeFragment fragment = new BoxOfficeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BoxOfficeFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        volleySingleton=VolleySingleton.getInstance();
        requestQueue=volleySingleton.getRequestQueue();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_box_office, container, false);
        recyclerView= (RecyclerView) v.findViewById(R.id.recyclerviewBoxOffice);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            //if this fragment starts after a rotation or configuration change, load the existing movies from a parcelable
            list = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            adapterBoxOffice=new AdapterBoxOffice(getActivity() ,list);
            recyclerView.setAdapter(adapterBoxOffice);
        } else {

            SendjsonRequest();
        }

        //adapterBoxOffice.setMovieList(list);



        //


        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    private void SendjsonRequest(){

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET ,getRequestUrl(25),null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                list=Reponse(jsonObject);
                adapterBoxOffice=new AdapterBoxOffice(getActivity() ,list);
                recyclerView.setAdapter(adapterBoxOffice);



                Toast.makeText(getActivity(), list.size()+"" ,Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity() , list.get(0).getTitle() , Toast.LENGTH_LONG).show();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(request);
    }
    private ArrayList<Movie> Reponse(JSONObject jsonObject){
        ArrayList<Movie> mlist=new ArrayList<>();
        if (jsonObject == null || jsonObject.length() == 0){
            return null;
        }else{
            try {
                JSONArray arrMovies=jsonObject.getJSONArray(KEY_MOVIES);
                for (int i = 0; i <arrMovies.length() ; i++) {
                    JSONObject jsonObject1=arrMovies.getJSONObject(i);
                    long id=jsonObject1.getLong(KEY_ID);
                    String title=jsonObject1.getString(KEY_TITLE);
                    JSONObject releaseDate=jsonObject1.getJSONObject(KEY_RELEASE_DATES);
                    String Theater=null;
                    if (releaseDate.has(KEY_THEATER)){
                        Theater=releaseDate.getString(KEY_THEATER);
                    }else {
                        Theater="Not Found";
                    }

                    JSONObject objectRating=jsonObject1.getJSONObject(KEY_RATINGS);
                    int AudienceScore=0;
                    if (objectRating.has(KEY_AUDIENCE_SCORE)){

                        AudienceScore=objectRating.getInt(KEY_AUDIENCE_SCORE);
                    }

                    String synopsis=jsonObject1.getString(KEY_SYNOPSIS);
                    JSONObject objectPosters=jsonObject1.getJSONObject(KEY_POSTERS);
                    String ThumbnailUrl=null;
                    if (objectPosters.has(KEY_THUMBNAIL)){
                        ThumbnailUrl=objectPosters.getString(KEY_THUMBNAIL);
                    }





                    Movie m=new Movie();
                    m.setId(id);
                    m.setTitle(title);
                    Date date=dateFormat.parse(Theater);
                    m.setReleaseDateTheater(date);
                    m.setAudienceScore(AudienceScore);
                    m.setSynopsis(synopsis);
                    m.setUrlThumbnail(ThumbnailUrl);
                    mlist.add(m);













                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return mlist;
        }
    }







    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the movie list to a parcelable prior to rotation or configuration change
        outState.putParcelableArrayList(STATE_MOVIES, list);
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onSortByName() {
        mSorter.sortMoviesByName(list);
        adapterBoxOffice.notifyDataSetChanged();

    }

    @Override
    public void onSortByDate() {

        mSorter.sortMoviesByDate(list);
        adapterBoxOffice.notifyDataSetChanged();
    }

    @Override
    public void onSortByRating() {

        mSorter.sortMoviesByRating(list);
        adapterBoxOffice.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
