package com.example.shika.matarialdesign.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shika.matarialdesign.Utils.InformationList;
import com.example.shika.matarialdesign.R;
import com.example.shika.matarialdesign.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private DrawerLayout drawerLayout;
    public static final String KEY_USER_LEARNED="user_learned_drawer";

    RecyclerViewAdapter adapter;

    private boolean mUserDrawerLearned;
    private boolean mFromSavedinstanceState;

    private View container;

    private RecyclerView recyclerView;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserDrawerLearned=Boolean.valueOf(ReadFromPreference(getActivity() , KEY_USER_LEARNED,"false"));
        if (savedInstanceState!=null){
            mFromSavedinstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerview);
        adapter  = new RecyclerViewAdapter(getActivity() , getdata());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    public static List<InformationList> getdata(){
        int[] iconId = {R.mipmap.ic_launcher , R.mipmap.ic_launcher , R.mipmap.ic_launcher , R.mipmap.ic_launcher ,};
        String[] itemText={"Message" , "FaceBook" , "Google" , "Youtube"};
        List<InformationList> mList=new ArrayList<>();
        for (int i = 0; i <100 ; i++) {
            InformationList info=new InformationList();
            info.ImageViewId=iconId[i%iconId.length];
            info.mTextView=itemText[i%itemText.length];
            mList.add(info);
        }
        return mList;
    }


    public void setUp(int id,DrawerLayout drawer, final Toolbar toolbar) {
        container=getActivity().findViewById(id);

        drawerLayout = drawer;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserDrawerLearned){
                    mUserDrawerLearned=true;
                    SaveToPreference(getActivity() , KEY_USER_LEARNED ,mUserDrawerLearned+"");

                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset<.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }
        };

        if (!mUserDrawerLearned && !mFromSavedinstanceState){
            drawerLayout.openDrawer(container);
        }
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {

                actionBarDrawerToggle.syncState();
            }
        });

    }

    private static void SaveToPreference(Context context, String ValueName, String Value) {
        SharedPreferences shared = context.getSharedPreferences("test_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(ValueName, Value);

        editor.apply();
    }
    private static String ReadFromPreference(Context context, String ValueName, String DefaultValue){
        SharedPreferences shared = context.getSharedPreferences("test_pref", Context.MODE_PRIVATE);
        return shared.getString(ValueName,DefaultValue);
    }

}
