package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriTrip;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class MenuItemsAdapter extends ArrayAdapter<String> {
    private String[] strings;
    private int mResrc_id;
    private LayoutInflater mVi;

    private int[] resources = {
            R.drawable.tripal_menu_dashboard,
            R.drawable.tripal_menu_account,
            R.drawable.tripal_menu_category,
            R.drawable.tripal_menu_setting,
            R.drawable.tripal_menu_about,
            R.drawable.tripal_menu_sync,
    };

    public MenuItemsAdapter(Context ctx, int resrc_id, String[] strings) {
        super(ctx, resrc_id ,strings);
        this.strings = strings;
        this.mResrc_id = resrc_id;
        this.mVi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Assert.assertTrue( strings.length == resources.length);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mVi.inflate(mResrc_id, null);
        ImageView src = (ImageView) convertView.findViewById(R.id.ItemSrc);
        TextView text = (TextView) convertView.findViewById(R.id.ItemText);
        src.setImageDrawable(ContextCompat.getDrawable(getContext(), resources[position]));
        text.setText(this.strings[position]);
        return convertView;
    }
}
