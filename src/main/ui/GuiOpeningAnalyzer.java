package ui;

import javax.swing.*;

import model.Game;
import model.GameList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

// EFFECTS: runs the GUI of the project. creates important objects that is used by the project
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
    int removingNumber;

    JFrame frame = new JFrame("Opening Analyzer");
    JPanel panel = new JPanel();
    JLabel colorLabel = new JLabel("which color did you play as?");
    JLabel currentColor = new JLabel("none");
    JLabel currentResult = new JLabel("none");
    JLabel myOpeningLabel = new JLabel("what opening did you play?");
    JLabel theirOpeningLabel = new JLabel("what opening did your opponent play?");
    JLabel resultLabel = new JLabel("Did you win, lose, or draw?");
    JLabel message = new JLabel();
    JLabel amountOfGames = new JLabel();
    JPanel gamesPanel = new JPanel();

    DefaultListModel<String> l1 = new DefaultListModel<>();
    JList<String> myGamelist;

    ImageIcon chessImage;

    JButton whitebutton = new JButton(new AbstractAction("White") {
        @Override
        public void actionPerformed(ActionEvent e) {
            color = "white";
            currentColor.setText(color);
        }
    });
    JButton blackbutton = new JButton(new AbstractAction("Black") {
        @Override
        public void actionPerformed(ActionEvent e) {
            color = "black";
            currentColor.setText(color);
        }
    });

    JButton wonbutton = new JButton(new AbstractAction("Won") {
        @Override
        public void actionPerformed(ActionEvent e) {
            result = "won";
            currentResult.setText(result);
        }
    });
    JButton lostbutton = new JButton(new AbstractAction("Lost") {
        @Override
        public void actionPerformed(ActionEvent e) {
            result = "lost";
            currentResult.setText(result);
        }
    });
    JButton drewbutton = new JButton(new AbstractAction("Drew") {
        @Override
        public void actionPerformed(ActionEvent e) {
            result = "drew";
            currentResult.setText(result);
        }
    });

    JButton confirmbutton = new JButton(new AbstractAction("Confirm") {
        @Override
        public void actionPerformed(ActionEvent e) {
            confirmAction();
        }
    });


    JTextField inputMyOpening = new JTextField(30);
    JTextField inputTheirOpening = new JTextField(30);

    JLabel removeLabel = new JLabel("Remove games:");
    JButton removebutton = new JButton(new AbstractAction("Remove Games") {
        @Override
        public void actionPerformed(ActionEvent e) {
            confirmRemove();
        }
    });

    JTextField removeText = new JTextField();
    JLabel message2 = new JLabel();

    //EFFECTS: constructor for gui. Sets up gamelist, frame, json, and implements popup window for saving data.
    public GuiOpeningAnalyzer() {
        gl = new GameList("Users' Games");
        frame.setSize(900, 900);
        frame.setResizable(false);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

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

    // EFFECTS: Runs the important methods that all implement the gui.
    public void rungui() {
        colorStuff();
        openingStuff();
        resultStuff();
        removeStuff();
        imageStuff();

        confirmbutton.setBounds(200, 300, 80, 35);
        confirmbutton.addActionListener(this);
        message.setBounds(2, 320, 200, 20);
        startUp();

    }

    //EFFECTS: implements popup window for loading data.
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
        amountOfGames.setBounds(10, 400, 200, 20);
    }

    //EFFECTS: implements button guis for adding color.
    public void colorStuff() {
        currentColor.setBounds(350, 10, 50, 20);
        colorLabel.setBounds(2, 10, 200, 20);
        whitebutton.setBounds(180, 10, 70, 20);
        whitebutton.addActionListener(this);
        blackbutton.setBounds(260, 10, 70,20);
        blackbutton.addActionListener(this);

        panel.add(colorLabel);
        panel.add(whitebutton);
        panel.add(blackbutton);
        panel.add(currentColor);
    }

    //EFFECTS: implements button guis for adding openings.
    public void openingStuff() {
        myOpeningLabel.setBounds(2, 70, 200, 20);
        inputMyOpening.setBounds(10, 100, 250, 20);
        theirOpeningLabel.setBounds(2, 130, 300, 20);
        inputTheirOpening.setBounds(10, 160, 250, 20);

        panel.add(myOpeningLabel);
        panel.add(inputMyOpening);
        panel.add(theirOpeningLabel);
        panel.add(inputTheirOpening);
    }

    //EFFECTS: implements button guis for adding result.
    public void resultStuff() {
        currentResult.setBounds(250, 220, 50, 20);
        wonbutton.setBounds(10, 220, 70, 20);
        wonbutton.addActionListener(this);
        lostbutton.setBounds(80, 220, 70, 20);
        lostbutton.addActionListener(this);
        drewbutton.setBounds(150, 220, 70, 20);
        drewbutton.addActionListener(this);
        resultLabel.setBounds(2, 190, 200, 20);

        panel.add(resultLabel);
        panel.add(wonbutton);
        panel.add(lostbutton);
        panel.add(drewbutton);
        panel.add(currentResult);
    }

    //EFFECTS: implements guis for removing games.
    public void removeStuff() {
        removeLabel.setBounds(500, 10, 100, 20);
        removeText.setBounds(500, 50, 50, 20);
        removebutton.setBounds(500, 100, 150, 20);
        removebutton.addActionListener(this);
        message2.setBounds(500, 130, 150, 20);

        panel.add(removeLabel);
        panel.add(removeText);
        panel.add(removebutton);
        panel.add(message2);
    }

    //  //EFFECTS: implements guis for main panel. Runs the method for viewing games.
    public void addToPanel() {
        gameListStuff();
        panel.setLayout(null);
        panel.add(confirmbutton);
        panel.add(message);
        amountOfGames.setText("you've played " + gl.length() + " Games so far");
        panel.add(amountOfGames);

        frame.add(panel);
        frame.setVisible(true);
    }

    //EFFECTS: implements system for viewing all games in the list.
    public void gameListStuff() {

        int count = 0;
        LinkedList<Game> list = gl.getList();

        for (Game g : list) {
            count = count + 1;
            l1.addElement("Game" + count + ": played as " + g.getColor() + ", played " + g.getMyOpening()
                    + " against " + g.getTheirOpening() + ". " + g.getResult());
        }
        myGamelist = new JList<>(l1);

        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(myGamelist);
        myGamelist.setLayoutOrientation(JList.VERTICAL);
        gamesPanel.add(new JLabel("List of all games"));
        gamesPanel.setBounds(400, 400, 450, 300);
        gamesPanel.add(scrollPane);
        panel.add(gamesPanel);
    }

    public void imageStuff() {
        chessImage = new ImageIcon("./data/chess.jpg");
        chessImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(chessImage);
        imageLabel.setBounds(20, 400, 400, 500);
        panel.add(imageLabel);
    }


    //Empty method that is needed for actionlistner.
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    //EFFECTS: checks if all inputs are properly filled in before proceeding to adding games.
    public void confirmAction() {
        myOpening = inputMyOpening.getText();
        theirOpening = inputTheirOpening.getText();

        if (currentColor.getText() == "none" || inputMyOpening.getText().isEmpty()
                || inputTheirOpening.getText().isEmpty() || currentResult.getText() == "none") {
            message.setText("Please input all values");
        } else {
            addToSystem();
            message.setText("");
            currentColor.setText("none");
            currentResult.setText("none");
        }
    }

    //EFFECTS: checks if all inputs are properly filled in before proceeding to removing games.
    public void confirmRemove() {

        try {
            removingNumber = Integer.parseInt(removeText.getText());
        } catch (NumberFormatException e) {
            message2.setText("That game doesn't exist.");
        }

        try {
            if (!gl.checkIndex(removingNumber) || removeText.getText().isEmpty()) {
                message2.setText("That game doesn't exist.");
            } else {
                removeFromSystem();
                message2.setText("");
            }
        } catch (IndexOutOfBoundsException e) {
            message2.setText("That game doesn't exist.");
        }
    }

    // EFFECTS: Adds game to the list.
    public void addToSystem() {
        game = new Game(color, myOpening, theirOpening, result);
        gl.addGame(game);
        amountOfGames.setText("you've played " + gl.length() + " Games so far");

        l1.addElement("Game" + gl.length() + ": played as " + color + ", played " + myOpening
                + " against " + theirOpening + ". " + result);
    }


    //EFFECTS: removes game from the list.
    public void removeFromSystem() {
        gl.removeGame(removingNumber);
        amountOfGames.setText("you've played " + gl.length() + " Games so far");
        l1.removeElementAt(removingNumber - 1);

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


