package ui;

import model.Game;
import model.GameList;

import java.util.LinkedList;
import java.util.Scanner;

public class OpeningsAnalyzer {

    Scanner input = new Scanner(System.in);
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

    //EFFECTS: shows user how many games were played, and also takes user input
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
            pickedViewStats();
        } else if (choice.equals("q")) {
            pickedQuit();
        } else {
            pickNotValid();
        }

    }

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

    public void nowAddGame() {
        game = new Game(color, myOpening, theirOpening, result);
        gl.addGame(game);
        System.out.println("Would you like to keep adding or go back to menu?");
        System.out.println("Type 'a' to keep adding or 'm' to go back.");
        whatNext();

    }

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



    public void viewGames() {
        System.out.println("-----------------------------------------------------------------------------------------");
        int count = 0;
        LinkedList<Game> list = gl.getList();

        for (Game g : list) {
            count = count + 1;
            System.out.println("Game" + count + ": played as " + g.getColor() + ", played " + g.getMyOpening()
                    + " against " + g.getTheirOpening() + ". " + g.getResult());
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        removeOrGoBack();
    }

    public void removeOrGoBack() {
        System.out.println("Type 'm' to go back to menu. ");
        System.out.println("If you would like to remove a game, type r.");
        String choice = input.nextLine();
        if (choice.equals("m")) {
            menu();
        } else if (choice.equals("r")) {
            System.out.println("You are now removing a game from the list.");
            remove();
        } else {
            removeOrGoBack();
        }
    }

    public void remove() {
        System.out.println("Type the game number of the game you would like to remove.");
        System.out.println("If you would like to cancel, type 0.");
        int n = input.nextInt();

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

    public void pickedViewStats() {

    }

    public void pickedQuit() {
        System.out.println("Thank you for using the chess opening analyzer!");
    }

    public void pickNotValid() {
        System.out.println("That input is not valid.");
        menu();
    }




}
