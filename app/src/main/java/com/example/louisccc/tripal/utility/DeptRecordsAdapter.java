package com.example.louisccc.tripal.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriDept;
import com.example.louisccc.tripal.model.TriItem;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class DeptRecordsAdapter extends ArrayAdapter<TriItem> {
    private ArrayList<TriItem> mItems;
    private int mResrc_id;
    private LayoutInflater mVi;

    public DeptRecordsAdapter(Context ctx, int resrc_id, ArrayList<TriItem> items) {
        super(ctx, resrc_id, items);
        this.mItems = items;
        this.mResrc_id = resrc_id;
        this.mVi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mVi.inflate(mResrc_id, null);
        TextView name = (TextView) convertView.findViewById(R.id.ItemName);
        TextView note = (TextView) convertView.findViewById(R.id.ItemAmount);

        TriApplication app = ((TriApplication) getContext().getApplicationContext());
        ArrayList<TriDept> depts = new ArrayList<>();
        for ( TriDept dept : app.getgDepts() ) {
            if (dept.getItemId() == mItems.get(position).getLocalId() ) {
                depts.add(dept);
            }
        }

        double total_proportion = 0;
        double total_money = 0;
        Double[] returns = new Double[depts.size()];
        for (TriDept dept : depts) {
            total_proportion += dept.getProportion();
            total_money += dept.getPaid();
        }

        String showingText = "In item " + mItems.get(position).getLocalId();
        for ( TriDept dept : depts ) {
            returns[ depts.indexOf(dept) ] = dept.getPaid() - mItems.get(position).getAmount() *  (dept.getProportion() / total_proportion);
            showingText += " user " + dept.getUserId() + " should get " + returns[ depts.indexOf(dept) ] + " back\n";
        }

        Assert.assertTrue( total_proportion == 100 );
//        Assert.assertTrue( total_money == mItems.get(position).getAmount() );

        name.setText( showingText );
        note.setText( mItems.get(position).getAmount() + "" );
        return convertView;
    }
}