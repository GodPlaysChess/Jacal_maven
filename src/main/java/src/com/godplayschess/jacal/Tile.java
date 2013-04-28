package src.com.godplayschess.jacal;

import com.sun.imageio.plugins.common.InputStreamAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Tile {
    public final int type;



    private int coins;

    public static final int INDEFINITE = -1;

    public static final int UNKNOWN = 0;
    public static final int WATER = 1;
    public static final int FOREST = 2;
    public static final int HOLE = 3;
    public static final int CROCODILE = 4;
    public static final int RHUM = 6;
    public static final int PARACHUTE = 7;
    public static final int CANNON = 8;
    public static final int CANNIBAL = 9;
    public static final int PLANE = 10;
    public static final int USED_PLANE = 11;
    public static final int HORSE = 12;

    public static final int FORT = 51;
    public static final int FORT_GIRL = 52;

    public static final int CHEST1 = 61;
    public static final int CHEST2 = 62;
    public static final int CHEST3 = 63;
    public static final int CHEST4 = 64;
    public static final int CHEST5 = 65;
    public static final int CHEST1_OPENED = 71;
    public static final int CHEST2_OPENED = 72;
    public static final int CHEST3_OPENED = 73;
    public static final int CHEST4_OPENED = 74;
    public static final int CHEST5_OPENED = 75;

    public static final int ARROW1 = 81;
    public static final int ARROW2 = 82;
    public static final int ARROW3 = 83;
    public static final int ARROW4 = 84;
    public static final int ICE_2X = 85;

    public static final int JUNGLE = 92;
    public static final int DESERT = 93;
    public static final int SWAMP = 94;
    public static final int WATERFALL = 95;


    public static final Color UNKNOWN_BACKGROUND = new Color(192, 142, 146);
    public static final Color WATER_BACKGROUND = new Color(137, 183, 255);
    public static final Color FOREST_BACKGROUND = new Color(171, 255, 70);
    public static final Color DARK_FOREST_BACKGROUND = new Color(109, 254, 48);
    public static final Color SWAMP_BACKGROUND = new Color(232, 144, 56);
    public static final Color SAND_BACKGROUND = new Color(255, 251, 136);
    private static final Color DIRT_BACKGROUND = new Color(104, 70, 2);

    public Tile(int type) {
        this.type = type;
        coins = 0;
    }

    public Tile(int type, int coinsnumber) {
        this.type = type;
        coins = coinsnumber;
    }

    public Tile() {
        type = UNKNOWN;
    }

    public void render(Graphics2D g, int positionX, int positionY) {
        switch (type) {
            case UNKNOWN:
                renderBackGround(g, UNKNOWN_BACKGROUND, positionX, positionY);
                break;
            case WATER:
                renderBackGround(g, WATER_BACKGROUND, positionX, positionY);
                break;
            case FOREST:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                break;

            case FORT:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderFort(g, positionX, positionY);
                break;
            case FORT_GIRL:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderFort(g, positionX, positionY);
                renderAmazon(g, positionX, positionY);
                break;

            case HOLE:
                renderBackGround(g, SWAMP_BACKGROUND, positionX, positionY);
                renderHole(g, positionX, positionY);
                break;
            case PARACHUTE:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderParachute(g, positionX, positionY);
                break;
            case CANNON:
                renderBackGround(g, DARK_FOREST_BACKGROUND, positionX, positionY);
                renderCannon(g, positionX, positionY);
                break;
            case CANNIBAL:
                renderBackGround(g, SAND_BACKGROUND, positionX, positionY);
                renderCannibal(g, positionX, positionY);
                break;

            case ARROW1:
            case ARROW2:
            case ARROW3:
            case ARROW4:
                renderBackGround(g, SAND_BACKGROUND, positionX, positionY);
                renderArrow(g, positionX, positionY);
                break;

            case ICE_2X:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderIce(g, positionX, positionY);
                break;

            case JUNGLE:
                renderBackGround(g, new Color(70, 153, 91), positionX, positionY);
                renderJungle(g, positionX, positionY);
                break;
            case DESERT:
                renderBackGround(g, SAND_BACKGROUND, positionX, positionY);
                renderDesert(g, positionX, positionY);
                break;
            case SWAMP:
                renderBackGround(g, new Color(63, 15, 255), positionX, positionY);
                renderSwamp(g, positionX, positionY);
                break;
            case WATERFALL:
                renderBackGround(g, new Color(81, 153, 153), positionX, positionY);
                renderWaterfall(g, positionX, positionY);
                break;

            case RHUM:
                renderBackGround(g, SAND_BACKGROUND, positionX, positionY);
                renderRhum(g, positionX, positionY);
                break;


            case CHEST1:
            case CHEST1_OPENED:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderChest(g, positionX, positionY, 1);
                break;
            case CHEST2:
            case CHEST2_OPENED:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderChest(g, positionX, positionY, 2);
                break;
            case CHEST3:
            case CHEST3_OPENED:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderChest(g, positionX, positionY, 3);
                break;
            case CHEST4:
            case CHEST4_OPENED:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderChest(g, positionX, positionY, 4);
                break;
            case CHEST5:
            case CHEST5_OPENED:
                renderBackGround(g, FOREST_BACKGROUND, positionX, positionY);
                renderChest(g, positionX, positionY, 5);
                break;
            case CROCODILE:
                renderBackGround(g, SWAMP_BACKGROUND, positionX, positionY);
                renderCrocodile(g, positionX, positionY);
                break;
            case PLANE:
                renderPlane(g, positionX, positionY);
                break;
            case USED_PLANE:
                renderUsedPlane(g, positionX, positionY);
                break;
            case HORSE:
                renderHorse(g, positionX, positionY);
        }

    }

    private void renderHorse(Graphics2D g, int positionX, int positionY) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/horse.jpg"));
            }
         catch (IOException e) {
            System.out.println("IMAGE IS NOT FOUND");
        }
        g.drawImage(img, positionX, positionY, Map.FIELD_SIZE, Map.FIELD_SIZE, null);
    }

    private void renderPlane(Graphics2D g, int positionX, int positionY) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/plane.jpg"));
        } catch (IOException e) {
            System.out.println("IMAGE IS NOT FOUND");
        }
        g.drawImage(img, positionX, positionY, Map.FIELD_SIZE, Map.FIELD_SIZE, null);
    }

    private void renderUsedPlane(Graphics2D g, int positionX, int positionY) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/plane_used.jpg"));
        } catch (IOException e) {
            System.out.println("IMAGE IS NOT FOUND");
        }
        g.drawImage(img, positionX, positionY, Map.FIELD_SIZE, Map.FIELD_SIZE, null);
    }

    private void renderAmazon(Graphics2D g, int positionX, int positionY) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/heart.jpg"));
        } catch (IOException e) {
            System.out.println("IMAGE IS NOT FOUND");
        }
        int w = img.getWidth() * Map.FIELD_SIZE / (img.getHeight() * 3);
        g.drawImage(img, positionX + 3 * Map.FIELD_SIZE / 10 + 1, positionY + 3 * Map.FIELD_SIZE / 10 + 3, w + 1, Map.FIELD_SIZE / 3, null);


    }

    private void renderCannibal(Graphics2D g, int positionX, int positionY) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/Cannibal_CUT.jpg"));
        } catch (IOException e) {
            System.out.println("IMAGE IS NOT FOUND");
        }
        int w = img.getWidth() * Map.FIELD_SIZE / img.getHeight();
        g.drawImage(img, positionX, positionY, w + 1, Map.FIELD_SIZE + 1, null);
    }

    protected void renderCannon(Graphics2D g, int positionX, int positionY) {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void renderParachute(Graphics2D g, int positionX, int positionY) {
        int c = Map.FIELD_SIZE / 10;
        g.setColor(Color.BLACK);
        g.fillOval(positionX + c - 1, positionY + c - 1, 8 * c + 2, 8 * c + 2);
        g.setColor(new Color(176, 255, 252));
        g.fillOval(positionX + c, positionY + c, 8 * c, 8 * c);
        g.setColor(Color.DARK_GRAY);
        g.drawLine(positionX + 5 * c, positionY + c, positionX + 5 * c, positionY + 9 * c);
        g.drawLine(positionX + c + 1, positionY + 5 * c, positionX + 9 * c, positionY + 5 * c);
        g.setColor(Color.BLACK);
        g.fillOval(positionX + 4 * c - 1, positionY + 4 * c - 1, 2 * c + 2, 2 * c + 2);
        g.setColor(new Color(174, 218, 255));
        g.fillOval(positionX + 4 * c, positionY + 4 * c, 2 * c, 2 * c);


    }

    private void renderRhum(Graphics2D g, int positionX, int positionY) {
        int c = Map.FIELD_SIZE / 10;
        g.setColor(new Color(182, 137, 68));
        g.fillOval(positionX + 2 * c, positionY + 7 * c, 5 * c, 2 * c);
        g.fillRect(positionX + 2 * c, positionY + 3 * c, 5 * c, 5 * c);
        g.setColor(Color.DARK_GRAY);
        g.fillOval(positionX + 2 * c, positionY + 2 * c, 5 * c, 2 * c);
        g.setColor(Color.BLACK);
        g.drawOval(positionX + 2 * c, positionY + 2 * c, 5 * c, 2 * c);
        g.drawLine(positionX + 2 * c, positionY + 3 * c, positionX + 2 * c, positionY + 17 / 2 * c);
        g.drawLine(positionX + 3 * c, positionY + 4 * c, positionX + 3 * c, positionY + 9 * c);
        g.drawLine(positionX + 6 * c, positionY + 4 * c, positionX + 6 * c, positionY + 9 * c);
        g.drawLine(positionX + 7 * c, positionY + 3 * c, positionX + 7 * c, positionY + 17 / 2 * c);
        g.drawArc(positionX + 2 * c, positionY + 7 * c, 5 * c, 2 * c, 0, -180);
        g.drawArc(positionX + 2 * c, positionY + 7 * c + 1, 5 * c, 2 * c, 0, -180);

        g.setColor(new Color(250, 193, 37));
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("RUM", positionX + 2 * c + 2, positionY + 7 * c);

    }

    private void renderWaterfall(Graphics2D g, int positionX, int positionY) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/waterfall1.jpg"));
        } catch (IOException e) {
            System.out.println("IMAGE IS NOT FOUND");
        }
        g.drawImage(img, positionX, positionY, Map.FIELD_SIZE, Map.FIELD_SIZE, null);

    }

    private void renderSwamp(Graphics2D g, int positionX, int positionY) {
        int c = Map.FIELD_SIZE / 10;
        int r = 3 * c;
        g.setColor(new Color(243, 197, 138));
        g.fillOval(positionX + c - 2, positionY + c - 2, r, r);
        g.fillOval(positionX + 7 * c - 2, positionY + 3 * c - 2, r, r);
        g.fillOval(positionX + c - 2, positionY + 5 * c - 2, r, r);
        g.fillOval(positionX + 7 * c - 2, positionY + 7 * c - 2, r, r);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 2 * c));
        g.drawString("I", positionX + 2 * c, positionY + 3 * c);
        g.drawString("II", positionX + 8 * c - 1, positionY + 5 * c);
        g.drawString("III", positionX + 2 * c - 3, positionY + 7 * c);
        g.drawString("IV", positionX + 8 * c - 3, positionY + 9 * c);
    }

    private void renderDesert(Graphics2D g, int positionX, int positionY) {
        g.setColor(new Color(172, 157, 147));
        g.setFont(new Font("Arial", Font.BOLD, Map.FIELD_SIZE / 3));
        g.drawString("I", positionX + Map.FIELD_SIZE / 10, positionY + Map.FIELD_SIZE / 3);
        g.drawString("II", positionX + 4 * Map.FIELD_SIZE / 5, positionY + 6 * Map.FIELD_SIZE / 10);
        g.drawString("III", positionX + Map.FIELD_SIZE / 10, positionY + 9 * Map.FIELD_SIZE / 10);
    }

    protected void renderArrow(Graphics2D g, int positionX, int positionY) {
    }


    private void renderJungle(Graphics2D g, int positionX, int positionY) {
        g.setColor(new Color(240, 217, 113));
        g.setFont(new Font("Arial", Font.BOLD, Map.FIELD_SIZE / 3));
        g.drawString("I", positionX + Map.FIELD_SIZE / 10, positionY + Map.FIELD_SIZE / 3);
        g.drawString("II", positionX + 3 * Map.FIELD_SIZE / 5, positionY + 9 * Map.FIELD_SIZE / 10);
    }

    private void renderIce(Graphics2D g, int positionX, int positionY) {
        g.setColor(Color.BLACK);
        g.fillOval(positionX + Map.FIELD_SIZE / 10 - 2, positionY + 2 * Map.FIELD_SIZE / 10 - 2, 8 * Map.FIELD_SIZE / 10 + 4, 6 * Map.FIELD_SIZE / 10 + 4);
        g.setColor(new Color(48, 255, 231));
        g.fillOval(positionX + Map.FIELD_SIZE / 10, positionY + 2 * Map.FIELD_SIZE / 10, 8 * Map.FIELD_SIZE / 10, 6 * Map.FIELD_SIZE / 10);
        g.setColor(new Color(48, 123, 255));
        g.setFont(new Font("Arial", Font.BOLD, Map.FIELD_SIZE / 2));
        g.drawString("X2", positionX + Map.FIELD_SIZE / 5, positionY + 2 * Map.FIELD_SIZE / 3);
    }

    private void renderHole(Graphics2D g, int positionX, int positionY) {
        g.setColor(new Color(0, 0, 0));
        g.fillOval(positionX + 2 * Map.FIELD_SIZE / 10, positionY + 2 * Map.FIELD_SIZE / 10, 6 * Map.FIELD_SIZE / 10, 6 * Map.FIELD_SIZE / 10);
    }

    private void renderFort(Graphics2D g, int positionX, int positionY) {
        g.setColor(Color.BLACK);
        g.fillOval(positionX + Map.FIELD_SIZE / 10 - 1, positionY + Map.FIELD_SIZE / 10 - 1, Map.FIELD_SIZE / 5 + 2, Map.FIELD_SIZE / 5 + 2);
        g.fillOval(positionX + 7 * Map.FIELD_SIZE / 10 - 1, positionY + Map.FIELD_SIZE / 10 - 1, Map.FIELD_SIZE / 5 + 2, Map.FIELD_SIZE / 5 + 2);
        g.fillOval(positionX + 7 * Map.FIELD_SIZE / 10 - 1, positionY + 7 * Map.FIELD_SIZE / 10 - 1, Map.FIELD_SIZE / 5 + 2, Map.FIELD_SIZE / 5 + 2);
        g.fillOval(positionX + Map.FIELD_SIZE / 10 - 1, positionY + 7 * Map.FIELD_SIZE / 10 - 1, Map.FIELD_SIZE / 5 + 2, Map.FIELD_SIZE / 5 + 2);

        g.setColor(new Color(182, 137, 68));
        g.fillRect(positionX + Map.FIELD_SIZE / 10 + Map.FIELD_SIZE / 20, positionY + Map.FIELD_SIZE / 10 + Map.FIELD_SIZE / 15, Map.FIELD_SIZE / 10, 5 * Map.FIELD_SIZE / 8);
        g.fillRect(positionX + 15 * Map.FIELD_SIZE / 20, positionY + Map.FIELD_SIZE / 10 + Map.FIELD_SIZE / 15, Map.FIELD_SIZE / 10, 5 * Map.FIELD_SIZE / 8);
        g.fillRect(positionX + 3 * Map.FIELD_SIZE / 20, positionY + Map.FIELD_SIZE / 10 + Map.FIELD_SIZE / 15, 5 * Map.FIELD_SIZE / 8, Map.FIELD_SIZE / 10);
        g.fillRect(positionX + 3 * Map.FIELD_SIZE / 20, positionY + 15 * Map.FIELD_SIZE / 20, 5 * Map.FIELD_SIZE / 8, Map.FIELD_SIZE / 10);

        g.setColor(Color.DARK_GRAY);
        g.fillOval(positionX + Map.FIELD_SIZE / 10, positionY + Map.FIELD_SIZE / 10, Map.FIELD_SIZE / 5, Map.FIELD_SIZE / 5);
        g.fillOval(positionX + 7 * Map.FIELD_SIZE / 10, positionY + Map.FIELD_SIZE / 10, Map.FIELD_SIZE / 5, Map.FIELD_SIZE / 5);
        g.fillOval(positionX + 7 * Map.FIELD_SIZE / 10, positionY + 7 * Map.FIELD_SIZE / 10, Map.FIELD_SIZE / 5, Map.FIELD_SIZE / 5);
        g.fillOval(positionX + Map.FIELD_SIZE / 10, positionY + 7 * Map.FIELD_SIZE / 10, Map.FIELD_SIZE / 5, Map.FIELD_SIZE / 5);

    }

    private void renderCrocodile(Graphics2D g, int positionX, int positionY) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/images/Crocodile1.jpg"));
        } catch (IOException e) {
            System.out.println("IMAGE IS NOT FOUND");
        }
        g.drawImage(img, positionX, positionY, img.getWidth() * Map.FIELD_SIZE / img.getHeight(), Map.FIELD_SIZE, null);
    }

    private void renderChest(Graphics2D g, int positionX, int positionY, int type) {
        int width = 5;
        g.setColor(Color.BLACK);
        for (int i = 0; i < width; i++) {
            g.drawRect(positionX + Map.FIELD_SIZE / 10 + i, positionY + Map.FIELD_SIZE / 10 + i, 3 * Map.FIELD_SIZE / 4 - 2 * i, 3 * Map.FIELD_SIZE / 4 - 2 * i);
        }
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(type + "", positionX + Map.FIELD_SIZE / 3, positionY + 2 * Map.FIELD_SIZE / 3);

    }

    private void renderBackGround(Graphics2D g, Color c, int positionX, int positionY) {
        g.setColor(c);
        g.fillRect(positionX, positionY, Map.FIELD_SIZE, Map.FIELD_SIZE);
    }

    private void renderCoinsNumber(Graphics2D g) {
        g.setColor(Color.BLACK);
    }

    public int getCoinsNumber() {
        return coins;
    }

    public void addCoin() {
        coins++;
    }

    public void removeCoin() {
        coins--;
    }

}
