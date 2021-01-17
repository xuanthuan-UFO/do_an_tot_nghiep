package com.xuanthuan.nutritionforcow.Controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuanthuan.nutritionforcow.R;

import java.util.List;

public class Adapter_MyRation extends BaseAdapter {

    List<String> list;
    Context context;
    int layout;

    public Adapter_MyRation(List<String> list, Context context, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
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
        return 0;
    }

    class ViewholderMyRation{
        TextView nameRation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewholderMyRation holder = new ViewholderMyRation();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_my_ration, null);

            holder.nameRation = convertView.findViewById(R.id.item_name_ration);

            convertView.setTag(holder);
        } else {
            holder = (ViewholderMyRation) convertView.getTag();
        }

        holder.nameRation.setText(list.get(position));

        return convertView;
    }
}
