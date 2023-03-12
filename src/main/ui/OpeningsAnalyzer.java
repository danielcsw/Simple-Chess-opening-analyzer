package ui;

import model.Game;
import model.GameList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class OpeningsAnalyzer {

    Scanner input;
    Scanner input2;  //made 2 scanners because there was a bug in some methods when using 1
    private GameList gl;
    private Game game;
    private String color;
    private String myOpening;
    private String theirOpening;
    private String result;

    private static final String JSON_STORE = "./data/myFile.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the OpeningAnalyzer
    public OpeningsAnalyzer() {

        input = new Scanner(System.in);
        input2 = new Scanner(System.in);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        gl = new GameList("Users' Games");
        runOpeningsAnalyzer();
    }

    // EFFECTS: initializes gameList, and introduces the user to the application
    public void runOpeningsAnalyzer() {
        System.out.println("_________________________________________________________________________________________");
        System.out.println("_________________________________________________________________________________________");
        System.out.println("Hello! Welcome to the chess opening analyzer. With this application, you can manually add");
        System.out.println("chess games that you have played, and see various statistics on your openings.");
        System.out.println("_________________________________________________________________________________________");
        System.out.println("_________________________________________________________________________________________");
        startUp();
    }

    private void startUp() {
        System.out.println("Would you like to load up your previous data?");
        System.out.println("enter 'y' if you would like to, 'n' if not.");
        String choice1 = input.nextLine();

        while (!((choice1.equals("y")) || (choice1.equals("n")))) {
            System.out.println("That's not a valid input, please try again.");
            choice1 = input.nextLine();
        }

        if (choice1.equals("y")) {
            loadWorkRoom();
            menu();
        } else {
            menu();
        }
    }



    //EFFECTS: shows the user the option menu
    public void options() {
        System.out.println("What would you like to do?");
        System.out.println("Type 'a' to add game,");
        System.out.println("Type 'b' to view all games,");
        System.out.println("Type 'c' to view your statistics,");
        System.out.println("Type 'q' to quit or start over.");
    }

    //EFFECTS: shows user how many games were played, and also takes user input for what they wanna do
    public void menu() {
        System.out.println("");
        System.out.println("Currently, you have played " + gl.length() + " games.");
        options();
        String choice2 = input.nextLine();

        while (!((choice2.equals("a")) || (choice2.equals("b")) || (choice2.equals("c")) || (choice2.equals("q")))) {
            System.out.println("That's not a valid input, please try again.");
            choice2 = input.nextLine();
        }

        if (choice2.equals("a")) {
            System.out.println("You're now adding a game to the system.");
            pickedAddGame();
        } else if (choice2.equals("b")) {
            System.out.println("You are now viewing all your games.");
            viewGames();
        } else if (choice2.equals("c")) {
            System.out.println("You are now viewing your statistics.");
            pickedViewStats();
        } else {
            System.out.println("You are now quitting.");
            quit();
        }
    }

    //EFFECTS: takes the user input for game.
    public void pickedAddGame() {
        System.out.println("What color did you play as?");
        System.out.println("Choices are 'white' or 'black'.");
        color = input.nextLine();

        while (!(color.equals("white") || color.equals("black"))) {
            System.out.println("That's not a valid color, please try again.");
            color = input.nextLine();
        }

        System.out.println("What opening did you use?");
        myOpening = input.nextLine();

        System.out.println("What opening did your opponent play?");
        theirOpening = input.nextLine();

        System.out.println("Did you win, lose, or draw?");
        System.out.println("Choices are 'won', 'lost', or 'drew'");
        result = input.nextLine();

        while (!(result.equals("won") || result.equals("lost") || result.equals("drew"))) {
            System.out.println("That's not a valid result, please try again.");
            result = input.nextLine();
        }
        System.out.println("");
        System.out.println("You've now successfully added your game.");

        nowAddGame();

    }


    //EFFECTS: makes a new game based on the user input and adds it to the GameList.
    public void nowAddGame() {
        game = new Game(color, myOpening, theirOpening, result);
        gl.addGame(game);
        System.out.println("Would you like to keep adding or go back to menu?");
        System.out.println("Type 'a' to keep adding or 'm' to go back.");
        whatNext();

    }

    //EFFECTS: asks user if they wanna keep adding or go back to menu
    public void whatNext() {
        String choice3 = input.nextLine();;

        while (!(choice3.equals("a") || choice3.equals("m"))) {
            System.out.println("That's not a valid input, please try again.");
            choice3 = input.nextLine();
        }

        if (choice3.equals("a")) {
            pickedAddGame();
        } else {
            System.out.println("");
            menu();
        }
    }


    //EFFECTS: shows the user all the games that are in the GameList so far
    public void viewGames() {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("Your games:");
        System.out.println("");
        int count = 0;
        LinkedList<Game> list = gl.getList();

        for (Game g : list) {
            count = count + 1;
            System.out.println("Game" + count + ": played as " + g.getColor() + ", played " + g.getMyOpening()
                    + " against " + g.getTheirOpening() + ". " + g.getResult());
        }
        System.out.println("----------------------------------------------------------------------------------------");
        removeOrGoBack();
    }

    //EFFECTS: asks user if they want to remove a game or go back
    public void removeOrGoBack() {
        System.out.println("Type 'm' to go back to menu. ");
        System.out.println("If you would like to remove a game, type r.");
        String choice4 = input.nextLine();

        while (!(choice4.equals("m") || choice4.equals("r"))) {
            System.out.println("That's not a valid input, please try again.");
            choice4 = input.nextLine();
        }

        if (choice4.equals("m")) {
            menu();
        } else {
            System.out.println("You are now removing a game from the list.");
            remove();
        }
    }

    //EFFECTS: asks user on what game they want to remove, or if they wanna cancel
    public void remove() {
        int n;

        do {
            System.out.println("Type the game number of the game you would like to remove.");
            System.out.println("If you would like to cancel, type 0.");
            n = input2.nextInt();

            if (!(gl.checkIndex(n))) {
                System.out.println("That game does not exist.");
            }
        } while (!(gl.checkIndex(n)));


        if (n == 0) {
            System.out.println("Cancelled removing game.");
            viewGames();
        } else {
            gl.removeGame(n);
            System.out.println("You have removed game" + n + ".");
            viewGames();
        }
    }

    //EFFECTS: shows the user the overall winrate, and asks if they want to go back to menu or see specific win rates
    public void pickedViewStats() {
        System.out.println("Your current overall winrate is " + gl.ovrlWinRate() + "%.");
        System.out.println("If you would like to check your win rate with your specific opening, type 'a'.");
        System.out.println("If you would like to check your win rate against a  specific opening, type 'b'.");
        System.out.println("Type m to go back to menu.");
        String choice5 = input.nextLine();

        while (!(choice5.equals("a") || choice5.equals("b") || choice5.equals("m"))) {
            System.out.println("That's not a valid input, please try again.");
            choice5 = input.nextLine();
        }

        if (choice5.equals("a")) {
            myOpeningWR();
        } else if (choice5.equals("b")) {
            theirOpeningWR();
        } else {
            menu();
        }
    }

    //EFFECTS: shows the user the win rate for the opening given.
    public void myOpeningWR() {
        System.out.println("Type the opening that you played you want to see the win rate of.");
        String n = input.nextLine();
        System.out.println("Your win rate with " + n + " is " + gl.myOpeningWinRate(n) + "%.");
        afterMWR();
    }

    //EFFECTS: asks user if they want to check win rates for other openings or go back
    public void afterMWR() {
        System.out.println("If you would like to check win rates with other openings, type 'a'.");
        System.out.println("If you would like to go back to statistics menu, type 's'.");
        String choice6 = input.nextLine();

        while (!(choice6.equals("a") || choice6.equals("s"))) {
            System.out.println("That's not a valid input, please try again.");
            choice6 = input.nextLine();
        }

        if (choice6.equals("a")) {
            myOpeningWR();
        } else {
            pickedViewStats();
        }
    }

    //EFFECTS: shows the user the win rate against the opening given.
    public void theirOpeningWR() {
        System.out.println("Type the opening that you played against which you want to see the win rate of.");
        String n = input.nextLine();
        System.out.println("Your win rate against " + n + " is " + gl.theirOpeningWinRate(n) + "%.");
        afterTWR();
    }

    //EFFECTS: asks user if they want to check win rates against other openings or go back
    public void afterTWR() {
        System.out.println("If you would like to check win rates against other openings, type 'a'.");
        System.out.println("If you would like to go back to statistics menu, type 's'.");
        String choice7 = input.nextLine();

        while (!(choice7.equals("a") || choice7.equals("s"))) {
            System.out.println("That's not a valid input, please try again.");
            choice7 = input.nextLine();
        }

        if (choice7.equals("a")) {
            theirOpeningWR();
        } else {
            pickedViewStats();
        }
    }

    //Tells user that the input was not valid
    public void pickNotValid() {
        System.out.println("That input is not valid.");
        menu();
    }

    //EFFECTS: prompts user if they want to save the GameList to the file
    public void quit() {
        System.out.println("Would you like to save your added Games to file?");
        System.out.println("enter 'y' if you would like to, 'n' if not.");
        String choice8 = input.nextLine();

        while (!(choice8.equals("y") || choice8.equals("n"))) {
            System.out.println("That's not a valid input, please try again.");
            choice8 = input.nextLine();
        }

        if (choice8.equals("y")) {
            saveGameList();
            System.out.println("Thank you for using the chess opening analyzer!");
        } else {
            System.out.println("Thank you for using the chess opening analyzer!");
        }
    }



    // MODIFIES: this
    // EFFECTS: loads GameList from file
    private void loadWorkRoom() {
        try {
            gl = jsonReader.read();
            System.out.println("Successfully Loaded " + gl.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the GameList to file
    private void saveGameList() {
        try {
            jsonWriter.open();
            jsonWriter.write(gl);
            jsonWriter.close();
            System.out.println("Successfully Saved " + gl.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }




}
