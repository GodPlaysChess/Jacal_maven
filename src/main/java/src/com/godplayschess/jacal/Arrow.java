package src.com.godplayschess.jacal;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.awt.*;
import java.util.LinkedList;

public class Arrow extends Tile {

    LinkedList<Integer> AllowedDirections = new LinkedList<Integer>();
    private final int width = Map.FIELD_SIZE / 7;

    public Arrow(int type) {
        super(type);
        int directions_number = type - 80;
        int new_direction = generateDirection();
        AllowedDirections.add(new_direction);
        switch (directions_number) {
            case 2:
                AllowedDirections.add(10 - new_direction);
                break;
            case 4:
                AllowedDirections.add(10 - new_direction);
                AllowedDirections.add(orthDirection(AllowedDirections.getFirst()));
                AllowedDirections.add(10 - AllowedDirections.getFirst());
                break;
            case 3:
                AllowedDirections.add(10 - new_direction);
                AllowedDirections.add(orthDirection(AllowedDirections.getFirst())-1);
                break;
        }
    }

    private int generateDirection(){
        int a = (int) (Math.random() * 8 + 1);
        if (a!=5)
            return a;
        else return generateDirection();

    }

    private int orthDirection(int direction) {
        switch (direction) {
            case 9:
            case 1:
                return 7;
            case 8:
            case 2:
                return 6;
            case 7:
            case 3:
                return 9;
            case 4:
            case 6:
                return 8;
        }
        return 0;
    }


    public void renderArrow(Graphics2D g, int positionX, int positionY) {
        for (int direction : AllowedDirections) {
            renderArrow(g, positionX, positionY, direction);
        }
    }

    private void renderArrow(Graphics2D g, int positionX, int positionY, int direction) {
        Polygon Arrow = new Polygon();
        Point[] CentralSquare = centralSquare(positionX, positionY, width);
        Polygon CSQR = new Polygon();
        CSQR.addPoint(CentralSquare[0].x,CentralSquare[0].y);
        CSQR.addPoint(CentralSquare[1].x,CentralSquare[1].y);
        CSQR.addPoint(CentralSquare[2].x,CentralSquare[2].y);
        CSQR.addPoint(CentralSquare[3].x,CentralSquare[3].y);

        Point[] CentralRhombus = centralRhombus(positionX, positionY, width);
        int c = Map.FIELD_SIZE / 10;
        int length = 4 * c;

        switch (direction) {
            case 1:
                Arrow.addPoint(CentralRhombus[3].x, CentralRhombus[3].y);
                Arrow.addPoint(CentralSquare[3].x, CentralSquare[3].y);
                Arrow.addPoint(CentralRhombus[0].x, CentralRhombus[0].y);
                Arrow.addPoint(CentralRhombus[0].x - length, CentralRhombus[0].y + length);
                Arrow.addPoint(CentralSquare[3].x - length, CentralSquare[3].y + length);
                Arrow.addPoint(CentralRhombus[3].x - length, CentralRhombus[3].y + length);
                break;
            case 3:
                Arrow.addPoint(CentralRhombus[2].x, CentralRhombus[2].y);
                Arrow.addPoint(CentralSquare[2].x, CentralSquare[2].y);
                Arrow.addPoint(CentralRhombus[3].x, CentralRhombus[3].y);
                Arrow.addPoint(CentralRhombus[3].x + length, CentralRhombus[3].y + length);
                Arrow.addPoint(CentralSquare[2].x + length, CentralSquare[2].y + length);
                Arrow.addPoint(CentralRhombus[2].x + length, CentralRhombus[2].y + length);
                break;
            case 7:
                Arrow.addPoint(CentralRhombus[0].x, CentralRhombus[0].y);
                Arrow.addPoint(CentralSquare[0].x, CentralSquare[0].y);
                Arrow.addPoint(CentralRhombus[1].x, CentralRhombus[1].y);
                Arrow.addPoint(CentralRhombus[1].x - length, CentralRhombus[1].y - length);
                Arrow.addPoint(CentralSquare[0].x - length, CentralSquare[0].y - length);
                Arrow.addPoint(CentralRhombus[0].x - length, CentralRhombus[0].y - length);
                break;
            case 9:
                Arrow.addPoint(CentralRhombus[1].x, CentralRhombus[1].y);
                Arrow.addPoint(CentralSquare[1].x, CentralSquare[1].y);
                Arrow.addPoint(CentralRhombus[2].x, CentralRhombus[2].y);
                Arrow.addPoint(CentralRhombus[2].x + length, CentralRhombus[2].y - length);
                Arrow.addPoint(CentralSquare[1].x + length, CentralSquare[1].y - length);
                Arrow.addPoint(CentralRhombus[1].x + length, CentralRhombus[1].y - length);
                break;
            case 8:
                Arrow.addPoint(CentralSquare[0].x, CentralSquare[0].y);
                Arrow.addPoint(CentralSquare[1].x, CentralSquare[1].y);
                Arrow.addPoint(CentralSquare[1].x, positionY + c + width / 2 + 1);
                Arrow.addPoint(CentralSquare[1].x - width / 2 - 1, positionY + c);
                Arrow.addPoint(CentralSquare[0].x, positionY + c + width / 2 + 1);
                break;
            case 6:
                Arrow.addPoint(CentralSquare[1].x, CentralSquare[1].y);
                Arrow.addPoint(CentralSquare[2].x, CentralSquare[2].y);
                Arrow.addPoint(positionX + 9 * c - width / 2, CentralSquare[2].y);
                Arrow.addPoint(positionX + 9 * c, CentralSquare[2].y - width / 2 - 1);
                Arrow.addPoint(positionX + 9 * c - width / 2, CentralSquare[1].y);
                break;
            case 4:
                Arrow.addPoint(CentralSquare[0].x, CentralSquare[0].y);
                Arrow.addPoint(CentralSquare[3].x, CentralSquare[3].y);
                Arrow.addPoint(positionX + c + width / 2, CentralSquare[3].y);
                Arrow.addPoint(positionX + c, CentralSquare[3].y - width / 2 - 1);
                Arrow.addPoint(positionX + c + width / 2, CentralSquare[0].y);
                break;
            case 2:
                Arrow.addPoint(CentralSquare[3].x, CentralSquare[3].y);
                Arrow.addPoint(CentralSquare[2].x, CentralSquare[2].y);
                Arrow.addPoint(CentralSquare[2].x, positionY + 9 * c - width / 2 - 1);
                Arrow.addPoint(CentralSquare[2].x - width / 2 - 1, positionY + 9 * c);
                Arrow.addPoint(CentralSquare[3].x, positionY + 9 * c - width / 2 - 1);
                break;
        }

        g.setColor(Color.DARK_GRAY);

        g.fillPolygon(Arrow);
        //g.fillPolygon(CSQR);
    }

    private Point[] centralSquare(int positionX, int positionY, int width) {
        Point[] result = new Point[4];
        int centerX = positionX + Map.FIELD_SIZE / 2;
        int centerY = positionY + Map.FIELD_SIZE / 2;
        result[0] = new Point(centerX - width / 2 - 1, centerY - width / 2 - 1);
        result[1] = new Point(centerX + width / 2, centerY - width / 2 - 1);
        result[2] = new Point(centerX + width / 2, centerY + width / 2);
        result[3] = new Point(centerX - width / 2 - 1, centerY + width / 2);
        return result;
    }

    private Point[] centralRhombus(int positionX, int positionY, int width) {
        Point[] result = new Point[4];
        int centerX = positionX + Map.FIELD_SIZE / 2;
        int centerY = positionY + Map.FIELD_SIZE / 2;
        result[0] = new Point(centerX - width / 2, centerY);
        result[1] = new Point(centerX, centerY - width / 2);
        result[2] = new Point(centerX + width / 2, centerY);
        result[3] = new Point(centerX, centerY + width / 2);
        return result;
    }


}
