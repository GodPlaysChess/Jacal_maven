package src.com.godplayschess.jacal;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Screen extends JFrame {
    public static final int D_WIDTH = 1280;
    public static final int D_HEIGHT = 860;
    private TextFormatter TF = new TextFormatter();


    public Screen() {
        super();
        setTitle("Jacal");
        setSize(D_WIDTH, D_HEIGHT);
        setLocation(200, 100);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        createBufferStrategy(2);
    }

    public void render() {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics2D g = null;

        try {
            g = (Graphics2D) bf.getDrawGraphics();
            g.clearRect(0, 0, D_WIDTH, D_HEIGHT);

            GameData.map.render(g);

            GameData.Team1.render(g);
            GameData.Team2.render(g);
            renderCoins(g);

            renderStatistics(g);
            renderWin(g);
            //   renderMouse(g);
        } finally {
            if (g != null)
                g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    private void renderMouse(Graphics2D g) {
        try {
            g.setColor(Color.BLACK);
            int mouseX = getMousePosition().x;
            int mouseY = getMousePosition().y;

            if (mouseX >= 0 && mouseX <= D_WIDTH && mouseY >= 0 && mouseY <= D_HEIGHT) {
                int mapX = (mouseX - Map.MAP_XSHIFT) / Map.FIELD_SIZE;
                int mapY = (mouseY - Map.MAP_YSHIFT) / Map.FIELD_SIZE;
                g.drawString("X = " + mapX + ", Y = " + mapY, mouseX, mouseY);
            }
        } catch (java.lang.NullPointerException npe) {
        }
    }

    private void renderStatistics(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 42));
        g.drawString("SCORE", 1000, 100);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Purple           Blue", 930, 160);
        g.drawString(GameData.Team1.getScore() + "", 980, 200);
        g.drawString(GameData.Team2.getScore() + "", 1180, 200);
        renderHelp(g);
        renderControls(g);
    }

    private void renderControls(Graphics2D g) {
        g.drawString("Numpad or mouse to move", 900, 650);
        g.drawString("Tab to choose pirate", 900, 680);
        g.drawString("<T>ake a coin", 900, 710);
        g.drawString("<K>ill a pirate if necessary", 900, 740);

        g.setFont(new Font("Arial", Font.ROMAN_BASELINE, 11));
        g.drawString("developed by G. Parakhonskiy", 1120, 850);

    }

    private void renderWin(Graphics2D g) {
        g.setFont(new Font("Arial", Font.ITALIC, 38));
        g.setColor(Color.BLUE);
        if (GameData.Team1.isWon() == 1)
            g.drawString("PURPLE WINS", 1000, 500);
        if (GameData.Team2.isWon() == 1)
            g.drawString("BLUE WINS", 1000, 500);
    }


    private void renderHelp(Graphics2D g) {

        String text = "";
        String headings = "";


        switch (tileMouseOn()) {
            case (Tile.UNKNOWN):
                headings = "UNKNOWN";
                text = "Go here, and you may find something useful";
                break;
            case (Tile.WATER):
                headings = "WATER";
                text = "You drown in. The only way is to get out of water " +
                        "is through the boat. " +
                        "Be sure that it's yours or you die." +
                        " Coin, dropped in water    sinks to the very deep of the ocean without any chance to have it back";
                break;
            case (Tile.ARROW1):
            case (Tile.ARROW2):
            case (Tile.ARROW3):
            case (Tile.ARROW4):
                headings = "ARROW";
                text = "Follow the white rabbit";
                break;
            case (Tile.FOREST):
                headings = "FOREST";
                text = "Since pirates embark on this island, " +
                        "nobody lives in the forest anymore";
                break;
            case (Tile.HOLE):
                headings = "PIT";
                text = "Pirates fell in a trap could not get out    without help. Once the other pirate" +
                        " comes to the pit, both are released";
                break;
            case (Tile.CROCODILE):
                headings = "CROCODILE";
                text = "Go back. Do not forget to check what happens with the coin";
                break;
            case (Tile.RHUM):
                headings = "RUM";
                text = "To become sober, pirate spends X turns,     where X is the amount of currently living   pirates";
                break;
            case (Tile.PARACHUTE):
                headings = "PARACHUTE";
                text = "Teleport to your ship.. with the coin..     even if you do not want it";
                break;
            case (Tile.CANNON):
                headings = "CANNON";
                text = "Fly to the very edge of the world in a      cannon direction";
                break;
            case (Tile.CANNIBAL):
                headings = "CRAZY";
                text = "This crazy beaver does not make any sense,  but you will die anyway if he finds you";
                break;
            case (Tile.PLANE):
                headings = "PLANE";
                text = "You can use the plane to fly to any field on the map, but only once. To use " +
                        "plane press <R>. If you want to take all coins with you, press <T> after.";
                break;
            case (Tile.USED_PLANE):
                headings = "USELESS PLANE";
                text = "You missed your chance";
                break;
            case (Tile.HORSE):
                headings = "HORSE";
                text = "Jump like a Horse";
                break;
            case (Tile.FORT):
            case (Tile.FORT_GIRL):
                headings = "FORT";
                text = "You could not be attacked inside the fort.  If there is a heart, you could resurrect    dead pirate by pressing <R>";
                break;
            case (Tile.CHEST1):
            case (Tile.CHEST2):
            case (Tile.CHEST3):
            case (Tile.CHEST4):
            case (Tile.CHEST1_OPENED):
            case (Tile.CHEST2_OPENED):
            case (Tile.CHEST3_OPENED):
            case (Tile.CHEST4_OPENED):

                headings = "TREASURE CHEST";
                text = "That's why you are here. Take the coins     (<T>) and go back to your ship";
                break;
            case (Tile.CHEST5):
            case (Tile.CHEST5_OPENED):
                headings = "TREASURE CHEST";
                text = "That's why you are here. Take the coins     (<T>) and go back to your ship" +
                        "Congratz!      A big one!";
                break;
            case (Tile.ICE_2X):
                headings = "ICE";
                text = "Ice here is another mystery. Slide in same   direction";
                break;
            case (Tile.JUNGLE):
                headings = "JUNGLE";
                text = "There are two fields in Jungle, and only one direction to go. " +
                        "Walk through to get out";
                break;
            case (Tile.DESERT):
                headings = "DESERT";
                text = "There are three fields in Desert, and only  one direction to go. " +
                        "Walk through to get out";

                break;
            case (Tile.SWAMP):
                headings = "SWAMP";
                text = "There are four fields in Swamp, and only one direction to go." +
                        "Walk through to get out";
                break;
            case (Tile.WATERFALL):
                headings = "MOUNTAINS";
                text = "There are five fields in Mountains, and only one direction to go." +
                        "Walk through to get out";

                break;
        }
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString(headings, 900, 300);

        TF.writeText(17, 900, 1650, 350, text, g);
    }

    private int tileMouseOn() {
        try {
            return GameData.map.getTileType((getMousePosition().x - Map.MAP_XSHIFT) / Map.FIELD_SIZE, (getMousePosition().y - Map.MAP_YSHIFT) / Map.FIELD_SIZE);
        } catch (java.lang.NullPointerException E) {

        }
        return -1;
    }

    //DO NOT CORRECTLY WORKS IN JUNGLE!!!
    private void renderCoins(Graphics2D g) {
        for (int i = 0; i < GameData.Coins.size(); i++) {
            if (!GameData.Coins.get(i).is_selected)
                GameData.Coins.get(i).render(g);
        }
        for (int i = 0; i < GameData.Coins.size(); i++) {
            if (GameData.Coins.get(i).is_selected)
                GameData.Coins.get(i).render(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        for (int i = 0; i < GameData.map.Map.length; i++) {
            for (int j = 0; j < GameData.map.Map.length; j++) {
                if (GameData.map.Map[i][j].getCoinsNumber() > 1 && GameData.map.Map[i][j].getCoinsNumber() < 10) {
                    g.drawString(GameData.map.Map[i][j].getCoinsNumber() + "", Map.getXCoordinate(i) + Coin.RADII / 4 + 1, Map.getYCoordinate(j) + 4 * Coin.RADII / 5 + 1);

                }
                if (GameData.map.Map[i][j].getCoinsNumber() >= 10)
                    g.drawString(GameData.map.Map[i][j].getCoinsNumber() + "", Map.getXCoordinate(i) + Coin.RADII / 4 - 3, Map.getYCoordinate(j) + 4 * Coin.RADII / 5 + 1);
            }
        }

    }


}
