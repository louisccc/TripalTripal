package com.example.louisccc.tripal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louisccc.tripal.model.DBManager;
import com.example.louisccc.tripal.model.TriApplication;
import com.example.louisccc.tripal.model.TriDept;
import com.example.louisccc.tripal.utility.DeptRecordsAdapter;
import com.example.louisccc.tripal.utility.FriendsAdapter;
import com.example.louisccc.tripal.R;
import com.example.louisccc.tripal.utility.MembersAdapter;
import com.example.louisccc.tripal.utility.RecordsAdapter;
import com.example.louisccc.tripal.model.TriFriend;
import com.example.louisccc.tripal.model.TriItem;
import com.example.louisccc.tripal.model.TriTrip;
import com.example.louisccc.tripal.utility.DateHelper;

import junit.framework.Assert;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by louisccc on 2/12/16.
 */
public class TripActivity extends Activity {

    private static int mTripActivityViewResSrcId = R.layout.activity_trip;
    private static int mMembersListViewItemResSrcId = R.layout.activity_members_list_item;
    private static int mItemsListViewItemResSrcId = R.layout.activity_items_list_item;
    private static int mDeptRecordsItemResSrcId = R.layout.activity_items_list_item;


    private TriTrip mTrip;
    private TextView mTripNameTextView;
    private TextView mRemainTimesTextView;
    private TextView mTotalSpentTextView;
    private TextView mCurrBalanceTextView;


    private ListView mMembersListView;
    private MembersAdapter mMembersAdapter;
    private ListView mRecordsListView;
    private RecordsAdapter mRecordsAdapter;
    private ListView mDeptRecordsListView;
    private DeptRecordsAdapter mDeptRecordsAdapter;


    private Button mCheckImageButton;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mTripActivityViewResSrcId);

        mTrip = getIntent().getParcelableExtra("trip");
        Assert.assertTrue(mTrip != null);
        ((TriApplication)getApplication()).refreshGlobals();

        mTripNameTextView = (TextView) this.findViewById(R.id.trip_name);
        mTripNameTextView.setText(mTrip.getName());

        mTotalSpentTextView = (TextView) this.findViewById(R.id.trip_total_spent);
        mTotalSpentTextView.setText("NT$" + mTrip.getTotalCost((TriApplication)getApplication()));

        mRemainTimesTextView = (TextView) this.findViewById(R.id.trip_remain_times);
        if (DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateFrom()) >= 0 && DateHelper.getCalendarDay(0).getTime().compareTo(mTrip.getDateTo()) <= 0 ) {
            mRemainTimesTextView.setText("remaining " +
                    DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()) + ": Remaining days: " +
                    Long.toString( ( mTrip.getDateTo().getTime() - DateHelper.getCalendarDay(0).getTimeInMillis() )/(24 * 60 * 60 * 1000) ));
        }
        else {
            mRemainTimesTextView.setText(DateHelper.getDateString(mTrip.getDateFrom()) + "~" + DateHelper.getDateString(mTrip.getDateTo()));
        }
        mCurrBalanceTextView = (TextView) this.findViewById(R.id.trip_curr_balance);
        mCurrBalanceTextView.setText("NT$" + mTrip.getCurrBalance((TriApplication)getApplication()) );

        ExpandableListView mDashboardExpandableListView = (ExpandableListView) this.findViewById(R.id.trip_dashboard);
        DashboardExpandableListView mDashboardExpandableAdapter = new DashboardExpandableListView(getBaseContext());
        mDashboardExpandableAdapter.getItems().get(0).getChildItemList().addAll(mTrip.getMembers((TriApplication)getApplication()));
        mDashboardExpandableAdapter.getItems().get(1).getChildItemList().addAll(mTrip.getRecords((TriApplication)getApplication()));
        mDashboardExpandableAdapter.getItems().get(2).getChildItemList().addAll(((TriApplication) getApplication()).getgItems());
        mDashboardExpandableListView.setAdapter(mDashboardExpandableAdapter);

        mDashboardExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                if ( i == 0 ) {
                    TriFriend friend = (TriFriend) expandableListView.getExpandableListAdapter().getChild(i, i1);
                    Intent intent = new Intent(expandableListView.getContext(), FriendActivity.class);
                    intent.putExtra("friend", friend);
                    startActivity(intent);
                }
                else if ( i == 1 ) {
                    TriItem item = (TriItem) expandableListView.getExpandableListAdapter().getChild(i, i1);
                    Intent intent = new Intent( expandableListView.getContext(), ItemActivity.class );
                    intent.putExtra("item", item);
                    startActivity(intent);
                }
                else if ( i == 2 ) {
                    TriItem item = (TriItem) expandableListView.getExpandableListAdapter().getChild(i, i1);
                    Intent intent = new Intent( expandableListView.getContext(), ItemActivity.class );
                    intent.putExtra("item", item);
                    startActivity(intent);
                }

                return true;
            }
        });

        mDashboardExpandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                if(ExpandableListView.getPackedPositionType(l) == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
                    int group_position = ExpandableListView.getPackedPositionGroup(l);
                    final int child_position = ExpandableListView.getPackedPositionChild(l);

                    if ( group_position == 1 ) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TripActivity.this);
                        builder.setTitle("Select options");
                        CharSequence options[] = new CharSequence[]{"View", "Edit", "delete"};
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TriItem item;
                                Intent i;
                                switch (which) {
                                    case 0: // view
                                        item = (TriItem) ((DashboardExpandableListView)adapterView.getAdapter()).getItems().get(1).getChildItemList().get(child_position);
                                        i = new Intent(getBaseContext(), ItemActivity.class);
                                        i.putExtra("item", item);
                                        startActivity(i);
                                        break;
                                    case 1: // edit
                                        item = (TriItem) ((DashboardExpandableListView)adapterView.getAdapter()).getItems().get(1).getChildItemList().get(child_position);
                                        i = new Intent(getBaseContext(), EditItemActivity.class);
                                        i.putExtra("item", item);
                                        i.putExtra("trip", mTrip);
                                        startActivity(i);
                                        break;
                                    case 2: // delete
                                        item = (TriItem) ((DashboardExpandableListView)adapterView.getAdapter()).getItems().get(1).getChildItemList().get(child_position);
// TODO                                ((TriApplication)getApplication()).getgItems().remove(item); // delete
                                        ((TriApplication) getApplication()).refreshGlobals();
                                        mRecordsAdapter.clear();
                                        mRecordsAdapter.addAll(((TriApplication) getApplication()).getgItems());
                                        mRecordsAdapter.notifyDataSetChanged();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                        builder.show();
                    }
                }


                return true;
            }
        });

        for(int i=0; i < mDashboardExpandableListView.getExpandableListAdapter().getGroupCount(); i++){
            mDashboardExpandableListView.expandGroup(i);
        }

        setupCheckButton();

        this.getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public class DashboardCategoryItem {
        ArrayList<Object> items;
        String caption;

        public DashboardCategoryItem(String name) {
            this.caption = name;
            this.items = new ArrayList<Object>();
        }
        public ArrayList<Object> getChildItemList(){
            return this.items;
        }
        public String getName(){
            return caption;
        }
    }
    public class DashboardExpandableListView extends BaseExpandableListAdapter {
        private ArrayList<DashboardCategoryItem> itemList;
        private LayoutInflater inflater;

        public DashboardExpandableListView(Context context) {
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.itemList = new ArrayList<DashboardCategoryItem>();
            this.itemList.add(new DashboardCategoryItem("Members"));
            this.itemList.add(new DashboardCategoryItem("Items"));
            this.itemList.add(new DashboardCategoryItem("Balance"));
        }

        public ArrayList<DashboardCategoryItem> getItems(){
            return itemList;
        }
        @Override
        public int getGroupCount() {
            return this.itemList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return this.itemList.get(i).getChildItemList().size();
        }

        @Override
        public Object getGroup(int i) {
            return this.itemList.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return this.itemList.get(i).getChildItemList().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i+i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            View v = view;
            v = inflater.inflate(R.layout.activity_dashboard_parent, null);
            TextView name = (TextView) v.findViewById(R.id.ItemTitle);
            DashboardCategoryItem catItem = itemList.get(i);
            name.setText(catItem.getName());
            return v;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            View v = view;
            DashboardCategoryItem catItem = itemList.get(i);
            if ( i == 0 ) { // members
                v = inflater.inflate(R.layout.activity_members_list_item, null);
                TextView name = (TextView) v.findViewById(R.id.ItemName);
                TextView curr_balance = (TextView) v.findViewById(R.id.ItemAmount);
                // TextView proportion = (TextView) convertView.findViewById(R.id.ItemProportion);
                // ImageView thumb = (ImageView) convertView.findViewById(R.id.ItemThumbnail);
                TriFriend friend = (TriFriend) itemList.get(i).getChildItemList().get(i1);
                name.setText(friend.getName());
                double value_curr_balance = friend.getCurrBalance(getApplication().getBaseContext(), mTrip);
                curr_balance.setText( "NT$" + Double.toString(value_curr_balance) );
            }
            else if ( i == 1 ) { // items
                v = inflater.inflate(R.layout.activity_items_list_item, null);
                TextView note = (TextView) v.findViewById(R.id.ItemNote);
                TextView amount = (TextView) v.findViewById(R.id.ItemAmount);
                TriItem item = (TriItem) itemList.get(i).getChildItemList().get(i1);
                note.setText( item.getNote() );
                amount.setText( "NT$" + item.getAmount());
            }
            else if ( i == 2 ) { // balance
                v = inflater.inflate(R.layout.activity_items_list_item, null);
                TextView name = (TextView) v.findViewById(R.id.ItemNote);
                TextView note = (TextView) v.findViewById(R.id.ItemAmount);
                TriItem item = (TriItem) itemList.get(i).getChildItemList().get(i1);
                TriApplication app = ((TriApplication) getApplication().getBaseContext().getApplicationContext());
                ArrayList<TriDept> depts = new ArrayList<>();
                for ( TriDept dept : app.getgDepts() ) {
                    if (dept.getItemId() == item.getLocalId() ) {
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

                String showingText = "In item " + item.getLocalId();
                for ( TriDept dept : depts ) {
                    returns[ depts.indexOf(dept) ] = dept.getPaid() - item.getAmount() *  (dept.getProportion() / total_proportion);
                    showingText += " user " + dept.getUserId() + " should get " + returns[ depts.indexOf(dept) ] + " back\n";
                }

//                Assert.assertTrue( total_proportion == 100 );
                // Assert.assertTrue( total_money == mItems.get(position).getAmount() );

                name.setText( showingText );
                note.setText( item.getAmount() + "" );
            }

            return v;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    public void setupCheckButton(){
        mCheckImageButton = (Button) this.findViewById(R.id.trip_check);
        mCheckImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ResultActivity.class);
                i.putExtra("trip", mTrip);
                startActivity(i);
                return;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_trip_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.action_add_item:
                Intent i = new Intent(this, EditItemActivity.class);
                i.putExtra("trip", mTrip);
                startActivity(i);
                break;
            case R.id.action_add_friend:
                getTempAlert( TripActivity.this ).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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