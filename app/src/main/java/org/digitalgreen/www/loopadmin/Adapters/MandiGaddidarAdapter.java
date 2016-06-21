package org.digitalgreen.www.loopadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.R;

import java.io.File;
import java.util.List;

/**
 * Created by Rahul Kumar on 6/8/2016.
 */
public class MandiGaddidarAdapter extends BaseAdapter {
    private List<Gaddidar> list;
    private Context context;
    private OnEditClickListener listener;

    public MandiGaddidarAdapter(List<Gaddidar> list, OnEditClickListener elistener, Context context) {
        super();
        this.list = list;
        this.listener = elistener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.addmandi_gaddidarlist_row, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.gaddidarList_name);
            holder.contact = (TextView) convertView.findViewById(R.id.gaddidarList_contact);
            holder.commission = (TextView) convertView.findViewById(R.id.gaddidarList_commission);
            holder.photo = (ImageView) convertView.findViewById(R.id.gaddidarList_photo);
            holder.edit = (ImageView) convertView.findViewById(R.id.gaddidarList_edit);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(list.get(position).name);
        holder.contact.setText(list.get(position).contact);
        holder.photo.setImageBitmap(list.get(position).getImage());
        holder.commission.setText(String.valueOf(list.get(position).commission));

        holder.edit.setTag(list.get(position));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gaddidar g = (Gaddidar) v.getTag();
                listener.onEditClick(g, position);
            }
        });

        return convertView;
    }

    public interface OnEditClickListener {
        void onEditClick(Gaddidar f, int position);
    }

    static class ViewHolder {
        TextView name, contact, commission;
        ImageView photo, edit;
    }
}
