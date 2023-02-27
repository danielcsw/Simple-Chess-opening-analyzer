package ui;

import model.Game;
import model.GameList;

import java.util.LinkedList;
import java.util.Scanner;

public class OpeningsAnalyzer {

    Scanner input = new Scanner(System.in);
    Scanner input2 = new Scanner(System.in);    //made 2 scanners because there was a bug in some methods when using 1
    private GameList gl;
    private Game game;
    private String color;
    private String myOpening;
    private String theirOpening;
    private String result;


    // EFFECTS: runs the OpeningAnalyzer
    public OpeningsAnalyzer() {

        gl = new GameList();
        runOpeningsAnalyzer();
    }

    // EFFECTS: initializes gameList, and introduces the user to the application
    public void runOpeningsAnalyzer() {
        gl = new GameList();
        System.out.println("_________________________________________________________________________________________");
        System.out.println("_________________________________________________________________________________________");
        System.out.println("Hello! Welcome to the chess opening analyzer. With this application, you can manually add");
        System.out.println("chess games that you have played, and see various statistics on your openings.");
        System.out.println("_________________________________________________________________________________________");
        System.out.println("_________________________________________________________________________________________");
        menu();
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
        String choice = input.nextLine();

        if (choice.equals("a")) {
            System.out.println("You're now adding a game to the system.");
            pickedAddGame();
        } else if (choice.equals("b")) {
            System.out.println("You are now viewing all your games.");
            viewGames();
        } else if (choice.equals("c")) {
            System.out.println("You are now viewing your statistics.");
            pickedViewStats();
        } else if (choice.equals("q")) {
            System.out.println("Thank you for using the chess opening analyzer!");
        } else {
            pickNotValid();
        }
    }

    //EFFECTS: takes the user input for game.
    public void pickedAddGame() {
        System.out.println("What color did you play as?");
        System.out.println("Choices are 'white' or 'black'.");
        color = input.nextLine();

        if (!(color.equals("white") || color.equals("black"))) {
            System.out.println("Sorry, that's not a valid input.");
            pickedAddGame();
        }

        System.out.println("What opening did you use?");
        myOpening = input.nextLine();

        System.out.println("What opening did you opponent play?");
        theirOpening = input.nextLine();

        getTheResult();

        nowAddGame();

    }

    //EFFECTS: gets the result of the game.
    public void getTheResult() {
        System.out.println("Did you win, lose, or draw?");
        System.out.println("Choices are 'won', 'lost', or 'drew'");
        result = input.nextLine();

        if (!(result.equals("won") || result.equals("lost") || result.equals("drew"))) {
            System.out.println("Sorry, that's not a valid input.");
            getTheResult();
        }
        System.out.println("");
        System.out.println("You've now successfully added your game.");

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
        String choice = input.nextLine();;

        if (choice.equals("a")) {
            pickedAddGame();
        } else if (choice.equals("m")) {
            System.out.println("");
            menu();
        } else {
            System.out.println("Sorry, that's not a valid input.");
            whatNext();
        }
    }


    //EFFECTS: shows the user all the games that are in the GameList so far
    public void viewGames() {
        System.out.println("----------------------------------------------------------------------------------------");
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
        String choice = input.next();

        if (choice.equals("m")) {
            menu();
        } else if (choice.equals("r")) {
            System.out.println("You are now removing a game from the list.");
            remove();
        } else {
            System.out.println("That's not a valid input.");
            removeOrGoBack();
        }
    }

    //EFFECTS: asks user on what game they want to remove, or if they wanna cancel
    public void remove() {
        System.out.println("Type the game number of the game you would like to remove.");
        System.out.println("If you would like to cancel, type 0.");
        int n = input2.nextInt();

        if (n == 0) {
            System.out.println("Cancelled removing game.");
            viewGames();
        } else if (!(gl.checkIndex(n))) {
            System.out.println("That game does not exist.");
            remove();
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
        String choice = input.nextLine();

        if (choice.equals("a")) {
            myOpeningWR();
        } else if (choice.equals("b")) {
            theirOpeningWR();
        } else if (choice.equals("m")) {
            menu();
        } else {
            System.out.println("That's not a valid input.");
            pickedViewStats();
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
        String x = input.nextLine();

        if (x.equals("a")) {
            myOpeningWR();
        } else if (x.equals("s")) {
            pickedViewStats();
        } else {
            System.out.println("That's not a valid input.");
            afterMWR();
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
        String x = input.nextLine();

        if (x.equals("a")) {
            theirOpeningWR();
        } else if (x.equals("s")) {
            pickedViewStats();
        } else {
            System.out.println("That's not a valid input.");
            afterTWR();
        }
    }

    //Tells user that the input was not valid
    public void pickNotValid() {
        System.out.println("That input is not valid.");
        menu();

    }
}
