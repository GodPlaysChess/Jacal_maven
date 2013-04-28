package src.com.godplayschess.jacal;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Cannon extends Tile {
    public final int direction;
    //   private AffineTransform transformer = new AffineTransform();

    public Cannon(int type) {
        super(type);
        direction = ((int) (Math.random() * 4) + 1) * 2;

    }


    public void renderCannon(Graphics2D g, int positionX, int positionY) {
        AffineTransform saveAT = g.getTransform();

        if (direction == 6) {
            renderCannon6(g, positionX, positionY);
        }

        if (direction == 8) {
            g.translate(positionX + Map.FIELD_SIZE, positionY);
            g.rotate(Math.PI / 2);
            g.translate(-positionX, -positionY);
            renderCannon6(g, positionX, positionY);

        }
        if (direction == 4) {
            g.rotate(Math.PI, positionX, positionY);
            g.translate(-Map.FIELD_SIZE, -Map.FIELD_SIZE);
            renderCannon6(g, positionX, positionY);

        }
        if (direction == 2) {
            g.rotate(-Math.PI / 2, positionX, positionY);
            g.translate(-Map.FIELD_SIZE,0);
            renderCannon6(g, positionX, positionY);
        }

        g.setTransform(saveAT);
    }


    private void renderCannon6(Graphics2D g, int positionX, int positionY) {
        int c = Map.FIELD_SIZE / 10;
        Polygon p = new Polygon();
        g.setColor(Color.BLACK);
        g.fillOval(positionX + 4 * c, positionY + 6 * c - c / 2 + 1, 2 * c, 2 * c);
        g.setColor(Color.GRAY);
        g.fillOval(positionX + 4 * c + c / 2, positionY + 6 * c + 1, c, c);
        g.setColor(new Color(104, 70, 32));
        p.addPoint(positionX + 2 * c, positionY + 6 * c);
        p.addPoint(positionX + 2 * c + c / 2, positionY + 7 * c);
        p.addPoint(positionX + 8 * c + c / 2, positionY + 4 * c);
        p.addPoint(positionX + 8 * c, positionY + 3 * c);
        g.fillPolygon(p);
    }


}
