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
    String[] gridSizeArray= {"4 x 4", "6 x 6","9 x 9", "12 x 12"};
    String[] gridDifficultyArray = {"1","2","3","4","5"};
    Button mNewGameButton ;
    Button mContinueGameButton;
    TextView mSudokuTextView;
    int gridChoice;
    int difficultyChoice;
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
        gridSelect.setTitle("Select a grid size");
        gridSelect.setItems(gridSizeArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int temp = which;
                if (temp == 0)gridChoice = 4;
                if (temp == 1)gridChoice = 6;
                if (temp == 2)gridChoice = 9;
                if (temp == 3)gridChoice = 12;
                //Intent gridSize = new Intent(HomeScreen.this, SudokuActivity.class);
                //gridSize.putExtra("USER_REQUEST_SIZE", gridChoice);
                //startActivity(gridSize);
                difficultyChoicesDialog();
                //SudokuPuzzle puzzle = new SudokuPuzzle();
                //puzzle.setPuzzleSize(gridChoice);
            }
        });
        gridSelect.show();

    }
    public void difficultyChoicesDialog (){
        final AlertDialog.Builder difficultySelect = new AlertDialog.Builder(this);
        difficultyChoice = 1;
        difficultySelect.setTitle("Select a difficulty");
        difficultySelect.setItems(gridDifficultyArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int temp = which;
                if (temp == 0)difficultyChoice = 8;
                if (temp == 1)difficultyChoice = 6;
                if (temp == 2)difficultyChoice = 4;
                if (temp == 3)difficultyChoice = 3;
                if (temp == 4)difficultyChoice = 2;
                //Intent gridSize = new Intent(HomeScreen.this, SudokuActivity.class);
                Intent gridSize = new Intent(HomeScreen.this, SudokuActivity.class);
                gridSize.putExtra("USER_REQUEST_DIFFICULTY", difficultyChoice);
                gridSize.putExtra("USER_REQUEST_SIZE", gridChoice);
                //SudokuPuzzle puzzle = new SudokuPuzzle();
                //puzzle.setPuzzleSize(gridChoice);
                startActivity(gridSize);

            }
        });
        difficultySelect.show();
    }
}
