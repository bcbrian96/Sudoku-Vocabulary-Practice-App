package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.graphics.drawable.IconCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.IconCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;
import static org.apache.commons.lang3.text.WordUtils.capitalize;

/**
 * The Sudoku Adapter allows Java to interact with the GUI
 */
public class SudokuAdapter extends BaseAdapter {

    // Global Variables
    private Context context;
    private SudokuPuzzle puzzle;

    // Initialization
    public SudokuAdapter(Context c, SudokuPuzzle puzzle) {
        this.context = c;
        this.puzzle = puzzle;
    }

    public int getCount() {
        return puzzle.workingPuzzle.length;
    }

    public Object getItem(int position) {
        return puzzle.workingPuzzle[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    /**
     * This is called everytime the app is started, or is restored from a landscape change
     * @param position  The position within the GridView
     * @param convertView   The reused view. Set to null so that we don't have recycling
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
            if (puzzle.isNotPreset(position)) {
                textView.setBackgroundColor(context.getResources().getColor(R.color.input_background));
                textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                textView.setBackgroundColor(context.getResources().getColor(R.color.preset_background));
                textView.setTextColor(Color.WHITE);
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                textView.isAllCaps();
            }

            textView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 124));
            //textView.setTextSize(32);
//            textView.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

            if (puzzle.isNormalMode()) {
                textView.setText(capitalize(puzzle.getWordAtPosition(position)));
            } else {
                // In listening comprehension mode, display numbers.
                textView.setText(capitalize(getItem(position).toString()));
            }

        } else {
            textView = (TextView) convertView;
        }

        return textView;
    }
}
