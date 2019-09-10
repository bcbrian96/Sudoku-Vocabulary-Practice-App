# Documentation: Sudoku Vocabulary Practice App (Final Iteration) - Group Mu

The final iteration of the Sudoku Vocabulary Practice app produces the fourth (final) working implementation that demonstrates the project concept: a playable Sudoku Vocabulary game. Rather than a normal Sudoku game, the idea of the Sudoku Vocabulary Practice App is to replace the digits 1 through 9 with nine pairs of words. Each pair of words consists of one word in the user's native language (English), and one word in the language being studied (French).


The final iteration involves the addition of new additional features such as: Sudoku progress bar, undo functionality, timer, and cell highlighting. The TDD examples of these new features are shown in more details below. Finally, the documentation concludes with a section outlining future issues to be addressed as the development of the app is to be taken further. 


## Final Iteration Features


**TDD Example: Sudoku Progress Bar**


**User Story:** As a language learner, I want a progress bar to indicate how close I am to complete the Sudoku. This is because for larger dimensions such as 12x12, I want to keep track how far I have progressed.
<br>**Example:** When a user clicks on an empty cell and enters a word, the progress bar will automatically update its progress towards completing the puzzle.


 Scenario 1:
* Given: that the user is filling in the grid in reading mode
* When: the user presses a pre-filled empty cell and enters one word 
* Then: the progress of the progress bar on the top will increase by one


 Scenario 2:
* Given: that the user is filling in the grid, and the grid includes a cell with the non-prefilled words “Quatre”, “Cinq”, “Six”, “Neuf”, “Sept” that the user has filled in. The progress bar has the progress of five words.
* When: the user clicks undo three time to delete the inserted words in the cells
* Then: the progress of progress bar decrements by three.


 Scenario 3:
* Given: that the user is filling in the grid with only one non-prefilled cell remaining
* When: the user presses the empty cell to enter the word “Deux”
* Then: the progress bar fills out and will make a short message that requires user to click the CheckSudoku button to check the correctness.


Scenario 4:
* Given: that user has inserted several words in the puzzle
* When:        the user press the reset button to reset the sudoku puzzle
* Then: the progress bar return back to the progress 0




**TDD Example: Undo**


**User story:** As a language learner, I want to be able to undo the recent word that I have filled out in the puzzle, so I can revise/fix my mistake.
<br>**Example:** When a user clicks the "undo" button, the previous word that is filled by the user is replaced by the previous instance.


Scenario 1:
* Given: that the user is filling in the grid, and has previously pressed an empty cell to enter the word “quatre”
* When: the user clicks the undo button
* Then: the word “quatre” at the position where the user has previously filled in will be removed


Scenario 2: 
* Given: that the user is filling in the grid, and has entered three words to three non-prefilled cells
* When: the user clicks the undo button three times
* Then: the three words that were recently inputted will be removed
 
**TDD Example: Timer**


**User story:** As a language learner, I want to be able to see how long I have taken to complete the puzzle, so I can compare/improve upon my time.
<br>**Example:** The time spent on the current Sudoku puzzle will be displayed by a timer.
 
Scenario 1:
* Given: that the user is filling in the grid, and has one empty cell remaining
* When: the user presses the empty cell to enter the word “quatre”, and clicks the Check Sudoku button
* Then: the time that it took to finish the puzzle will be displayed
 
Scenario 2:
* Given: that the user is filling in the grid, and has played for 10 seconds
* When: the user clicks the Pause button
* Then: the timer will pause at 10 seconds


Scenario 3:
* Given: that the user is filling in the grid, and has paused the Sudoku timer at 10 seconds
* When: the user clicks an empty cell to input a word “quatre”
* Then: the timer will continue from 10 seconds
 
 
**TDD Example: Cell Highlight**


**User story:** As a language learner, I want to be able to see which cell I have selected, so it is clear that I have clicked the correct cell that I have intended.
<br>**Example:** The empty cell that is clicked by the user will be briefly highlighted to notify user on which specific position of the cell was clicked.


 Scenario 1:
* Given: that the user is filling in the grid
* When: the user presses an empty cell
* Then: the cell that was clicked and processed by the user will be highlighted


Scenario 2:
* Given: that the user has inserted three words and a cell is highlighted
* When: the user press the reset button on screen
* Then: the cell highlight will be removed




## Future Issues


**User story:** As a student who wants to practice my pronunciation of words in the language that I am learning, I want a speaking/pronunciation comprehension mode. In this mode, words will be inputted into an appropriate puzzle cell by clicking on a cell and saying the word to input in the learning language.
<br>**Example:** When a user plays the Sudoku in the speaking comprehension mode, the words will be inputted into empty cells by speaking the word in the learning language. The user can then test their speaking comprehension to see if the SpeechToText recognizes their pronunciation.


Scenario 1:
* Given: that the user is filling in the grid in speaking comprehension mode
* When: the user presses an empty cell
* Then: the app will notify the user to “say the Word to input”


Scenario 2:
* Given: that the user is filling in the grid in speaking comprehension mode
* When: the user presses an empty cell, and the app notifies the user to “say the Word to input”, and the user says “Quatre” to their device
* Then: the cell that was clicked will be filled with the word “quatre” that they had said


**User story:** As a student who wants to practice my writing in the language that I am learning, I want a writing comprehension mode. In this mode, the app will use Optical Character Recognition to translate the word that I have written to text.
<br>**Example:** When a user plays the Sudoku in the writing comprehension mode, the words will be inputted into empty cells by writing the word in the learning language. I can then test my writing comprehension to see if I can correctly spell/write the translated word.


Scenario 1:
* Given: that the user is filling in the grid in writing comprehension mode
* When: the user presses an empty cell
* Then: a new Activity screen will pop up, that notifies user to write the word that they want to input inside of a rectangular region


Scenario 2:
* Given: that the user is filling in the grid in writing comprehension mode
* When: the user presses an empty cell, and writes the word “quatre” inside the specified rectangular region
* Then: the cell will be filled with “quatre” by the way the user spelled it




**User story:** As a language learner, I want to be able to import a single list of native words (ex. English) to use in the Sudoku, instead of both the native language and translated language.
<br>**Example:** When a user imports a single list of native words without the corresponding translations, the app will automatically translate the native words to the specified learning language to use in the Sudoku.


Scenario 1:
* Given: that the user clicks the “Import CSV” button
* When: the user imports a CSV with only one column of words
* Then: the app will automatically translate that column of words into a desired language (French) to make word pairs, and notify user


Scenario 2:
* Given: that the user clicks the “Import CSV” button, and imports a CSV with only one column of words
* When: the user starts to play the Sudoku
* Then: the cells will be populated by the automatically translated words


**User story:** As a avid language learner, I want to practice my knowledge of multiple languages in the Sudoku.
<br>**Example:** When the user clicks "New Game", the user can select its own native and learning language. For example, the current implementation is restricted to English to French. However, this new feature will allow English to Spanish, and ect.


Scenario 1:
* Given: that the user clicks “New Game”
* When: the user sets native language to English, and learning language to Spanish
* Then: the Sudoku cells will be repopulated with words in English and Spanish


**User story:** As a language learner that is active online, I want to compare the times that I have finished a pre-authorized Sudoku puzzle with other people.
<br>**Example:** When a user clicks "Check Leaderboard/Compare Times", the leaderboard of who finished the specified puzzle quicker and in what time will be displayed in a ranking system.


Scenario 1:
* Given: that the user enables Online Mode
* When: the user clicks “Check Leaderboard”
* Then: a ranking system will appear with the username and time that other people have taken to complete that puzzle


Scenario 2:
* Given: that the user enables Online Mode, and is almost finished with the current Sudoku Puzzle
* When: the user enters a input in the final cell, and the solution is correct
* Then: the user will be notified by a pop-up saying “You have completed the puzzle in 1 minute. You are ranked first!” 


**User story:** As a language learner, I want a start screen so I can decide and control language selection and difficulty of Sudoku Puzzles.
<br>**Example:** When a user starts the Sudoku App, the user will be greeted by a home/start screen, where the user can select difficulty levels (easy, normal, hard) and learning language.


Scenario 1:
* Given: that the user is in the home screen of their Android Device
* When: the user clicks the Sudoku Vocabulary App icon to open up the app
* Then: the user will be greeted by a start screen, where the user can select difficulty levels and desired learning language


Scenario 2:
* Given: that the user is at the start screen
* When: the user selects the hard difficulty level
* Then: the more Sudoku cells will be empty, making the puzzle harder to solve


Scenario 3:
* Given: that the user is at the start screen
* When: the user changed the desired language from the default French to Japanese
* Then: the Sudoku words will be shown in Japanese


**User story:** As a language learner, I want the import a large list of words and then select which words I specifically want to practice on in the Sudoku
<br>**Example:** When a user imports a word list and clicks on "Manage Words" the user will be able to select which specific words to include in the Sudoku Puzzle. The puzzle will be based on the selected word list.


Scenario 1:
* Given: the user enters a CSV file of words and clicks “Manage Words”
* When: the user clicks “Manage Words”
* Then: a new activity will pop up with the list of words in the CSV file, and the user will be able to specify what words they want to include in the Sudoku puzzle


**User story:** As a teacher, I want the capability to load a list of word pairs in either CSV or TXT file extensions. This is because the teacher wants the flexibility to load word pairs in multiple file formats (not just CSV)
<br>**Example:** When a user clicks on "Import Words" and imports a word file in TXT, the words in the Sudoku will be updated from the words in the TXT file.


Scenario 1:
* Given: the user clicks on “Import Words”
* When: the user imports a word file in TXT
* Then: the Sudoku will be updated from words in the TXT file
