package com.example.me.haft;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LearnAdapter extends BaseAdapter {

    private final Context context;
    private final MyDBHandler dbHandler;

    public LearnAdapter(Context context, MyDBHandler dbHandler) {
        this.context = context;
        this.dbHandler=dbHandler;
    }

    @Override
    public int getCount() {
        return dbHandler.countWorkoutsHandler();
    }

    @Override
    public Object getItem(int i) {return null;}

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("debug","getview called");
        final Workout workout = dbHandler.findWorkoutHandler(position);

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



        viewHolder.nameTextView.setText(workout.getName());
        Log.d("debug","meee"+workout.getName());
        /*if (dbHandler.findWorkoutHandler(position)!=null) {
            viewHolder.nameTextView.setText(dbHandler.findWorkoutHandler(position).getName());
            Log.d("debug", "meee" + dbHandler.findWorkoutHandler(position).getName());
        }*/
        //viewHolder.iconImageView.setImageDrawable(null);
        return convertView;

        //TODO: how baseAdapter getview() works?
        //TODO: how viewholder reduce size?

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

