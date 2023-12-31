package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

//Represents a list of games to be handled by the OpeningsAnalyzer.
//No maximum size.
public class GameList implements Writable {

    private String name;
    private LinkedList<Game> list;

    //EFFECTS: constructs GameList that has a name as an argument.
    public GameList(String name) {
        this.name = name;
        list = new LinkedList<Game>();
    }

    //MODIFIES: this
    //EFFECTS: adds game to the list
    public void addGame(Game game) {
        list.add(game);
        EventLog.getInstance().logEvent(new Event("Added Game " + length() + ": played as "
                + game.getColor() + ", played " + game.getMyOpening() + " against " + game.getTheirOpening()
                + ". " + game.getResult()));
    }


    //EFFECTS: returns true if given index exists in the list, otherwise returns false
    public Boolean checkIndex(int index) {
        if (index - 1 >= list.size() || index < 0) {
            return false;
        } else {
            return true;
        }
    }

    //REQUIRES: given index must exist in the list (positive integer, and is less than the size of the list).
    //MODIFIES: this
    //EFFECTS: returns the game with the given index
    public Game getGame(int index) {
        return list.get(index - 1);
    }

    //REQUIRES: given index must exist in the list (positive integer, and is less than the size of the list).
    //MODIFIES: this
    //EFFECTS: removes the game with the given index
    public void removeGame(int index) {
        EventLog.getInstance().logEvent(new Event("Removed Game " + index + ": played as "
                + getGame(index).getColor() + ", played " + getGame(index).getMyOpening()
                + " against " + getGame(index).getTheirOpening() + ". " + getGame(index).getResult()));
        list.remove(index - 1);
    }

    //returns the size of the list.
    public int length() {
        return list.size();
    }

    //EFFECTS: returns the overall winrate percentage, with 2 decimal places
    public double ovrlWinRate() {
        double wins = 0;

        for (Game g : list) {
            if (g.getResult().equals("won")) {
                wins = wins + 1;
            }
        }
        double winrate = (double) 100 * (wins / list.size());
        return Math.round(winrate * 100.0) / 100.0;
    }


    //EFFECTS: returns the win rate of the games with given MyOpening.
    public double myOpeningWinRate(String myOpening) {
        LinkedList<Game> filteredList = new LinkedList<Game>();
        double wins = 0;

        for (Game g : list) {
            if (g.getMyOpening().equals(myOpening)) {
                filteredList.add(g);
            }
        }

        for (Game f : filteredList) {
            if (f.getResult().equals("won")) {
                wins = wins + 1;
            }
        }
        double winrate = (double) 100 * (wins / filteredList.size());
        return Math.round(winrate * 100.0) / 100.0;
    }

    //EFFECTS: returns the win rate of the games with given theirOpening.
    public double theirOpeningWinRate(String theirOpening) {
        LinkedList<Game> filteredList = new LinkedList<Game>();
        double wins = 0;

        for (Game g : list) {
            if (g.getTheirOpening().equals(theirOpening)) {
                filteredList.add(g);
            }
        }

        for (Game f : filteredList) {
            if (f.getResult().equals("won")) {
                wins = wins + 1;
            }
        }
        double winrate = (double) 100 * (wins / filteredList.size());
        return Math.round(winrate * 100.0) / 100.0;
    }

    //EFFECTS: returns a list of game, so that openingsAnalyzer can handle it in loops
    public LinkedList<Game> getList() {
        return list;
    }

    //EFFECTS: returns the name of the GameList
    public String getName() {
        return name;
    }

    // writes new json object so that the games can be saved as json.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("Games", gamesToJson());
        return json;
    }

    // EFFECTS: returns games in this gamelist as a JSON array
    private JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game g : list) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }
}


