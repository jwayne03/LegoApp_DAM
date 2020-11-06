package com.wayne.brickapp.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wayne.brickapp.R;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    private List<Theme> themes;
    private final Context context;
    private final int layoutResource;

    public ThemeAdapter(Context context, List<Theme> themes, int layoutResource) {
        this.context = context;
        this.themes = themes;
        this.layoutResource = layoutResource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResource, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        themes = (List<Theme>) themes.get(position);
    }

    @Override
    public int getItemCount() {
        return themes.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLegoImage;
        TextView tvThemeId;
        TextView tvThemeName;

        public ViewHolder(View view) {
            super(view);
            ivLegoImage = view.findViewById(R.id.ivLegoImage);
            tvThemeId = view.findViewById(R.id.tvThemeId);
            tvThemeName = view.findViewById(R.id.tvThemeName);
        }

        Theme theme;

        public void setTheme(Theme theme) {
            this.theme = theme;
            tvThemeName.setText(theme.getName());
            Picasso.with(context).load(theme.getName()).into(ivLegoImage);
        }
    }


}


