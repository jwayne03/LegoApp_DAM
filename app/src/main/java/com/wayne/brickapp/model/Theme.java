package com.wayne.brickapp.model;

import com.google.gson.annotations.SerializedName;

public class Theme {

    private int id;
    @SerializedName("parent_id")
    private Integer parentId;
    private String name;
    @SerializedName("set_img_url")
    private String imageUrl;

    public Theme(Integer id, int parent_id, String name) {
        this.id = id;
        this.parentId = parent_id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public int getParentId() { return parentId; }
    public void setParentId(Integer parentId) { this.parentId = parentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
