package com.cmpt276.groupmu.sudokuvocabularypractice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.apache.commons.lang3.text.WordUtils.capitalize;

/**
 * Main Activity. Provides Methods for:
 * - Initialization
 * - Dropdown menu
 * - Generates grid
 * - onClickListners
 * - Changes language modes (English and French)
 * - Changes puzzle modes (listening and reading comprehension modes
 * - Read word list from CSV file
 * - Save and restore app state after layout change (portrait/landscape)
 */
public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {

    /** VARIABLES */
    public SudokuModel model;
    protected GridView grid;

    Button resetButton;
    Button checkSudokuButton;
    Button newPuzzleButton;
    Switch languageSwitch;
    //Button newGameButton;
    Switch modeSwitch;
    private static final int READ_REQUEST_CODE = 42;
//    int GridSizeChoice;

    /** TextToSpeech */
    private TextToSpeech mTTS;
    float pitch = (float)0.7;
    float speed = (float)0.7;
    int detected_User_Choice_Size;
//    String[] newGameArray= {"4 x 4", "6 x 6","9 x 9", "12 x 12"};

    /**
     *
     * @param savedInstanceState Initialization on startup or from onSaveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // For every activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        // Declared Variables Initialization
        detected_User_Choice_Size = getIntent().getIntExtra("USER_REQUEST_SIZE", 9);
        model = new SudokuModel();
        model.puzzle.setPuzzleSize(detected_User_Choice_Size);
        //puzzle.readPuzzlesFromInputStream(getResources().openRawResource(R.raw.puzzles));
        //puzzle.newPuzzle();

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
        languageSwitch.setText(model.words.getCurrentLanguage());

        modeSwitch = findViewById(R.id.mode_switch);
        modeSwitch.setOnClickListener(this);

        /*
          Method for initializing text to speech variable mTTS
         */
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            /**
             * Set the initial language, and notify the user when TTS (voice) data is not available.
             * @param status Status of TextToSpeech initialization.
             */
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    int result = mTTS.setLanguage(model.words.getVoiceLocale());

                    if (result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(getApplicationContext(),
                                "Your device does not have voice data for "+model.words.getForeignLanguage()
                                +". Please download it from language settings.",Toast.LENGTH_LONG).show();
                    } else if (result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language Not Supported");
                        Toast.makeText(getApplicationContext(), "Sorry, Speech is not supported for "+model.words.getForeignLanguage(), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Log.e("TTS", "Initialization Failed");
                }
            }
        });


        Button openFile = findViewById(R.id.get_file);
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFileChooser();
            }
        });

        generateGrid();

    }

    /**
     *  Method for generating the dropdown menu to select the item to insert in the GridView
     *
     * @param set       The textview that is generated by the dialog builder
     * @param position  The position within the GridView / sudoku array
     */
    public void dialogBuilder(final TextView set, final int position) {
        final AlertDialog.Builder sudokuWords = new AlertDialog.Builder(this);
        sudokuWords.setTitle("Select the word to insert");

        /* The list of choices */
        sudokuWords.setItems(model.words.getChoiceWords(),  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int dialogChoice) {
                if (dialogChoice != -1) {
                    model.puzzle.setValueAt(position, dialogChoice);

                    set.setText(capitalize(model.getWordAtPosition(position)));

                }
            }
        });
        sudokuWords.show();

    }

    /**
     * Generate the GridView
     */
    public void generateGrid() {

        /* Connect to SudokuAdapter.java */
        grid.setAdapter(new SudokuAdapter(this, model));
//        grid.setBackgroundColor(Color.parseColor("#29434e"));

        /* Button actions on GridView*/
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (v != null) {
                    /* Empty cell, dropdown menu */
                    if (model.puzzle.isNotPreset(position)) {
                        dialogBuilder((TextView) v, position); // Choose a value for the cell.
                    } else {
                        /* Reading Comprehension (normal) Mode: Toast hint */
                        if(model.isNormalMode()) {
                            hintPresetCellTranslation(position);
                        } else {
                            /* Listening Comprehension Mode: Text to speech hint */
                            speak(position);
                        }
                    }
                }
            }
        });
        grid.bringToFront();
        Log.i("generateGrid", "grid generated");
    }

    /**
     * Method that separates the different button actions based on the type
     *
     * @param v View button within grid to be clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /* Clear and reset the puzzle */
            case R.id.resetBtn:
                try {
                    model.puzzle.resetPuzzle();
                    generateGrid();
                } catch (Exception e) {
                    Log.d("Can not reset", " " + e);
                }
                break;
            /* Check if the sudoku is correct */
            case R.id.checkSudoku:
                try {
                    checkSudoku();
                } catch (Exception e) {
                    Log.d("Check Sudoku error", "" + e);
                }
                break;
            /* Toggle the language modes (French & English) */
            case R.id.language_switch:
                try {
                    changeLanguage();
                } catch (Exception e) {
                    Log.d("Check switch eror", "" + e);
                }
                break;
            /* Toggle between Reading and Listening comprehension modes */
            case R.id.mode_switch:
                try {
                    changeMode();
                } catch (Exception e) {
                    Log.d("Mode switch error",""+e);
                }
                break;
            case R.id.newPuzzle:
                /* Generate a new puzzle */
                try {
                    model.newPuzzle();
                    generateGrid();
                } catch (Exception e) {
                    Log.d("New Puzzle error:","" + e);
                }
                break;
        }
    }

    /**
     * Check the current progress of the user against the puzzle solution
     */
    public void checkSudoku() {
        if (model.puzzle.checkSudokuIncorrect()) {
            Toast.makeText(this,"Sudoku not Correct",Toast.LENGTH_SHORT).show();
        } else if (model.puzzle.checkSudokuIncomplete()) {
            Toast.makeText(this, "Sudoku is not completed yet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Congratulation! Answer correct", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Swap languages (English and French)
     */
    public void changeLanguage() {
        model.words.swapLanguage();
        languageSwitch.setText(model.words.getCurrentLanguage());
        mTTS.setLanguage(model.words.getVoiceLocale());

        generateGrid();
        Toast.makeText(this, "Language Switched: " + model.words.getCurrentLanguage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Swap reading and listening comprehension modes
     */
    public void changeMode() {
        model.swapMode();
        if (model.isNormalMode()) {
            modeSwitch.setText(R.string.mode_normal);
            languageSwitch.setEnabled(true);
        } else {
            modeSwitch.setText(R.string.mode_listening);
            // Listening mode: ensure foreign language is spoken (prefilled)
            // and native language is input.
            if (model.words.currentLanguageIsNotForeignLanguage()) {
                model.words.swapLanguage();
                languageSwitch.setText(model.words.getCurrentLanguage());
                languageSwitch.setChecked(false);
            }
            languageSwitch.setEnabled(false); // disable changing language
            // possible todo: refactor so Normal, Alt-language, Listening are all different
            // eg. when starting new puzzle, choose mode from 3 above
            // move TTS initialization here ?
        }
        generateGrid();
    }

    /**
     * Provides a toast hint to the user when a prefilled cell is clicked in reading comprehension
     * mode.
     * @param position  The position within the GridView array
     */
    public void hintPresetCellTranslation(int position) {
        // create function in sudokuPuzzle for this? eg. puzzle.logHint(position)
        model.words.numHints[model.puzzle.getValueAt(position)]++;
        Log.d("hints", "Hint Position: " + position);
        Log.d("hints", "NumHint Value: " + model.words.numHints[model.puzzle.getValueAt(position)]);
        Toast.makeText(this, model.getTranslationAtPosition(position), Toast.LENGTH_SHORT).show();
    }

    /**
     * Provides an audio hint to the user when a prefilled cell is clicked in the listening
     * comprehension mode.
     * @param position  The position within the GridView array
     */
    public void speak(int position) {
        String text = model.getForeignWordAtPosition(position);
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    /**
     * Ensures that the textToSpeech variable is destroyed when OnDestroy() is called
     */
    @Override
    protected void onDestroy() {
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    /*
      Code added for reading csv files. Note, this app currently only accepts csv files.
      The intended format has not headers: English_word1, French_word1
                                           English_word2, French_word2
                                           ....
      Iteration 1 & 2 will only support french and english words, final will
      support multiple languages.
     */

    /**
     * Fires an intent to spin up the "file chooser" UI and select a CSV File.
     */
    public void startFileChooser() {

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
     * Gets a result from an activity - in this case from the Android File Manager, we return the
     * following variables to ensure that the CSV file was passed without error through the intent.
     * @param requestCode   Which request code was returned - We want READ_REQUEST_CODE
     * @param resultCode    Was the request code returned successfully - We want RESULT_OK
     * @param resultData    Returned value passed through the intent. In this case the URI to the
     *                      file
     *
     * Called after startFileChooser()
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
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                if (uri != null) {
                    Log.i(null, "Uri: " + uri.getPath());
                    try {
                        // Parse the words within the CSV file
                        parseCSV(uri);
                    } catch (Exception e) {
                        Log.i(null, "parseCSV: " + e.toString());
                    }
                } else {
                    Log.i(null, "Uri is null from onActivityResult()");
                }
            }
        }

    }

    /**
     * Parse the words from a CSV file given a file path URI variable. See format of CSV above.
     * @param uri   The URI of the CSV file to be parsed
     */
    public void parseCSV(Uri uri) {
        try {

            // Setup a buffer to read the CSV line by line
            InputStream inputStream = getContentResolver().openInputStream(uri);
            InputStreamReader isr = new InputStreamReader(inputStream);
            CSVReader dataRead = new CSVReader(isr);
            String[] nextLine;
            ArrayList<String> frWords = new ArrayList<>();
            ArrayList<String> enWords = new ArrayList<>();
            // Empty string for the first entry
            enWords.add(0, "");
            frWords.add(0, "");

            // For each line in the csv
            while ((nextLine = dataRead.readNext()) != null) {
                enWords.add(capitalize(nextLine[0]));
                frWords.add(capitalize(nextLine[1]));
            }

            // Close the stream
            dataRead.close();

            // Allocate new words
            model.words.allEnglishWords = enWords.toArray(new String[0]);
            model.words.allFrenchWords = frWords.toArray(new String[0]);
            model.words.loadWordPairs();
            // Initialize the grid again
            generateGrid();
        }
        catch (Exception e) {
            Log.e("TAG",e.toString());
        }
    }

    /**
     * Saves the instance of the app when device orientation is changed
     * @param savedInstanceState    The Bundle object to save the state parameters to.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i("saveState", "Saved Instance");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putIntArray("workingPuzzle", model.puzzle.workingPuzzle);
        savedInstanceState.putIntArray("originalPuzzle", model.puzzle.originalPuzzle);
        // These are the full list of word pairs.
        savedInstanceState.putStringArray("englishWords", model.words.allEnglishWords);
        savedInstanceState.putStringArray("frenchWords", model.words.allFrenchWords);
        // save puzzle indices and hints.
        savedInstanceState.putIntArray("pairIndexes", model.words.pairIndexes);
        savedInstanceState.putIntArray("numHints", model.words.numHints);
        savedInstanceState.putInt("languageIndex", model.words.languageIndex);
        savedInstanceState.putInt("gridSize", model.detected_User_Choice_Size);



        // etc.

        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Restores the app to the state before the orientation of the devices was changed
     * @param savedInstanceState    The Bundle object to restore the state parameters from.
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(null, "generate instance");
        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        int temp_size = savedInstanceState.getInt("gridSize");
        if (model.restoreState(savedInstanceState)) {
            // update if successful
            this.detected_User_Choice_Size = temp_size;
        }

    }

}


// END
