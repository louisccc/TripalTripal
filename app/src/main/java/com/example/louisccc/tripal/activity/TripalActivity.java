package com.example.louisccc.tripal.activity;

/**
 * Created by louisccc on 16/01/03.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.fragment.CategoriesFragment;
import com.example.louisccc.tripal.fragment.DashBoardFragment;
import com.example.louisccc.tripal.fragment.FriendsFragment;
import com.example.louisccc.tripal.fragment.HelpFragment;
import com.example.louisccc.tripal.fragment.SettingsFragment;
import com.example.louisccc.tripal.model.DBManager;
import com.example.louisccc.tripal.utility.MenuItemsAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.sql.SQLException;

public class TripalActivity extends FragmentActivity{
    private static int mTripalActivityResSrcId = R.layout.main;
    private static int mTripalActivityMenuItemResSrcId = R.layout.slidingmenu_list_item;

    private SlidingMenu mMenu;
    private ListView mMenuListView;
    private MenuItemsAdapter mMenuListViewAdapter;
    private String[] slidingMenuItems = {"Tripal Dashboard", "Friends", "category", "settings", "help", "Log out"};
    private FragmentTabHost tabHost;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mTripalActivityResSrcId);

        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("Dashboard").setIndicator("Dashboard"), DashBoardFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Friends").setIndicator("Friends"), FriendsFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Category").setIndicator("Category"), CategoriesFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Settings").setIndicator("Settings"), SettingsFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("Help").setIndicator("Help"), HelpFragment.class, null);

        mMenu = new SlidingMenu(this);
        mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mMenu.setShadowWidth(15);
        mMenu.setBehindOffset(90);
        mMenu.setFadeDegree(0.35f);
        mMenu.setMenu(R.layout.menu_frame);
        mMenuListView = (ListView) (mMenu.getMenu().findViewById(R.id.menu_list));
        mMenuListViewAdapter = new MenuItemsAdapter(this, mTripalActivityMenuItemResSrcId, slidingMenuItems);
        mMenuListView.setAdapter(mMenuListViewAdapter);
        mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mMenu.showContent();
                if (position == slidingMenuItems.length) { // logout
                } else if (position == slidingMenuItems.length - 1) { // help
                    exportDB(TripalActivity.this);
                } else {
                    mMenuListView.setSelection(position);
                    tabHost.setCurrentTab(position);
                }
            }
        });
        mMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_tripal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenu.toggle();
                break;
            case R.id.action_add_trip:
                getTempAlert(TripalActivity.this).show();
                break;
            case R.id.action_add_friend:
                getTempAlert(TripalActivity.this).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenu.isMenuShowing()) {
            mMenu.showContent();
        } else {
            super.onBackPressed();
        }
    }

    private void exportDB(Context ctx) {
        DBManager db = new DBManager(ctx);
        try {
            db.open();
            db.exportDB();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AlertDialog.Builder getTempAlert(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
                .setMessage("小元 alert").setMessage("回來再做給你用")
                .setPositiveButton("爽", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setNegativeButton("不爽", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        return builder;
    }
}
