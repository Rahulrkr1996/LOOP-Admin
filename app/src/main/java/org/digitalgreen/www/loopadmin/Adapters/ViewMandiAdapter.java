package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.AddMandi;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 6/16/2016.
 */
public class ViewMandiAdapter extends BaseExpandableListAdapter {
    private List<Mandi> list;
    private Context context;
    private OnViewMandiEditClickListener listener;

    public ViewMandiAdapter(List<Mandi> list, Context context, OnViewMandiEditClickListener editClickListener) {
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
        List<Gaddidar> glist = new Gaddidar().getGaddidarsFromMandi(list.get(groupPosition).getId());
        return glist.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return new Gaddidar().getGaddidarsFromMandi(list.get(groupPosition).getId()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return new Gaddidar().getGaddidarsFromMandi(list.get(groupPosition).getId()).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        convertView = inflater.inflate(R.layout.view_mandi_row, parent, false);
        parentViewHolder = new ParentViewHolder();
        parentViewHolder.name = (TextView) convertView.findViewById(R.id.name);
        parentViewHolder.district_name = (TextView) convertView.findViewById(R.id.district_name);
        parentViewHolder.aggregators = (TextView) convertView.findViewById(R.id.aggregators);
        parentViewHolder.gaddidars = (TextView) convertView.findViewById(R.id.gaddidars);
        parentViewHolder.edit = (ImageView) convertView.findViewById(R.id.edit);

        parentViewHolder.name.setText(list.get(groupPosition).mandi_name);
        parentViewHolder.district_name.setText(list.get(groupPosition).district.name);
        parentViewHolder.gaddidars.setText(String.valueOf((new Gaddidar().getGaddidarsFromMandi(list.get(groupPosition).getId())).size()) + "G");
        parentViewHolder.aggregators.setText("NA");//String.valueOf(new LoopUser().getAggregatorsFromMandi( list.get(groupPosition).getId() ).size()));
        parentViewHolder.edit.setTag(list.get(groupPosition));
        parentViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mandi m = (Mandi) v.getTag();
             //   long temp = m.getId();
                listener.onEditClick(m);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        convertView = inflater.inflate(R.layout.view_mandi_child_row, parent, false);
        childViewHolder = new ChildViewHolder();
        childViewHolder.name = (TextView) convertView.findViewById(R.id.name);
        childViewHolder.contact = (TextView) convertView.findViewById(R.id.contact);
        childViewHolder.commission = (TextView) convertView.findViewById(R.id.commission);
        childViewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
        convertView.setTag(childViewHolder);

        ArrayList<Gaddidar> gList = new Gaddidar().getGaddidarsFromMandi(list.get(groupPosition).getId());

        if (gList.size() != 0) {
            childViewHolder.name.setText(gList.get(childPosition).name);
            childViewHolder.contact.setText(gList.get(childPosition).contact);
            childViewHolder.commission.setText(String.valueOf(gList.get(childPosition).commission));
            childViewHolder.photo.setImageBitmap(gList.get(childPosition).getImage());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnViewMandiEditClickListener {
        void onEditClick(Mandi mandi);
    }

    static class ParentViewHolder {
        TextView name, district_name, aggregators, gaddidars;
        ImageView edit;
    }


    static class ChildViewHolder {
        TextView name, contact, commission;
        ImageView photo;
    }

}
