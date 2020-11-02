package com.wayne.brickapp.model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wayne.brickapp.R;

import java.util.List;

public class ThemeAdapter extends BaseAdapter {

    private List<Theme> themes;

    public ThemeAdapter(List<Theme> themes) {
        this.themes = themes;
    }

    @Override public int getCount() { return themes.size(); }
    @Override public Object getItem(int position) { return themes.get(position); }
    @Override public long getItemId(int position) {
//        Theme theme = themes.get(position);
        return position;
    }

    protected static class ViewHolder {
        ImageView ivLegoImage;
        TextView tvThemeId;
        TextView tvThemeName;
        public ViewHolder(View view) {
            ivLegoImage = view.findViewById(R.id.ivLegoImage);
            tvThemeId = view.findViewById(R.id.tvThemeId);
            tvThemeName = view.findViewById(R.id.tvThemeName);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("wayne", "getView(position: " + position + ")");
        Theme theme = themes.get(position);
        View view = convertView;

        if (view == null) {
            Log.d("wayne", "inflate!");
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_theme, null);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.tvThemeId.setText(String.valueOf(theme.getId()));
        viewHolder.tvThemeName.setText(theme.getName());
        Picasso.with(parent.getContext()).load(theme.getImageUrl()).into(viewHolder.ivLegoImage);
        return view;
    }
}


