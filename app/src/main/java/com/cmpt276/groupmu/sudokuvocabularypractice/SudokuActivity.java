package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView gridtext;
//    private final String[] puzzle = {
//            "five", "three", "", "", "seven", "", "", "", "",
//            "six", "", "", "one", "nine", "five", "", "", "",
//            "", "nine", "eight", "", "", "", "", "six", "",
//
//            "eight", "", "", "", "six", "", "", "", "three",
//            "four", "", "", "eight", "", "three", "", "", "one",
//            "seven", "", "three", "", "two", "", "", "", "six",
//
//            "", "six", "", "", "", "", "", "eight", "",
//            "two", "", "", "four", "one", "nine", "", "", "five",
//            "", "four", "five", "", "eight", "", "", "seven", "nine"
//    };
    private final String[] puzzle = {
            "one",  "two","three", "four", "five",  "six","seven","eight", "nine",
           "four", "five",  "six","seven","eight", "nine",  "one",  "two","three",
          "seven","eight", "nine",  "one",  "two","three", "four", "five",  "six",
            "two","three", "four", "five",  "six","seven","eight", "nine",  "one",
           "five",  "six","seven","eight", "nine",  "one",  "two",     "",     "",
          "eight", "nine",  "one",  "two","three", "four", "five",  "six","seven",
          "three", "four", "five",  "six","seven","eight", "nine",  "one",  "two",
            "six","seven","eight", "nine",  "one",  "two","three", "four", "five",
           "nine",  "one",  "two","three", "four", "five",  "six","seven","eight",
    };
    private GridView grid;
    Button resetButton;
    private String mText = "";
    private int dialogChoice;
    private String currentItem = "";
    Button checkSudokuButton;
    private final String[] frenchWords = {"un", "deux", "Trois", "quatre", "cinq", "six", "sept", "huit", "nine"};
    private final String[] englishWords = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        grid = findViewById(R.id.grid);
        resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);
        checkSudokuButton = findViewById(R.id.checkSudoku);
        checkSudokuButton.setOnClickListener(this);
        //TextView gridtext = findViewById(R.id.gridText);
        generateGrid();
    }
    public void dialogBuilder(final TextView set) {
        AlertDialog.Builder sudokuWords = new AlertDialog.Builder(this);
        sudokuWords.setTitle("Select the word to insert");
        dialogChoice = 0;
        sudokuWords.setSingleChoiceItems(frenchWords, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogChoice = which;
            }
        });
        sudokuWords.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) throws ArrayIndexOutOfBoundsException {
                if (dialogChoice != -1) {
                    mText = frenchWords[dialogChoice];
                    set.setText(mText);
                }
            }
        });
        sudokuWords.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        sudokuWords.show();
    }
    public void generateGrid() {
        grid.setAdapter(new SudokuAdapter(this, puzzle));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (v == null)
                    return;
                else if (puzzle[position] == "") {
                    TextView textViewClicked = (TextView) v;
                    dialogBuilder(textViewClicked);
                }
                return;
                //Toast.makeText(this, "" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn:
                try {
                    generateGrid();
                } catch (Exception e) {
                    Log.d("Can not reset", " " + e);
                }
                break;
            case R.id.checkSudoku:
                try {
                    checkSudoku();
                } catch (Exception e) {
                    Log.d("Check Sudoku error", "" + e);
                }
        }
    }
    public void checkSudoku() {
        boolean result = false;
        for (int i = 0; i < grid.getCount(); i++) {
            currentItem = grid.getItemAtPosition(i).toString();
            if (currentItem.equals("")) {
                Log.d("false triggered", "false");
                Toast.makeText(this, "Sudoku is not completed yet", Toast.LENGTH_SHORT).show();
                return;
            }
        }
            for (int j = 0; j < grid.getCount(); j++) {
                int rowNumber = j / 9;
                int columnNumber = j % 9;
                result = realCheckSudoku(getRow(rowNumber), getColumn(columnNumber));
                }
        if (result == true) Toast.makeText(this,"Congratulation! Answer correct",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Sudoku not Correct",Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Congratulation! Answer correct", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Sudoku not correct", Toast.LENGTH_SHORT).show();
    }
    public String[] getRow(int rowNum) {
        String[] row = new String[9];
        for (int i = 0; i < 9; i++) {
            row[i] = grid.getItemAtPosition(i + rowNum * 9).toString();
        }
        return row;
    }

    public String[] getColumn(int columnNum) {
        String[] column = new String[9];
        for (int i = 0; i < 9; i++) {
            column[i] = grid.getItemAtPosition(i + columnNum * 9).toString();
        }
        return column;
    }

    public boolean realCheckSudoku(String[] rowArray, String[] columnArray) {
        int repeatedCount = 0;
        for (int curIndex = 0; curIndex < rowArray.length; curIndex++) {
            if (rowArray[curIndex].equals(frenchWords[0]) || rowArray[curIndex].equals(englishWords[0]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[1]) || rowArray[curIndex].equals(englishWords[1]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[2]) || rowArray[curIndex].equals(englishWords[2]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[3]) || rowArray[curIndex].equals(englishWords[3]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[4]) || rowArray[curIndex].equals(englishWords[4]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[5]) || rowArray[curIndex].equals(englishWords[5]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[6]) || rowArray[curIndex].equals(englishWords[6]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[7]) || rowArray[curIndex].equals(englishWords[7]))
                repeatedCount += 1;
            if (rowArray[curIndex].equals(frenchWords[8]) || rowArray[curIndex].equals(englishWords[8]))
                repeatedCount += 1;
        }
        for (int curIndex = 0; curIndex < columnArray.length; curIndex++) {
            if (columnArray[curIndex].equals(frenchWords[0]) || columnArray[curIndex].equals(englishWords[0]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[1]) || columnArray[curIndex].equals(englishWords[1]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[2]) || columnArray[curIndex].equals(englishWords[2]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[3]) || columnArray[curIndex].equals(englishWords[3]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[4]) || columnArray[curIndex].equals(englishWords[4]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[5]) || columnArray[curIndex].equals(englishWords[5]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[6]) || columnArray[curIndex].equals(englishWords[6]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[7]) || columnArray[curIndex].equals(englishWords[7]))
                repeatedCount += 1;
            if (columnArray[curIndex].equals(frenchWords[8]) || columnArray[curIndex].equals(englishWords[8]))
                repeatedCount += 1;
        }
        if (repeatedCount > 1) return false;
        else return true;
    }
}
