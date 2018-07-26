package com.example.me.haft.achievementslist;

public class SectionItem implements Item{
    private String title;

    public SectionItem(String title) {
        this.title = title;
    }

    @Override
    public boolean isSection() {
        return true;
    }

    public String getTitle() {
        return title;
    }
}
