package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {

//    Global Variables
    public SudokuPuzzle puzzle;
    protected GridView grid;
//    private LinearLayout linear;
    Button resetButton;
    private int dialogChoice;
    Button checkSudokuButton;
    Button newPuzzleButton;
    Switch languageSwitch;
    //Button newGameButton;
    private static final int READ_REQUEST_CODE = 42;
    //int detected_User_Choice_Size;
    int GridSizeChoice;
//    TextToSpeech
    private TextToSpeech mTTS;
    float pitch = (float)0.7;
    float speed = (float)0.7;
    int detected_User_Choice_Size;
    String[] newGameArray= {"4 x 4", "6 x 6","9 x 9", "12 x 12"};



    //    Initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        For every activity

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        int puzzleSize= getIntent().getIntExtra("USER_REQUEST_SIZE", 9);
        puzzle= new SudokuPuzzle();
        detected_User_Choice_Size = puzzleSize;
        puzzle.setPuzzleSize(detected_User_Choice_Size);
        grid = findViewById(R.id.grid);
        grid.setNumColumns(detected_User_Choice_Size);
        resetButton = findViewById(R.id.resetBtn);
        resetButton.setOnClickListener(this);
        checkSudokuButton = findViewById(R.id.checkSudoku);
        checkSudokuButton.setOnClickListener(this);
        newPuzzleButton = findViewById(R.id.newPuzzle);
        newPuzzleButton.setOnClickListener(this);
//        newGameButton = findViewById(R.id.NewGame);
//        newGameButton.setOnClickListener(this);

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
                        Toast.makeText(getApplicationContext(), "Failed to set language", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Log.e("TTS", "Initialization Failed");
                }
            }
        });

        Button openFile = (Button) findViewById(R.id.get_file);
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGet_file();
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
        sudokuWords.setItems(puzzle.getChoiceWords(),  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogChoice = which;
                if (dialogChoice != -1){
                puzzle.setValueAtPosition(position, dialogChoice);
                set.setText(puzzle.getWordAtPosition(position));}

            }
        });
//        Set value to grid

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
        Log.i(null, "generateGrid()");
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
                break;
        }
    }

//    Check Sudoku solutions
    public void checkSudoku() {
        if (puzzle.checkSudokuIncorrect()) {
            Toast.makeText(this,"Sudoku not Correct",Toast.LENGTH_SHORT).show();
        } else if (puzzle.checkSudokuIncomplete()) {
            Toast.makeText(this, "Sudoku is not completed yet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Congratulation! Answer correct", Toast.LENGTH_SHORT).show();
        }
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

    /**
     * Code added for reading csv files. Note, this app currently only accepts csv files.
     * The intended format has not headers: English_word1, French_word1
     *                                      English_word2, French_word2
     *                                      ....
     * Iteration 1 & 2 will only support french and english words, iteration 3 will
     * support multiple languages.
     */

    /**
     * Fires an intent to spin up the "file chooser" UI and select a CSV File.
     */
    public void setGet_file() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("text/*");

        startActivityForResult(intent, READ_REQUEST_CODE);


    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param resultData
     *
     * Called after setGet_File()
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
//                This string apparently does not exist
                Log.i(null, "Uri: " + uri.getPath());

                try {

                    parseCSV(uri);
                } catch (Exception e) {
                    Log.i(null, "parseCSV: " + e.toString());
                }
            }
        }

    }

    public void parseCSV( Uri uri){
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            InputStreamReader isr = new InputStreamReader(inputStream);
            CSVReader dataRead = new CSVReader(isr);
            String[] nextLine;

            while ((nextLine = dataRead.readNext()) != null) {

                puzzle.enWords.add(nextLine[0]);
                puzzle.frWords.add(nextLine[1]);
            }


            dataRead.close();
            fix_size();
//            System.out.println("English: ");
//            System.out.println(Arrays.toString(frWords.toArray()));
//            System.out.println("French: ");
//            System.out.println(Arrays.toString(frWords.toArray()));

        }
        catch (Exception e) {
            Log.e("TAG",e.toString());
        }
    }

    public void fix_size(){
        int size = puzzle.enWords.size();
       // int var = detected_User_Choice_Size;
        if(size < detected_User_Choice_Size){
            for(int i = 1; i < detected_User_Choice_Size-size+1; i++){
                puzzle.enWords.add(puzzle.englishWords[i]);
                puzzle.frWords.add(puzzle.frenchWords[i]);
            }
        } else if(size > detected_User_Choice_Size){
            for(int i = 0; i > size - detected_User_Choice_Size; i++){
                puzzle.enWords.remove(i);
                puzzle.frWords.remove(i);
            }

        } else{
//            Do nothing
        }
        puzzle.enWords.add(0, "");
        puzzle.frWords.add(0, "");

        puzzle.english = (String[])puzzle.enWords.toArray(puzzle.english);
        puzzle.french = (String[])puzzle.frWords.toArray(puzzle.french);
        puzzle.Words = new String[][]{puzzle.english, puzzle.french};
        generateGrid();
        System.out.println(Arrays.toString(puzzle.english));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(null, "Saved Instance");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putIntArray("workingPuzzle", puzzle.workingPuzzle);
        savedInstanceState.putIntArray("originalPuzzle", puzzle.originalPuzzle);
        savedInstanceState.putStringArray("english", puzzle.englishWords);
        savedInstanceState.putStringArray("french", puzzle.frenchWords);
        savedInstanceState.putInt("languageIndex", puzzle.languageIndex);
        savedInstanceState.putInt("gridSize",detected_User_Choice_Size);



        // etc.

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(null, "generate instance");
        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        int[] wp = savedInstanceState.getIntArray("workingPuzzle");
        System.arraycopy(wp, 0, puzzle.workingPuzzle, 0, detected_User_Choice_Size*detected_User_Choice_Size);

        int[] op = savedInstanceState.getIntArray("originalPuzzle");
        System.arraycopy(op, 0, puzzle.originalPuzzle, 0, detected_User_Choice_Size*detected_User_Choice_Size);

        puzzle.english = savedInstanceState.getStringArray("english");
        puzzle.french = savedInstanceState.getStringArray("french");
        puzzle.languageIndex = savedInstanceState.getInt("languageIndex");
        puzzle.Words[0] = puzzle.english;
        puzzle.Words[1] = puzzle.french;
        detected_User_Choice_Size = savedInstanceState.getInt("gridSize");

    }

}




