package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import android.support.v4.widget.TextViewCompat;

import android.graphics.Typeface;

import android.view.Gravity;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.GridView;
import android.widget.TextView;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

/**
 * The Sudoku Adapter allows dynamically populating the GridView
 * with the appropriate number of TextView cells for the puzzle.
 */
public class SudokuAdapter extends BaseAdapter {

    // Global Variables
    private Context context;
    private SudokuModel model;

    // Initialization
    SudokuAdapter(Context c, SudokuModel model) {
        this.context = c;
        this.model = model;
    }

    public int getCount() {
        return model.puzzle.workingPuzzle.length;
    }

    public Object getItem(int position) {
        return model.puzzle.getValueAt(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    /**
     * This is called every time the app is started, or is restored from a landscape change
     * This is called whenever Android needs to generate a TextView object in the GridView.
     * For example, when the adapter is set through generateGrid(),
     * or if the grid is scrolled so a new row is brought onto the screen.
     * @param position  The position within the GridView
     * @param convertView   The reused view. If it is null, we generate the textView; otherwise we reuse the convertView.
     * @param parent    The parent of the view, used to inflate our textview to the proper
     *                  dimensions
     * @return  A TextView
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView textView;
        if (convertView == null) {

            textView = new TextView(context);

            //textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));//inflater.inflate(R.layout.item, null);

            // Set a bunch of parameters for the TextView. Most of them are to ensure that the text
            // scales to fit the parent, has the appropriate colours/contrasts etc...
            if (model.puzzle.isNotPreset(position)) {
                textView.setBackground(context.getResources().getDrawable(R.drawable.textview_selector));
//                textView.setBackgroundColor(context.getResources().getColor(R.color.input_background));
                textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                textView.setBackgroundColor(context.getResources().getColor(R.color.preset_background));
                textView.setTextColor(Color.WHITE);
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                textView.isAllCaps();
            }

            textView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 100));
            //textView.setTextSize(32);
//            textView.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

            textView.setText(capitalize(model.getDisplayedTextAt(position)));

        } else {
            textView = (TextView) convertView;
        }

        return textView;
    }
}
