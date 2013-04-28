package src.com.godplayschess.jacal;

import java.awt.*;
import java.util.ArrayList;

public class GameData {


    public static Map map = new Map();

    public final static Color SELECTED_OBJECT = new Color(255, 165, 25);

    public static Team Team1 = new Team(1);
    public static Team Team2 = new Team(2);

    public static ArrayList<Coin> Coins = new ArrayList<Coin>();

    private static int tours_made = 0;

    public static void nextTurn(){
        tours_made++;
    }

    public static int getTours_made(){
        return tours_made;
    }



}
