package com.example.me.haft;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LearnGridAdapter extends BaseAdapter {

    List<String> names;
    List<Integer> iconIds=new ArrayList<>();
    Context context;

    public LearnGridAdapter(Context context, String[] names, int[] iconIds) {
        this.names= Arrays.asList(names);
        if (iconIds!=null)
            for (int i:iconIds){
            this.iconIds.add(i);
            }
        this.context=context;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        // view holder pattern
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.lean_item, null);
            final ImageView iconImageView = (ImageView)convertView.findViewById(R.id.learn_item_imageview);
            final TextView nameTextView = (TextView)convertView.findViewById(R.id.learn_item_textview);

            final ViewHolder viewHolder=new ViewHolder(nameTextView,iconImageView);
            convertView.setTag(viewHolder);
        }

        final ViewHolder viewHolder=(ViewHolder) convertView.getTag();

        viewHolder.nameTextView.setText(names.get(i));
        //viewHolder.iconImageView.setImageResource(iconIds.get(i));


        return convertView;
    }

    private class ViewHolder {
        private final TextView nameTextView;
        private final ImageView iconImageView;

        public ViewHolder(TextView nameTextView, ImageView iconImageView) {
            this.nameTextView = nameTextView;
            this.iconImageView = iconImageView;
        }
    }
}
