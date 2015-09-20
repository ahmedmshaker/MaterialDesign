package com.example.shika.matarialdesign.UI;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shika.matarialdesign.Extra.SortListener;
import com.example.shika.matarialdesign.R;
import com.example.shika.matarialdesign.adapters.SectionsPagerAdapter;
import com.example.shika.matarialdesign.animation.AnimationUtils;
import com.example.shika.matarialdesign.fragments.NavigationDrawerFragment;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class MainActivity extends ActionBarActivity implements MaterialTabListener, View.OnClickListener {


    MaterialTabHost tabHost;
    ViewPager mViewPager;
    Toolbar toolbar;
    SectionsPagerAdapter adapter;

    String TAG_BUTTON1 = "sortname";
    String TAG_BUTTON2 = "sortdat";
    String TAG_BUTTON3 = "sortrating";

    private ViewGroup mContainerToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentnavigationdrawer);
        navigationDrawerFragment.setUp(R.id.fragmentnavigationdrawer, (DrawerLayout) findViewById(R.id.drawerlayout), toolbar);


        //startActivity(new Intent(MainActivity.this , QrCodeActivity.class));
        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.mpager);
        adapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(tabHost.newTab()
                    .setIcon(getResources().getDrawable(adapter.getIcon(i)))
                    .setTabListener(this));
        }


        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.selector_button_red)


                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));


        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.mipmap.ic_tab_friends);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.mipmap.ic_tab_friends);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();


        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(R.mipmap.ic_tab_inbox);
        SubActionButton button3 = itemBuilder.setContentView(itemIcon).build();


        button1.setTag(TAG_BUTTON1);
        button2.setTag(TAG_BUTTON2);
        button3.setTag(TAG_BUTTON3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();



        AnimationUtils.animateToolbarDroppingDown(mContainerToolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this , ActivityRecylerAnimators.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) adapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        if (fragment instanceof SortListener) {
            if (v.getTag().equals(TAG_BUTTON1)) {
                //call the sort by name method on any Fragment that implements sortlistener
                ((SortListener) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_BUTTON2)) {
                //call the sort by date method on any Fragment that implements sortlistener
                ((SortListener) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_BUTTON3)) {
                //call the sort by ratings method on any Fragment that implements sortlistener
                ((SortListener) fragment).onSortByRating();

            }

        }


    }
}
