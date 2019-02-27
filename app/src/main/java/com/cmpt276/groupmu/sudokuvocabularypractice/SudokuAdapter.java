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
    private int[] workingPuzzle;
    private int[] originalPuzzle;
    private String[][] words;
    private int language;

    public SudokuAdapter(Context c, int[] workingPuzzle, int[] originalPuzzle, String[][] Words, Boolean switchState) {
        this.context = c;
        this.workingPuzzle = workingPuzzle;
        this.originalPuzzle = originalPuzzle;
        this.words = Words;
        if(switchState){
            this.language = 1;
        } else {
            this.language = 0;
        }
    }

    public int getCount() {
        return workingPuzzle.length;
    }

    public Object getItem(int position) {
        return workingPuzzle[position];
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
            if(originalPuzzle[position]==0) {
                textView.setText(words[language][workingPuzzle[position]]);
            } else {
                textView.setText(words[1-language][workingPuzzle[position]]);
            }

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
