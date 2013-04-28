package src.com.godplayschess.jacal;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Screen extends JFrame {
    public static final int D_WIDTH = 1280;
    public static final int D_HEIGHT = 860;


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

            //draw elements here
            renderStatistics(g);
            renderMouse(g);
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
/*        for (int i = 0; i < src.com.godplayschess.jacal.GameData.Team1.TeamObjects.size(); i++) {
            boolean a123 = src.com.godplayschess.jacal.GameData.Team1.TeamObjects.get(i).is_selected;
            g.drawString("T1 src.com.godplayschess.jacal.Pirate " + i + " is selected   " + a123, 1000, 100 + 50 * i);
        }

        for (int i = 0; i < src.com.godplayschess.jacal.GameData.Team2.TeamObjects.size(); i++) {
            boolean a123 = src.com.godplayschess.jacal.GameData.Team2.TeamObjects.get(i).is_selected;
            g.drawString("T2 src.com.godplayschess.jacal.Pirate " + i + " is selected   " + a123, 1000, 300 + 50 * i);
        }*/

        g.drawString(GameData.Team1.getScore()+"", 980, 200);
        g.drawString(GameData.Team2.getScore()+"", 1180, 200);
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
                if (GameData.map.Map[i][j].getCoinsNumber() > 1 && GameData.map.Map[i][j].getCoinsNumber() < 10){
                    g.drawString(GameData.map.Map[i][j].getCoinsNumber() + "", Map.getXCoordinate(i) + Coin.RADII / 4 + 1, Map.getYCoordinate(j) + 4 * Coin.RADII / 5 + 1);

                }
                if (GameData.map.Map[i][j].getCoinsNumber() >= 10)
                    g.drawString(GameData.map.Map[i][j].getCoinsNumber() + "", Map.getXCoordinate(i) + Coin.RADII / 4 - 3, Map.getYCoordinate(j) + 4 * Coin.RADII / 5 + 1);
            }
        }

    }


}
