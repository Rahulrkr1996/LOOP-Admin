package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Models.Block;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.Farmer;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.Village;
import org.digitalgreen.www.loopadmin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 7/5/2016.
 */
public class ViewVillageAdapter extends BaseExpandableListAdapter {

    private List<Block> list;
    private Context context;
    private OnViewVillageEditClickListener listener;

    public ViewVillageAdapter(List<Block> list, Context context, OnViewVillageEditClickListener editClickListener) {
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
        List<Village> vlist = new Village().getVillageFromBlock(list.get(groupPosition).name);
        return vlist.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return new Village().getVillageFromBlock(list.get(groupPosition).name).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return new Village().getVillageFromBlock(list.get(groupPosition).name).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        convertView = inflater.inflate(R.layout.view_village_row, parent, false);
        parentViewHolder = new ParentViewHolder();
        parentViewHolder.name = (TextView) convertView.findViewById(R.id.name);
        parentViewHolder.villages = (TextView) convertView.findViewById(R.id.villages);

        parentViewHolder.name.setText(list.get(groupPosition).name);
        parentViewHolder.villages.setText(String.valueOf((new Village().getVillageFromBlock(list.get(groupPosition).name)).size()) + " Villages");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        convertView = inflater.inflate(R.layout.view_village_child_row, parent, false);
        childViewHolder = new ChildViewHolder();
        childViewHolder.name = (TextView) convertView.findViewById(R.id.name);
        childViewHolder.farmers = (TextView) convertView.findViewById(R.id.farmers);
        childViewHolder.edit = (ImageView) convertView.findViewById(R.id.edit);

        ArrayList<Village> vList = new Village().getVillageFromBlock(list.get(groupPosition).name);

        if (vList.size() != 0) {
            childViewHolder.name.setText(vList.get(childPosition).name);
            childViewHolder.farmers.setText(String.valueOf((new Farmer().getFarmerFromVillage(vList.get(childPosition).getId())).size())+" F");
            childViewHolder.edit.setTag(vList.get(childPosition));
            childViewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Village village = (Village) v.getTag();
                    //long temp = village.getId();
                    listener.onEditClick(village);
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnViewVillageEditClickListener {
        void onEditClick(Village village);
    }

    static class ParentViewHolder {
        TextView name, villages;
    }


    static class ChildViewHolder {
        TextView name, farmers;
        ImageView edit;
    }

}
