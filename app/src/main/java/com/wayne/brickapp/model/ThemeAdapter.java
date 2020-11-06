package com.wayne.brickapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wayne.brickapp.R;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    private ApiThemes apiThemes;
    private final Context context;

    public ThemeAdapter(Context context,ApiThemes apiThemes) {
        this.context = context;
        this.apiThemes = apiThemes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTheme(apiThemes.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        return apiThemes.getCount();
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
            tvThemeId.setText(String.valueOf(theme.getId()));
            Picasso.with(context).load(theme.getImageUrl()).into(ivLegoImage);
        }
    }
}


