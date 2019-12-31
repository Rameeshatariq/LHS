package com.example.cv.lhs.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cv.lhs.Lister;
import com.example.cv.lhs.MainActivity;
import com.example.cv.lhs.R;

import java.util.HashMap;
import java.util.List;

public class Monthly_Report_Extandable_List extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    private List<String> _listCount; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private String _activityid;

    public Monthly_Report_Extandable_List(Context context, List<String> listDataHeader,
                                      HashMap<String, List<String>> listChildData, String activity_id) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._activityid = activity_id;
        //  this._listCount = listCount;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        Log.d("000999", "getChildView: "+childText);
        Log.d("000000", "getChildView: "+childText.split("@")[1]);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.validation_custom_child_layout, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        final Button btn_validate = (Button) convertView
                .findViewById(R.id.validate);
        Log.d("000000", "getChildView: "+childText.split("@")[1]);

        txtListChild.setText(childText.split("@")[1]);

        try {
            btn_validate.setText("Validate");
            btn_validate.setTextColor(Color.parseColor("#C969B9"));
            Log.d("000333", "ElsegetChildView: " );

            Lister ls = new Lister(_context);
            ls.createAndOpenDB();
            String mData[][] = ls.executeReader("Select is_validated from validation " +
                    "where activity_id  = '" + _activityid + "' AND member_id  = '" + childText.split("@")[0] + "' " +
                    " AND type = '"+_listDataHeader.get(groupPosition)+"' ");

            if (mData[0][0].equals("1")) {
                btn_validate.setText("Validated");
                btn_validate.setTextColor(Color.parseColor("#459518"));
                Log.d("000333", "IfgetChildView: " + mData[0][0]);
                ls.closeDB();
            }
        }
        catch (Exception ex){

        }

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Lister ls= new Lister(_context);
                    ls.createAndOpenDB();

                    String ans1 = "insert into validation (activity_id, member_id,full_name,month, year, is_validated,added_by,type)" +
                            "values" +
                            "(" +
                            "'" + _activityid + "'," +
                            "'" + childText.split("@")[0] + "'," +
                            "'" + childText.split("@")[1] + "'," +
                            "'" + childText.split("@")[2] + "'," +
                            "'" + childText.split("@")[3] + "'," +
                            "'" + "1" + "'," +
                            "'" + MainActivity.login_useruid + "'," +
                            "'" + _listDataHeader.get(groupPosition)+ "'" +
                            ")";

                    Boolean res = ls.executeNonQuery(ans1);
                    Log.d("000555", "Data: " + ans1);
                    Log.d("000555", "Query: " + res);

                    if(res){
                        btn_validate.setTextColor(Color.parseColor("#459518"));
                    }

                }
                catch (Exception e)
                {
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        List childList = _listDataChild.get(_listDataHeader.get(groupPosition));
        if (childList != null && !childList.isEmpty()) {
            return childList.size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_parent_list, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
