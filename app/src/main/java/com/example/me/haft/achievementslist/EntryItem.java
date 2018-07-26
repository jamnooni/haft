package com.example.me.haft.achievementslist;

public class EntryItem implements Item{
    private String title;
    private String description;
    private int iconId;
    private boolean isUnlocked=false;

    public EntryItem(String title, String description, int iconId, boolean isUnlocked) {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
        this.isUnlocked=isUnlocked;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getIconId() {
        return iconId;
    }
}
