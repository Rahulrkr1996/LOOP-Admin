package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Models.Village;
import org.digitalgreen.www.loopadmin.R;

import java.util.List;

/**
 * Created by Rahul Kumar on 7/7/2016.
 */
public class AggregatorAssignVillageAdapter extends BaseAdapter {
    private List<Village> list;
    private Context context;
    private boolean[] checkState;


    public AggregatorAssignVillageAdapter(List<Village> list, Context context) {
        this.list = list;
        this.context = context;
        checkState = new boolean[list.size()];
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(convertView==null){
            convertView = inflater.inflate(R.layout.basic_checkbox_row,parent,false);
            holder = new ViewHolder();
            holder.name = (CheckBox) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(list.get(position).name);
        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkState[position] = isChecked;
            }
        });
        return convertView;
    }

    public boolean[] getCheckState() {
        return checkState;
    }

    static class ViewHolder {
        CheckBox name;
    }
}


