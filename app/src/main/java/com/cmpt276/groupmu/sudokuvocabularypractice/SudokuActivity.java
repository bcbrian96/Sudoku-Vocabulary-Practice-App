package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.Context;
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

import java.util.Arrays;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {

//    Global Variables
    private TextView gridtext;
    private final String[] Englishpuzzle = {
            "five", "three", "", "", "seven", "", "", "", "",
            "six", "", "", "one", "nine", "five", "", "", "",
            "", "nine", "eight", "", "", "", "", "six", "",

            "eight", "", "", "", "six", "", "", "", "three",
            "four", "", "", "eight", "", "three", "", "", "one",
            "seven", "", "three", "", "two", "", "", "", "six",

            "", "six", "", "", "", "", "", "eight", "",
            "two", "", "", "four", "one", "nine", "", "", "five",
            "", "four", "five", "", "eight", "", "", "seven", "nine"
    };

    private final String[] Frenchpuzzle = {
            "cinq", "trois", "", "", "sept", "", "", "", "",
            "six", "", "", "un", "neuf", "cinq", "", "", "",
            "", "neuf", "huit", "", "", "", "", "six", "",

            "huit", "", "", "", "six", "", "", "", "trois",
            "quatre", "", "", "huit", "", "trois", "", "", "un",
            "sept", "", "trois", "", "deux", "", "", "", "six",

            "", "six", "", "", "", "", "", "huit", "",
            "deux", "", "", "quatre", "un", "neuf", "", "", "cinq",
            "", "quatre", "cinq", "", "huit", "", "", "sept", "neuf"
    };
    //    private final String[] puzzle = {
//            "one",  "two","three", "four", "five",  "six","seven","eight", "nine",
//           "four", "five",  "six","seven","eight", "nine",  "one",  "two","three",
//          "seven","eight", "nine",  "one",  "two","three", "four", "five",  "six",
//            "two","three", "four", "five",  "six","seven","eight", "nine",  "one",
//           "five",  "six","seven","eight", "nine",  "one",  "two",     "",     "",
//          "eight", "nine",  "one",  "two","three", "four", "five",  "six","seven",
//          "three", "four", "five",  "six","seven","eight", "nine",  "one",  "two",
//            "six","seven","eight", "nine",  "one",  "two","three", "four", "five",
//           "nine",  "one",  "two","three", "four", "five",  "six","seven","eight",
//    };
    private GridView grid;
    Button resetButton;
    private String mText = "";
    private int dialogChoice;
    private String currentItem = "";
    Button checkSudokuButton;
    Switch languageSwitch;
    Boolean switchState;
    private final String[] frenchWords = {"un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
    private final String[] englishWords = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private String[] savedPuzzle = new String[Englishpuzzle.length];
    public String state;

//    Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        grid = findViewById(R.id.grid);
        resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);
        checkSudokuButton = findViewById(R.id.checkSudoku);
        checkSudokuButton.setOnClickListener(this);
        languageSwitch = (Switch) findViewById(R.id.language_switch);
        languageSwitch.setOnClickListener(this);
        languageSwitch.setChecked(true);
        languageSwitch.setText("English");

        switchState = languageSwitch.isChecked();
        savedPuzzle = Arrays.copyOf(Englishpuzzle, Englishpuzzle.length);
        generateGrid();
    }
//    Drop Down Menue
    public void dialogBuilder(final TextView set) {
        AlertDialog.Builder sudokuWords = new AlertDialog.Builder(this);
        sudokuWords.setTitle("Select the word to insert");
        dialogChoice = 0;
//        Check Language Mdde
        if(switchState == true){
            sudokuWords.setSingleChoiceItems(frenchWords, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogChoice = which;
                }
            });
        }
        else{
            sudokuWords.setSingleChoiceItems(englishWords, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogChoice = which;
                }
            });
        }
//        Set value to grid
        sudokuWords.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) throws ArrayIndexOutOfBoundsException {
                if (dialogChoice != -1) {
                    if(switchState == true){
                        mText = frenchWords[dialogChoice];
                    }else {
                        mText = englishWords[dialogChoice];
                    }

                    set.setText(mText);

                }

            }

        });
//
        sudokuWords.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        sudokuWords.show();
//        Toast.makeText(this, mText, Toast.LENGTH_SHORT).show();
    }
//    Generate Grid
    public void generateGrid() {
        if(switchState == true){
            grid.setAdapter(new SudokuAdapter(this, Englishpuzzle));
        }else{
            grid.setAdapter(new SudokuAdapter(this, Frenchpuzzle));
        }



        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (v == null)
                    return;
                else if (Englishpuzzle[position] == "" || Frenchpuzzle[position] == "") {
                    TextView textViewClicked = (TextView) v;
                    dialogBuilder(textViewClicked);
                } else{
                    makeAToast(position);

                }
                return;
                //Toast.makeText(this, "" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    Button Click methods
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
                break;
            case R.id.language_switch:
                try {
                    changeLanguage();
                } catch (Exception e) {
                    Log.d("Check switch eror", "" + e);
                }
                break;

        }
    }
//    Check Sudoku solutions
    public void checkSudoku() {
        boolean result = false;
        for (int i = 0; i < grid.getCount(); i++) {
            currentItem = getDisplayedText(i);
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


    private String getDisplayedText(int position) {
        // This is needed because only the TextView's content is modified, not
        // the puzzle array's content, and using getView() would reset those values.
        return ((TextView) grid.getChildAt(position)).getText().toString();
    }

    public String[] getRow(int rowNum) {
        String[] row = new String[9];
        for (int i = 0; i < 9; i++) {
            row[i] = getDisplayedText(i + rowNum * 9);
        }
        return row;
    }

    public String[] getColumn(int columnNum) {
        String[] column = new String[9];
        for (int i = 0; i < 9; i++) {
            column[i] = getDisplayedText(columnNum + i * 9);
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
                box[col + 3*row] = getDisplayedText(position);
            }
        }
        return box;
    }

//    Check if rows and columns contain duplicates
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

//    Switch Language (French & English)
    public void changeLanguage(){
        if(switchState){
            switchState = false;
            state = "French";

        } else{
            switchState = true;
            state = "English";
        }
//        copyGrid();
        languageSwitch.setText(state);
        generateGrid();
        Toast.makeText(this, "Language Switched: " + state, Toast.LENGTH_SHORT).show();
    }

    public void makeAToast(int position){
        if(switchState){
            Toast.makeText(this, Frenchpuzzle[position], Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, Englishpuzzle[position], Toast.LENGTH_SHORT).show();
        }
    }
//    public void copyGrid() {
//        for (int i = 0; i < 81; i++) {
//            if (savedPuzzle[i] != "") {
//                savedPuzzle[i] = swapLanguage(switchState, savedPuzzle[i]);
//
//            }
//        }
//    }
//    public String swapLanguage(Boolean language, String word){
//        for(int i = 0; i < 9; i++){
//            if(savedPuzzle[i] != ""){
//                if(word == frenchWords[i]){
//                    return englishWords[i];
//                }else if(word == englishWords[i]){
//                    return frenchWords[i];
//                }
//                continue;
//            }
//
//        }
//        return null;
//    }
}
