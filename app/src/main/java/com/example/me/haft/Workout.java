package com.example.me.haft;

public class Workout {
    private int index;
    private String name;
    private int iconId;
    private int imageId;
    private String description;

    public Workout(){};

    public Workout(int index, String name, int iconId, int imageId, String description) {
        this.index = index;
        this.name = name;
        this.iconId = iconId;
        this.imageId = imageId;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
