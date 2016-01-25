package com.example.louisccc.tripal;

/**
 * Created by louisccc on 16/01/03.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class TripalActivity extends FragmentActivity {

    @Override
    protected void onResume() {
        super.onResume();
    }


    private SlidingMenu menu;
    private FragmentTabHost tabHost;
    private ListView list;

    private String[] slidingMenuItems = {"Tripal", "account", "dashboard", "category", "settings", "help", "Log out"};
    private int[] resources = { R.drawable.tripal_menu_dashboard,
            R.drawable.tripal_menu_account,
            R.drawable.tripal_menu_category,
            R.drawable.tripal_menu_setting,
            R.drawable.tripal_menu_about,
            R.drawable.tripal_menu_sync,
            R.drawable.tripal_menu_sync
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidth(15);
        menu.setBehindOffset(90);
        menu.setFadeDegree(0.35f);
        menu.setMenu(R.layout.menu_frame);

        list = (ListView) (menu.getMenu().findViewById(R.id.menu_list));

        final LayoutInflater mVi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayAdapter<String> menus_string = new ArrayAdapter<String>(this, R.layout.slidingmenu_list_item, slidingMenuItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String item = getItem(position);
                convertView = mVi.inflate(R.layout.slidingmenu_list_item, null);
                ImageView src = (ImageView) convertView.findViewById(R.id.ItemSrc);
                TextView text = (TextView) convertView.findViewById(R.id.ItemText);
                src.setImageDrawable(ContextCompat.getDrawable(getContext(), resources[position]));
                text.setText(slidingMenuItems[position]);
                return convertView;
            }
        };

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                menu.showContent();
                list.setSelection(position);
                if (position == 0) {
                    Intent intent = new Intent(TripalActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                else {
                    tabHost.setCurrentTab(position);
                }

            }
        });
        list.setAdapter(menus_string);

        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        this.getActionBar().setDisplayHomeAsUpEnabled(true);
//        this.getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.banck_bar_background_grey));
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.getTabWidget().setVisibility(View.INVISIBLE);
        tabHost.addTab(tabHost.newTabSpec("Summary").setIndicator("Summary"), DashBoardFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Account").setIndicator("Account"), DashBoardFragment.class, null);
//        tabHost.addTab(tabHost.newTabSpec("Dashboard").setIndicator("Dashboard"), DashBoardFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Settings").setIndicator("Settings"), SettingsFragment.class, null);
//        tabHost.addTab(tabHost.newTabSpec("Help").setIndicator("Help"), DashBoardFragment.class, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                menu.toggle();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        if (menu.isMenuShowing()){
            menu.showContent();
        }
        else{
            super.onBackPressed();
        }
    }
}
