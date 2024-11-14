//code for hangman game
import java.util.Random;
import java.util.Scanner;

class Word {
    private String actualWord;
    private String displayWord;

    // Constructor to set the word and display it as underscores
    public Word(String actualWord) {
        this.actualWord = actualWord.toUpperCase();
        this.displayWord = this.actualWord.replaceAll(".", "_ ");
    }

    public String getDisplayWord() {
        return displayWord;
    }

    public boolean guessLetter(char letter) {
        letter = Character.toUpperCase(letter);
        boolean correctGuess = false;
        StringBuilder updatedDisplay = new StringBuilder(displayWord.replaceAll(" ", ""));

        for (int i = 0; i < actualWord.length(); i++) {
            if (actualWord.charAt(i) == letter) {
                updatedDisplay.setCharAt(i, letter);
                correctGuess = true;
            }
        }
        displayWord = updatedDisplay.toString().replaceAll(".", "$0 ");
        return correctGuess;
    }

    public boolean isFullyGuessed() {
        return !displayWord.contains("_");
    }

    public String getActualWord() {
        return actualWord;
    }
}

class Player {
    private int wrongGuesses;
    private int maxGuesses;
    private String guessedLetters;

    public Player(int maxGuesses) {
        this.maxGuesses = maxGuesses;
        this.wrongGuesses = 0;
        this.guessedLetters = "";
    }

    public boolean hasGuessesLeft() {
        return wrongGuesses < maxGuesses;
    }

    public void addWrongGuess() {
        wrongGuesses++;
    }

    public void addGuessedLetter(char letter) {
        guessedLetters += letter;
    }

    public boolean hasGuessedLetter(char letter) {
        return guessedLetters.indexOf(letter) != -1;
    }

    public int getRemainingGuesses() {
        return maxGuesses - wrongGuesses;
    }
}

class HangmanGame {
    private Word word;
    private Player player;
    private Scanner input;

    public HangmanGame(String[] words, int maxGuesses) {
        Random random = new Random();
        String chosenWord = words[random.nextInt(words.length)];
        this.word = new Word(chosenWord);
        this.player = new Player(maxGuesses);
        this.input = new Scanner(System.in);
    }

    public void play() {
        System.out.println("Welcome to Hangman Game!");

        while (player.hasGuessesLeft() && !word.isFullyGuessed()) {
            System.out.println("Word to guess: " + word.getDisplayWord());
            System.out.println("Guesses left: " + player.getRemainingGuesses());

            System.out.print("Enter your guess: ");
            char guess = input.nextLine().toUpperCase().charAt(0);

            if (player.hasGuessedLetter(guess)) {
                System.out.println("You have already guessed that letter. Try again.");
                continue;
            }

            player.addGuessedLetter(guess);

            if (word.guessLetter(guess)) {
                System.out.println("Good guess!");
            } else {
                System.out.println("Incorrect guess.");
                player.addWrongGuess();
            }
        }

        if (word.isFullyGuessed()) {
            System.out.println("Congratulations! You guessed the word: " + word.getActualWord());
        } else {
            System.out.println("You've run out of guesses. The word was: " + word.getActualWord());
        }
    }
}

public class Hangman {
    public static void main(String[] args) {
        String[] words = {"Maruti", "Tata", "Suzuki", "Ducati", "Toyota"};
        int maxGuesses = 10;

        HangmanGame game = new HangmanGame(words, maxGuesses);
        game.play();
    }
}
