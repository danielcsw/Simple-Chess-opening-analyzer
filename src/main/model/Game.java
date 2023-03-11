package model;

import org.json.JSONObject;
import persistence.Writable;

    // Represents single chess game, that has the color I played as, opening that I played, opponent played, and result.
public class Game implements Writable {

    private String color;                      // color that I played
    private String myOpening;                  // opening that I played
    private String theirOpening;               // opening opponent played
    private String result;                     // win, loss, or draw

    //Requires: color, myOpening, theirOpening, and state are all non-zero length
    //Effects: Game has given color, myOpening, theirOpening, and result.
    public Game(String color, String myOpening, String theirOpening, String state) {

        this.color = color;
        this.myOpening = myOpening;
        this.theirOpening = theirOpening;
        this.result = state;

    }

    // EFFECTS: returns Color
    public String getColor() {
        return color;
    }

    // EFFECTS: returns myOpening
    public String getMyOpening() {
        return myOpening;
    }

    // EFFECTS: returns TheirOpening
    public String getTheirOpening() {
        return theirOpening;
    }

    // EFFECTS: returns result
    public String getResult() {
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("color", color);
        json.put("my opening", myOpening);
        json.put("opponents opening", theirOpening);
        json.put("result", result);
        return json;
    }
}
