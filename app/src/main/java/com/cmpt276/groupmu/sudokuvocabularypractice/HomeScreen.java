package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {
    Button mNewGameButton ;
    Button mContinueGameButton;
    int gridChoice;

    final int GRID_SIZE_REQUEST = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mNewGameButton= findViewById(R.id.NewGame);
        mContinueGameButton = findViewById(R.id.ContinueGame);
        mNewGameButton.setOnClickListener(this);
        mContinueGameButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.NewGame:
                try {
                    dialogBuilder();

                } catch (Exception e) {
                    Log.d("Can not reset", " " + e);
                }
                break;
        }
    }
    public void dialogBuilder() {
        final AlertDialog.Builder gridSelect = new AlertDialog.Builder(this);
        gridChoice = 9;
//        String[] gridSizeArray= {"4 x 4", "6 x 6", "9 x 9", "12 x 12"};
//        final int[] gridsize_choices = {4, 6, 9, 12};
        String[] gridSizeArray= {"4 x 4", "6 x 6", "9 x 9","12 x 12"};
        final int[] gridsize_choices = {4, 6, 9, 12};
        gridSelect.setTitle("Select a grid size");
        gridSelect.setItems(gridSizeArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gridChoice = gridsize_choices[which];

                //Intent gridSize = new Intent(HomeScreen.this, SudokuActivity.class);
                Intent gridSize = new Intent(HomeScreen.this,SudokuActivity.class);
                gridSize.putExtra("USER_REQUEST_SIZE", gridChoice);
                //SudokuPuzzle puzzle = new SudokuPuzzle();
                //puzzle.setPuzzleSize(gridChoice);
                startActivity(gridSize);
            }
        });
        gridSelect.show();

    }
}
