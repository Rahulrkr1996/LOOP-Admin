package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.Models.Vehicle;
import org.digitalgreen.www.loopadmin.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rahul Kumar on 7/11/2016.
 */
public class ViewVehicleAdapter  extends BaseAdapter {
    private ArrayList<Vehicle> list;
    private Context context;
    private OnEditClickListener listener;

    public ViewVehicleAdapter(ArrayList<Vehicle> list, Context context, OnEditClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
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
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_vehicle_row, parent, false);
            holder = new ViewHolder();
            holder.vehicle_name = (TextView) convertView.findViewById(R.id.vehicle_name);
            holder.vehicle_edit = (ImageView) convertView.findViewById(R.id.vehicle_edit);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.vehicle_name.setText(list.get(position).vehicle_name);

        holder.vehicle_edit.setTag(list.get(position));
        holder.vehicle_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vehicle vehicle = (Vehicle) v.getTag();
                listener.onEditClick(vehicle, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView vehicle_name;
        ImageView vehicle_edit;
    }

    public interface OnEditClickListener {
        void onEditClick(Vehicle f, int position);
    }
}

