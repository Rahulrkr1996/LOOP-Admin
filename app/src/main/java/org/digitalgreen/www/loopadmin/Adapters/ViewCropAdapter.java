package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.R;
import org.digitalgreen.www.loopadmin.Widgets.CircularImageView;

import java.util.ArrayList;

/**
 * Created by Rahul Kumar on 7/10/2016.
 */
public class ViewCropAdapter extends BaseAdapter {
    private ArrayList<Crop> list;
    private Context context;
    private OnEditClickListener listener;

    public ViewCropAdapter(ArrayList<Crop> list, Context context, OnEditClickListener listener) {
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
            convertView = inflater.inflate(R.layout.view_crop_row, parent, false);
            holder = new ViewHolder();
            holder.crop_name = (TextView) convertView.findViewById(R.id.crop_name);
            holder.crop_image = (ImageView) convertView.findViewById(R.id.crop_image);
            holder.crop_edit = (ImageView) convertView.findViewById(R.id.crop_edit);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.crop_name.setText(list.get(position).crop_name);
        holder.crop_image.setImageBitmap(list.get(position).getImage());

        holder.crop_edit.setTag(list.get(position));
        holder.crop_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop c = (Crop) v.getTag();
                listener.onEditClick(c, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView crop_name;
        ImageView crop_edit, crop_image;
    }

    public interface OnEditClickListener {
        void onEditClick(Crop f, int position);
    }

}
