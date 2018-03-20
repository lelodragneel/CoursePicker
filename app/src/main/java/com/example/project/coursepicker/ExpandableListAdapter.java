package com.example.project.coursepicker;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lelo on 2/22/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, Course> _listDataChild;

    protected ExpandableListAdapter(Context context, List<String> listDataHeader,
                                    HashMap<String, Course> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        final Course childText = (Course) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_list, null);
        }

        // tag the button with course name
        Button btn_add = convertView.findViewById(R.id.btn_add);
        btn_add.setTag(childText.getName());

        // extract course details
        TextView textView_desc = convertView.findViewById(R.id.textView_desc);
        TextView textView_req = convertView.findViewById(R.id.textView_req);
        TextView textView_courseDays = convertView.findViewById(R.id.textView_courseDays);
        TextView textView_courseSeats = convertView.findViewById(R.id.textView_courseSeats);
        TextView textView_courseTimes = convertView.findViewById(R.id.textView_courseTimes);

        // display on content view
        textView_desc.setText(childText.getDescription());
        textView_req.setText("Prerequisites: " + childText.getPrerequisites());
        textView_courseDays.setText("Days\n" + childText.getClassDays());
        textView_courseSeats.setText("Seats\n" + childText.getSeatsAvail());
        textView_courseTimes.setText("Hours\n" + childText.getClassTime());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.course_list, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

//        if (coursesRegistered.contains(headerTitle)) {
//            convertView.setBackgroundColor(_context.getApplicationContext().getResources()
//                    .getColor(R.color.lightGreen));
//        }

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
