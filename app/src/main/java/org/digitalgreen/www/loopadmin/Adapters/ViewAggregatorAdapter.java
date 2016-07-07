package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Models.Farmer;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.Village;
import org.digitalgreen.www.loopadmin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 7/7/2016.
 */
public class ViewAggregatorAdapter extends BaseExpandableListAdapter {
    private List<LoopUser> list;
    private Context context;
    private OnViewAggregatorEditClickListener listener;

    public ViewAggregatorAdapter(List<LoopUser> list, Context context, OnViewAggregatorEditClickListener editClickListener) {
        super();
        this.list = list;
        this.listener = editClickListener;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Village> vlist = list.get(groupPosition).assigned_villages;
        return vlist.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (list.get(groupPosition).assigned_villages).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (list.get(groupPosition).assigned_villages).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    // ------ToDo---------- ---------------------
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        convertView = inflater.inflate(R.layout.view_aggregator_row, parent, false);
        parentViewHolder = new ParentViewHolder();
        parentViewHolder.name = (TextView) convertView.findViewById(R.id.name);
        parentViewHolder.contact = (TextView) convertView.findViewById(R.id.contact);
        parentViewHolder.villages = (TextView) convertView.findViewById(R.id.villages);
        parentViewHolder.mandis = (TextView) convertView.findViewById(R.id.mandis);
        parentViewHolder.edit = (ImageView) convertView.findViewById(R.id.edit);

        parentViewHolder.name.setText(list.get(groupPosition).name);
        parentViewHolder.contact.setText(list.get(groupPosition).contact);
        parentViewHolder.villages.setText(String.valueOf((list.get(groupPosition).assigned_villages).size()) + "V");
        parentViewHolder.mandis.setText(String.valueOf((list.get(groupPosition).assigned_villages).size()) + "M");
        parentViewHolder.edit.setTag(list.get(groupPosition));
        parentViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoopUser loopUser = (LoopUser) v.getTag();
                //   long temp = m.getId();
                listener.onEditClick(loopUser);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        convertView = inflater.inflate(R.layout.view_aggregator_child_row, parent, false);
        childViewHolder = new ChildViewHolder();
        childViewHolder.name = (TextView) convertView.findViewById(R.id.name);
        childViewHolder.farmers = (TextView) convertView.findViewById(R.id.farmers);
        convertView.setTag(childViewHolder);

        ArrayList<Village> vList = list.get(groupPosition).assigned_villages;

        if (vList.size() != 0) {
            childViewHolder.name.setText(vList.get(childPosition).name);
            childViewHolder.farmers.setText(String.valueOf((new Farmer().getFarmerFromVillage(vList.get(childPosition).getId())).size()) + " F");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnViewAggregatorEditClickListener {
        void onEditClick(LoopUser loopUser);
    }

    static class ParentViewHolder {
        TextView name, contact, villages, mandis;
        ImageView edit;
    }


    static class ChildViewHolder {
        TextView name, farmers;
    }

}

