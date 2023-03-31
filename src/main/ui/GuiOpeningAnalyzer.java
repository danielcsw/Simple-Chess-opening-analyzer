package ui;

import javax.swing.*;

import model.Game;
import model.GameList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class GuiOpeningAnalyzer extends JFrame implements ActionListener {


    private static final String JSON_STORE = "./data/myFile.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private GameList gl;
    private Game game;
    private String color;
    private String myOpening;
    private String theirOpening;
    private String result;

    JFrame frame = new JFrame("Adding games.");
    JPanel panel = new JPanel();
    JLabel colorlabel = new JLabel("which color did you play as?");
    JLabel currentcolor = new JLabel(color);
    JLabel currentResult = new JLabel(result);
    JLabel myOpeningLabel = new JLabel("what opening did you play?");
    JLabel theirOpeningLabel = new JLabel("what opening did your opponent play?");
    JLabel resultLabel = new JLabel("Did you win, lose, or draw?");
    JLabel message = new JLabel();

    JLabel amountOfGames = new JLabel();


    JButton whitebutton = new JButton("White");
    JButton blackbutton = new JButton("Black");

    JButton wonbutton = new JButton("Won");
    JButton lostbutton = new JButton("Lost");
    JButton drewbutton = new JButton("Drew");

    JButton confirmbutton = new JButton("Confirm");

    JTextField inputMyOpening = new JTextField(30);
    JTextField inputTheirOpening = new JTextField(30);


    public GuiOpeningAnalyzer() {
        gl = new GameList("Users' Games");
        frame.setSize(900, 900);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Do you want to save before exiting?", "Save before close?",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    saveGameList();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else if (result == JOptionPane.NO_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
        rungui();

    }

    public void rungui() {
        colorStuff();
        openingStuff();
        resultStuff();

        confirmbutton.setBounds(200, 300, 80, 35);
        confirmbutton.addActionListener(this);
        message.setBounds(2, 320, 200, 20);
        startUp();
    }



    public void startUp() {
        int result = JOptionPane.showConfirmDialog(frame,
                "Do you want to load your games?", "Load games?",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            loadGames();
            addToPanel();
        } else if (result == JOptionPane.NO_OPTION) {
            addToPanel();
        }
        amountOfGames.setBounds(400, 200, 200, 20);
    }





    public void addToPanel() {
        panel.setLayout(null);
        panel.add(colorlabel);
        panel.add(whitebutton);
        panel.add(blackbutton);
        panel.add(currentcolor);
        panel.add(myOpeningLabel);
        panel.add(inputMyOpening);
        panel.add(theirOpeningLabel);
        panel.add(inputTheirOpening);
        panel.add(resultLabel);
        panel.add(wonbutton);
        panel.add(lostbutton);
        panel.add(drewbutton);
        panel.add(currentResult);
        panel.add(confirmbutton);
        panel.add(message);
        amountOfGames.setText("you've played " + gl.length() + " Games so far");
        panel.add(amountOfGames);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void colorStuff() {
        currentcolor.setBounds(350, 10, 50, 20);
        colorlabel.setBounds(2, 10, 200, 20);
        whitebutton.setBounds(180, 10, 70, 20);
        whitebutton.addActionListener(this);
        blackbutton.setBounds(260, 10, 70,20);
        blackbutton.addActionListener(this);
    }

    public void openingStuff() {
        myOpeningLabel.setBounds(2, 70, 200, 20);
        inputMyOpening.setBounds(10, 100, 250, 20);
        theirOpeningLabel.setBounds(2, 130, 300, 20);
        inputTheirOpening.setBounds(10, 160, 250, 20);


    }

    public void resultStuff() {
        currentResult.setBounds(250, 220, 50, 20);
        wonbutton.setBounds(10, 220, 70, 20);
        wonbutton.addActionListener(this);
        lostbutton.setBounds(80, 220, 70, 20);
        lostbutton.addActionListener(this);
        drewbutton.setBounds(150, 220, 70, 20);
        drewbutton.addActionListener(this);
        resultLabel.setBounds(2, 190, 200, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == whitebutton) {
            color = "white";
            currentcolor.setText(color);
        }
        if (e.getSource() == blackbutton) {
            color = "black";
            currentcolor.setText(color);
        }
        if (e.getSource() == wonbutton) {
            result = "won";
            currentResult.setText(result);
        }
        if (e.getSource() == lostbutton) {
            result = "lost";
            currentResult.setText(result);
        }
        if (e.getSource() == drewbutton) {
            result = "drew";
            currentResult.setText(result);
        }
        if (e.getSource() == confirmbutton) {
            confirmAction();
        }
    }

    public void confirmAction() {
        myOpening = inputMyOpening.getText();
        theirOpening = inputTheirOpening.getText();

        if (color == "none" || myOpening == null || theirOpening == null || result == "none") {
            message.setText("Please input all values");
        } else {
            addToSystem();
        }
    }


    public void addToSystem() {
        game = new Game(color, myOpening, theirOpening, result);
        gl.addGame(game);
        amountOfGames.setText("you've played " + gl.length() + " Games so far");

    }






    // MODIFIES: this
    // EFFECTS: loads GameList from file
    private void loadGames() {
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


