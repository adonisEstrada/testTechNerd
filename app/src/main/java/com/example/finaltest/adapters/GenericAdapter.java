package com.example.finaltest.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.finaltest.R;

import java.util.List;

public class GenericAdapter extends ArrayAdapter<ItemsListView> {
    private List<ItemsListView> objects;
    private Context context;
    private int resource;

    public GenericAdapter(@NonNull Context context, int resource, @NonNull List<ItemsListView> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        GenericAdapterHolder holder = null;
        int resourseUtil = resource;
        if (row == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            holder = new GenericAdapterHolder();
            ItemsListView elemento = objects.get(position);

            int textElement1 = 0;
            int textElement2 = 0;
            int textElement3 = 0;
            int textElement4 = 0;
            if (elemento.getTitle2() != null && !elemento.getTitle2().equals("")) {
                resourseUtil = R.layout.listview_2_element;
                textElement1 = R.id.text2element1;
                textElement2 = R.id.text2element2;
            }
                        if (elemento.getTitle4() != null && !elemento.getTitle4().equals("")) {
                            resourseUtil = R.layout.listview_4_element;
                            textElement1 = R.id.text4element1;
                            textElement2 = R.id.text4element2;
                            textElement3 = R.id.text4element3;
                            textElement4 = R.id.text4element4;
                        }
//                        holder.textViewTitulo = (TextView) row.findViewById(R.id.text3element1);
//                        holder.textView2 = (TextView) row.findViewById(R.id.text3element2);
//                        holder.textView3 = (TextView) row.findViewById(R.id.text3element3);


            row = layoutInflater.inflate(resourseUtil, parent, false);
            if (textElement1 != 0) {
                holder.textViewTitulo = (TextView) row.findViewById(textElement1);
                holder.textViewTitulo.setText(elemento.getTitle1());
            }
            if (textElement2 != 0) {
                holder.textView2 = (TextView) row.findViewById(textElement2);
                holder.textView2.setText(elemento.getTitle2());
            }
            if (textElement3 != 0) {
                holder.textView3 = (TextView) row.findViewById(textElement3);
                holder.textView3.setText(elemento.getTitle3());
            }
            if (textElement4 != 0) {
                holder.textView4 = (TextView) row.findViewById(textElement4);
                holder.textView4.setText(elemento.getTitle4());
            }
        }
        return row;
    }

    static class GenericAdapterHolder {
        TextView textViewTitulo;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }
}
