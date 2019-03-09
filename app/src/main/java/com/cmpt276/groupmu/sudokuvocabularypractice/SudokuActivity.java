package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {

//    Global Variables
    public SudokuPuzzle puzzle;
    private GridView grid;
//    private LinearLayout linear;
    Button resetButton;
    private int dialogChoice;
    Button checkSudokuButton;
    Button newPuzzleButton;
    Switch languageSwitch;

//    TextToSpeech
    private TextToSpeech mTTS;
    float pitch = (float)0.7;
    float speed = (float)0.7;


//    Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        For every activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

//        Declared Variables
        puzzle = new SudokuPuzzle();
        puzzle.readPuzzlesFromInputStream(getResources().openRawResource(R.raw.puzzles));
        puzzle.newPuzzle();
        grid = findViewById(R.id.grid);
//        linear = findViewById(R.id.linear);
        resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);
        checkSudokuButton = findViewById(R.id.checkSudoku);
        checkSudokuButton.setOnClickListener(this);
        newPuzzleButton = findViewById(R.id.newPuzzle);
        newPuzzleButton.setOnClickListener(this);

        languageSwitch = findViewById(R.id.language_switch);
        languageSwitch.setOnClickListener(this);
        languageSwitch.setChecked(true);
        languageSwitch.setText(puzzle.getCurrentLanguage());

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    int result = mTTS.setLanguage(puzzle.getVoiceLocale());

                    if(result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language No Supported");
                        Toast.makeText(null, "Failed to set language", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Log.e("TTS", "Initialization Failed");
                }
            }
        });

        generateGrid();




    }
//    Drop Down Menue
    public void dialogBuilder(final TextView set, final int position) {
        AlertDialog.Builder sudokuWords = new AlertDialog.Builder(this);
        sudokuWords.setTitle("Select the word to insert");
        dialogChoice = 0;
//        Check Language Mdde
        sudokuWords.setSingleChoiceItems(puzzle.getChoiceWords(), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogChoice = which;
            }
        });
//        Set value to grid
        sudokuWords.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) throws ArrayIndexOutOfBoundsException {
                if (dialogChoice != -1) {
                    puzzle.setValueAtPosition(position, dialogChoice);
                    set.setText(puzzle.getWordAtPosition(position));
//                    set.setTextColor(1255);
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
        grid.setAdapter(new SudokuAdapter(this, puzzle));


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (v != null) {
                    if (puzzle.isNotPreset(position)) {
                        dialogBuilder((TextView) v, position); // Choose a value for the cell.
                    } else {
                        hintPresetCellTranslation(position);
                        speak(position);
                    }
                }
            }
        });
    }

    //    Button Click methods
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn:
                try {
                    puzzle.resetPuzzle();
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
            case R.id.newPuzzle:
                try {
                    puzzle.newPuzzle();
                    generateGrid();
                } catch (Exception e) {
                    Log.d("New Puzzle error:","" + e);
                }

        }
    }

//    Check Sudoku solutions
    public void checkSudoku() {
        if (puzzle.checkSudokuIncomplete()) {
            Log.d("checkSudoku", "sudoku incomplete");
            Toast.makeText(this, "Sudoku is not completed yet", Toast.LENGTH_SHORT).show();
            return;
        }
        if (puzzle.checkSudokuCorrect())
        Toast.makeText(this,"Congratulation! Answer correct",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Sudoku not Correct",Toast.LENGTH_SHORT).show();
    }

//    Switch Language (French & English)
    public void changeLanguage() {
        puzzle.swapLanguage();
        languageSwitch.setText(puzzle.getCurrentLanguage());
        mTTS.setLanguage(puzzle.getVoiceLocale());

        generateGrid();
        Toast.makeText(this, "Language Switched: " + puzzle.getCurrentLanguage(), Toast.LENGTH_SHORT).show();
    }

    public void hintPresetCellTranslation(int position) {
        Toast.makeText(this, puzzle.getTranslationAtPosition(position), Toast.LENGTH_SHORT).show();
    }

//    Speaking functions
    public void speak(int position) {
        String text = puzzle.getTranslationAtPosition(position);
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
//        } else {
//            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//        }

    }
//    Make sure the mTTS variable is destroyed
    @Override
    protected void onDestroy() {
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
