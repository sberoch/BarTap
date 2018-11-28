package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eriochrome.bartime.R;

import java.util.ArrayList;

public class DrawerAdapter extends BaseAdapter {

    private ArrayList<String> itemsMenu;
    private Context context;

    public DrawerAdapter(Context context, ArrayList<String> itemsMenu) {
        this.context = context;
        this.itemsMenu = itemsMenu;
    }

    @Override
    public int getCount() {
        return itemsMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_drawer, viewGroup, false);
        }

        TextView menuItem = view.findViewById(R.id.menuItemText);
        menuItem.setText(itemsMenu.get(position));

        return view;
    }
}
