package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.IconCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

public class SudokuAdapter extends BaseAdapter {


    private Context context;
    private SudokuPuzzle puzzle;

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = (LayoutInflater) context         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView textView;
        if (convertView == null) {

            textView = new TextView(context);

            //textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));//inflater.inflate(R.layout.item, null);
            textView.setBackgroundColor(Color.parseColor("#007080"));
            textView.setTextColor(Color.WHITE);
            textView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 124));
            //textView.setTextSize(32);
//            textView.setTextSize(context.getResources().getDimension(R.dimen.textsize));
            TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

            if (puzzle.isNormalMode() || puzzle.isNotPreset(position)) {
                textView.setText(puzzle.getWordAtPosition(position));
            } else {
                textView.setText(getItem(position).toString());
            }

        } else {
            textView = (TextView) convertView;
        }

        return textView;
    }
}
