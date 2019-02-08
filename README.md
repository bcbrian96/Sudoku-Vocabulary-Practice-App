# CMPT276-1191E1-Mu

# Documentation: Sudoku Vocabulary Practice App (Iteration 1)
Iteration 1 of the Sudoku Vocabulary Practice App produces the first working implementation that demonstrates the project concept: a playable Sudoku Vocabulary game. Rather than a normal Sudoku game, the idea of the Sudoku Vocabulary Practice App is to replace the digits 1 through 9 with nine pairs of words. Each pair of words consists of one word in the user's native language (English), and one word in the language being studied (French).

## Analysis of user requirements
### Iteration 1 User Stories/TDD Examples

* **User Story:**
As a user, I want to be able to fill in a square, so I can solve the Sudoku puzzle.
<br>**Example:** When a square is selected, the user can choose from a list of options,
and then square is filled in with that option.

* **User story:** As a language learning beginner, I want to select a translated word from a list to insert into a square, because I want to practice on easy words and be able to see the translated options.
<br>**Example:** When user clicks the empty cell, a list of fill-able words will be displayed for user to insert the word.

* **User story:** As a language learner, I want a reset button so that I can restart on the same Sudoku puzzle by removing all the words that I had inserted because my translation skill is still weak.
<br>**Example** : When the user presses the reset button on screen, the app will remove all the inserted words on the same puzzle.

* **User story:** As a language learner, I want to be able to check if my filled Sudoku puzzle is correct, because I want to check my answer to see if I am actually correct.
<br>**Example:** When a user clicks the "Check Answer" button, the filled out Sudoku puzzle will be checked against the stored solution. A toast will be displayed with some text regarding if they match or  not.

* **User story:** As a language learner, I want to be able to switch between solving the puzzle in my native language to the language being studied, so I am able to be proficient in both sides of the languages.
<br>**Example:** There will be a "Change Mode" button so that in one mode, the initial layout uses words in the user's native language and solving the puzzle is to fill in the grid cells using words from the language being studied. In another mode, the initial layout uses words from the language being studied and the user should fill in grid cells using her or his native language.

* **User story:** As a language learner, I want to be able to peek at the correct translation of a word, so that I can try to remember it in filling out the puzzle.
<br>**Example:** When a user selects a Sudoku cell that is part of the pre-filled configuration of a puzzle, the translation of that word is momentarily displayed.

### Complete Sudoku Vocabulary App User Stories/TDD Examples (Not Implemented)
* **User story:** As a language learner, I want to test my language skill by playing games with different difficulties.
<br>**Example:** When a user presses the new game button, a window will ask the user to select difficulties. Therefore, user can select the difficulty he/she wants.

* **User story:** As a language learner, I want to be able to undo the recent word that I have filled out in the puzzle, so I can revise/fix my mistake.
<br>**Example:** When a user clicks the "undo" button, the previous word that is filled by the user is replaced by the previous instance.

* **User story:** As a language learner, I want to be able to see how long I have taken to complete the puzzle, so I can compare/improve upon my time.
<br>**Example:** The time spent on the current Sudoku puzzle will be displayed by a timer.

* **User story:** As a language learner, I want to be able to start a completely new Sudoku puzzle, so I can work with different words.
<br>**Example:** When a user clicks the "New Game" button, a new Sudoku puzzle will start.

* **User story:** As a language teacher, I want my students to practice on specific words. I want to have the choices for word lists selection so that I can see the students' knowledge of the language.
<br>**Example:** When the user clicks on the new game button, the app will provide a list of word sets for user to select on so that the puzzle will be built based on the selected word list.

* **User story:** As a language learner, I want to be able to use a hint and check whether or not the word that I inputted is correct, so I am certain that I am on the right track in solving the puzzle.
<br>**Example:** When a user clicks the "hint" button on a filled square, the application will display a toast to inform the user if that filled square is "Correct" or "Incorrect".

* **User Story**: As a language teacher, I want to provide different words for students to work on during class, so the language learner will play the game with provided words.
<br>**Example**: When a teacher wants to select words for her students to practice, she can select the desired words from within the app or upload new ones.
