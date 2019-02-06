package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SudokuAdapter extends BaseAdapter {

    private Context context;
    private String[] textViewVals;

    public SudokuAdapter(Context c, String[] textViewVals) {
        this.context = c;
        this.textViewVals = textViewVals;
    }

    public int getCount() {
        return textViewVals.length;
    }

    public Object getItem(int position) {
        return textViewVals[position];
    }

    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = (LayoutInflater) context         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView textView;
        if (convertView == null) {

            textView = new TextView(context);

            //textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));//inflater.inflate(R.layout.item, null);
            textView.setBackgroundColor(Color.LTGRAY);
            textView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 122));
            textView.setText(textViewVals[position].toString());

            // set value into textview
//            TextView textView = (TextView) gridView
//                    .findViewById(R.id.the_grid_label);
//            textView.setText(textViewVals[position]);
        } else {
            textView = (TextView) convertView;
        }

        return textView;
    }
}
