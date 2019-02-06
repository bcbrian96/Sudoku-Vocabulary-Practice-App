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
        result = true;
        for (int regionNum = 0; regionNum < 9; regionNum++) {
            result = result && !containsDuplicates(getRow(regionNum));
            result = result && !containsDuplicates(getColumn(regionNum));
            result = result && !containsDuplicates(getBox(regionNum));
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
            column[i] = grid.getItemAtPosition(columnNum + i * 9).toString();
        }
        return column;
    }

    public String[] getBox(int boxNum) {
        String[] box = new String[9];
        int firstRow = (boxNum - (boxNum % 3));
        int firstCol = 3 * (boxNum % 3);
        // Go through the box, left-to-right top-to-bottom.
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int position = (firstCol + col) + (firstRow + row) * 9;
                box[col + 3*row] = grid.getItemAtPosition(position).toString();
            }
        }
        return box;
    }

    public boolean containsDuplicates(String[] region) {
        boolean[] seen_yet = new boolean[9];
        for(String word : region){
            for(int i = 0; i < 9; i++){
                if (word.equals(frenchWords[i]) || word.equals(englishWords[i])) {
                    if (seen_yet[i]) {
                        return true; // we already saw this word
                    }
                    seen_yet[i] = true;
//                    break;
                }
            }
        }
        return false;
    }
}
