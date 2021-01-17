package com.xuanthuan.nutritionforcow.Controler;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.xuanthuan.nutritionforcow.Model.Object_Food;
import com.xuanthuan.nutritionforcow.R;

import java.util.List;

public class Adapter_chooseRation extends BaseAdapter
{

    List<Object_Food> list;
    Context context;
    int layout;

    public Adapter_chooseRation(List<Object_Food> list, Context context, int layout) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolderChooseRation{
        EditText edtAmount;
        TextView txtNameRation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderChooseRation holder = new ViewHolderChooseRation();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_choose_food, null);

            holder.edtAmount = convertView.findViewById(R.id.amountFood);
            holder.txtNameRation = convertView.findViewById(R.id.nameFoodChoose);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChooseRation) convertView.getTag();
        }

        Object_Food choose_food = list.get(position);
        holder.txtNameRation.setText(choose_food.getNameFood());

        try {
            holder.edtAmount.setText((int) choose_food.getKg() + "");
        } catch (Exception e) {

        }

        holder.edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    choose_food.setKg(Float.parseFloat(String.valueOf(s)));
                    Log.e("zzz", "onTextChanged:  " + position + "   " + choose_food.getKg());
                    Log.e("zzz", "onTextChanged: z " + list.get(position).getKg());
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }
}
