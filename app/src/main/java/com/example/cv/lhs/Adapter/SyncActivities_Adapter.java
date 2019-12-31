package com.example.cv.lhs.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cv.lhs.Lister;
import com.example.cv.lhs.R;

import java.util.ArrayList;

public class SyncActivities_Adapter extends BaseAdapter {
    private Context ctx;
    //    ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<HashMap<String, String>>();
    ArrayList<String> arrayListDate;
    ArrayList<String> activity;
    ArrayList<String> activity_id;
    ArrayList<String> activity_month;
    ArrayList<String> activity_year;
    String uid;
    Dialog alertDialog;
    Button execute;
    String abc;
    private LayoutInflater inflater = null;

    // Constructor
    public SyncActivities_Adapter(Context ctx, ArrayList<String> activity, ArrayList<String> arrayListDate,
                                  ArrayList<String> arrayListId, ArrayList<String> arrayListmonth, ArrayList<String> arrayListyear) {
        this.ctx = ctx;
        this.arrayListDate = arrayListDate;
        this.activity = activity;
        this.activity_id = arrayListId;
        this.activity_month = arrayListmonth;
        this.activity_year = arrayListyear;

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        return arrayListDate.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int pos, View row, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (row == null) {
            holder = new ViewHolder();

            row = inflater.inflate(R.layout.custom_sync_activities_lv_layout, null);
            holder.sync_list_background = row.findViewById(R.id.rl_background);
            holder.rl_left_topbottomcorner = (RelativeLayout) row.findViewById(R.id.rl_left_topbottomcorner);
            holder.sync_activities_lv_activity = row.findViewById(R.id.activityname);
            holder.sync_activities_lv_date = row.findViewById(R.id.activitydate);
            holder.sync_activities_lv_id= row.findViewById(R.id.activity_id);
            holder.activities_lv_status = row.findViewById(R.id.activitySatus);

            holder.activities_lv_status.setVisibility(View.GONE);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.sync_activities_lv_activity.setText(activity.get(pos));
        holder.sync_activities_lv_date.setText(arrayListDate.get(pos));
        holder.sync_activities_lv_id.setText(activity_id.get(pos));


        try{
            Lister ls= new Lister(ctx);
            ls.createAndOpenDB();

            String mData[][]= ls.executeReader("Select complete_status from Activities where activity_id = '"+activity_id.get(pos)+"' ");
            Log.d("000555", "getView: "+mData[0][0]);
            if(mData[0][0].equals("1")){
                holder.activities_lv_status.setVisibility(View.VISIBLE);
                holder.activities_lv_status.setText("Completed");
            }
        }
        catch (Exception e){

        }

        if (pos % 2 == 0) {

            holder.sync_list_background.setBackgroundColor(ctx.getResources().getColor(R.color.light_pink_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.rl_left_topbottomcorner.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.hp_listview_textview_redcolor));
            }
        } else {

            holder.sync_list_background.setBackgroundColor(ctx.getResources().getColor(R.color.color_white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.rl_left_topbottomcorner.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.hp_listview_textview_bluecolor));
            }
        }

        return row;
    }


    static class ViewHolder {
        TextView sync_activities_lv_date, sync_activities_lv_activity, sync_activities_lv_id, activities_lv_status;
        RelativeLayout sync_list_background,rl_left_topbottomcorner;


    }

}


