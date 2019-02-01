package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SudokuActivity extends AppCompatActivity {
    private TextView gridtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
         final int[] puzzle = {
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                4, 5, 6, 7, 8, 9, 1, 2, 3,
                7, 8, 9, 1, 2, 3, 4, 5, 6,
                2, 3, 4, 5, 6, 7, 8, 9, 1,
                5, 6, 7, 8, 9, 1, 2, 3, 4,
                8, 9, 1, 2, 3, 4, 5, 6, 7,
                3, 4, 5, 0, 0, 0, 9, 1, 2,
                6, 0, 0, 0, 0, 0, 0, 0, 0,
                9, 0, 0, 0, 0, 0, 0, 0, 0
        };

        GridView grid = findViewById(R.id.grid);
        //TextView gridtext = findViewById(R.id.gridText);
        grid.setAdapter(new SudokuAdapter(this, puzzle));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Toast.makeText(this, "" , Toast.LENGTH_SHORT).show();
    }

    });
    }
}