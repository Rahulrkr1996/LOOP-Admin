package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Models.Block;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.R;

import java.util.List;

/**
 * Created by Rahul Kumar on 6/15/2016.
 */
public class VillageAggregatorAdapter extends BaseAdapter {
    private List<LoopUser> list;
    private Context context;

    public VillageAggregatorAdapter(List<LoopUser> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(convertView==null){
            convertView = inflater.inflate(R.layout.basic_name_row,parent,false);
            holder = new ViewHolder();
            holder.Name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Name.setText(list.get(position).name);

        return convertView;
    }

    static class ViewHolder {
        TextView Name;
    }

}

