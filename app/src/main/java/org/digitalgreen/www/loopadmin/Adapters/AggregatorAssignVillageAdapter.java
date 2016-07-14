package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.activeandroid.query.Select;

import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Village;
import org.digitalgreen.www.loopadmin.R;

import java.util.List;

/**
 * Created by Rahul Kumar on 7/7/2016.
 */
public class AggregatorAssignVillageAdapter extends BaseAdapter {
    private List<Village> list;
    private Context context;
    private LoopUser loopUser;
    private long aggregatorID;

    public AggregatorAssignVillageAdapter(List<Village> list, Context context, long aggregatorID) {
        this.list = list;
        this.context = context;
        this.aggregatorID = aggregatorID;
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

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.basic_checkbox_row, parent, false);
            holder = new ViewHolder();
            holder.name = (CheckBox) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(list.get(position).name);

        loopUser = LoopUser.load(LoopUser.class, aggregatorID);

        for(int i=0;i<loopUser.assigned_villages.size();i++){
            if(holder.name.getText().toString().equals(loopUser.assigned_villages.get(i).toString())){
                holder.name.setChecked(true);
                break;
            }
        }

        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    loopUser = LoopUser.load(LoopUser.class, aggregatorID);
                    loopUser.assigned_villages.add(list.get(position));
                    loopUser.save();
                }else{
                    loopUser = LoopUser.load(LoopUser.class, aggregatorID);
                    loopUser.assigned_villages.remove(list.get(position));
                    loopUser.save();
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        CheckBox name;
    }
}


