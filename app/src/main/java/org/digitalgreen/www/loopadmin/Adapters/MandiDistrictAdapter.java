package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.R;

import java.util.List;

/**
 * Created by Rahul Kumar on 6/2/2016.
 */
public class MandiDistrictAdapter extends BaseAdapter {
    private List<District> list;
    private Context context;

    public MandiDistrictAdapter(List<District> list, Context context) {
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
            convertView = inflater.inflate(R.layout.addmandi_districtlist_row,parent,false);
            holder = new ViewHolder();
            holder.districtName = (TextView) convertView.findViewById(R.id.districtName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.districtName.setText(list.get(position).name);

        return convertView;
    }

    static class ViewHolder {
        TextView districtName;
    }
}
