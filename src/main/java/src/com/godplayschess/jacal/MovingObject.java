package src.com.godplayschess.jacal;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;
import java.util.ArrayList;

public abstract class MovingObject {
    protected int prev_x_on_map;
    protected int prev_y_on_map;
    protected int x_on_map;
    protected int y_on_map;
    protected int fields_left = 0;

    public boolean is_selected;
    public boolean is_moved;
    public boolean could_be_selected;

    MovingObject() {
        could_be_selected = true;
        prev_x_on_map = x_on_map;
        prev_y_on_map = y_on_map;
        is_selected = false;
        is_moved = false;
    }


    public void move(int direction_pad) {
        move(getNewPosition(direction_pad)[0], getNewPosition(direction_pad)[1]);
    }

    public void move(int targetX, int targetY) {
        int tempx = x_on_map;
        int tempy = y_on_map;
        x_on_map = targetX;
        y_on_map = targetY;
        prev_x_on_map = tempx;
        prev_y_on_map = tempy;
        is_moved = true;
        movementSecondaryActions(targetX, targetY);
    }

    protected void movementSecondaryActions(int direction_pad) {

    }

    protected void movementSecondaryActions(int targetX, int targetY) {

    }

    public void push(int direction) {
        move(direction);
        checkEvent();
    }

    public void push(int targetX, int targetY) {
        move(targetX, targetY);
        checkEvent();
    }

    public void update(int direction) {
        if (movementAllowed(direction)) {   //ADDED HERE CUZ RUM.
            push(direction);
        }
    }

    public void update(int targetX, int targetY) {
        if (movementAllowed(targetX, targetY)) {
            push(targetX, targetY);
        }
    }

    protected boolean movementAllowed(int direction_pad) {
        int newx = getNewPosition(direction_pad)[0];
        int newy = getNewPosition(direction_pad)[1];
        return movementAllowed(newx, newy);
    }

    protected boolean movementAllowed(int targetX, int targetY) {

        for (int[] aF : getAllowedFields()) {
            if (targetX == aF[0] && targetY == aF[1])
                return true;
        }

        return false;
    }

    public void checkEvent() {

    }

    public void render(Graphics2D g) {

    }

    protected boolean inJungle() {
        if (getPrevTile() > 90 && getPrevTile() < 96)
            return true;
        else return false;
    }

    public void select() {
        is_selected = true;
    }

    public void unselect() {
        is_selected = false;
    }

    protected int[] getNewPosition(int direction_pad) {
        if (getTile() == Tile.HORSE)
            return getNewPositionHorse(direction_pad);

        int[] resultcoor = new int[2];
        switch (direction_pad) {
            case 0: {
                resultcoor[0] = x_on_map;
                resultcoor[1] = y_on_map;
            }
            break;
            case 1: {
                resultcoor[0] = x_on_map - 1;
                resultcoor[1] = y_on_map + 1;
                break;
            }
            case 2: {
                resultcoor[0] = x_on_map;
                resultcoor[1] = y_on_map + 1;
                break;
            }
            case 3: {
                resultcoor[0] = x_on_map + 1;
                resultcoor[1] = y_on_map + 1;

                break;
            }
            case 4: {
                resultcoor[0] = x_on_map - 1;
                resultcoor[1] = y_on_map;
                break;
            }
            case 6: {
                resultcoor[0] = x_on_map + 1;
                resultcoor[1] = y_on_map;
                break;
            }
            case 7: {
                resultcoor[0] = x_on_map - 1;
                resultcoor[1] = y_on_map - 1;
                break;
            }
            case 8: {
                resultcoor[0] = x_on_map;
                resultcoor[1] = y_on_map - 1;
                break;
            }
            case 9: {
                resultcoor[0] = x_on_map + 1;
                resultcoor[1] = y_on_map - 1;
                break;
            }
        }
        return resultcoor;

    }

    private int[] getNewPositionHorse(int direction_pad) {
        int[] resultcoor = new int[2];
        switch (direction_pad) {
            case 0: {
                resultcoor[0] = x_on_map;
                resultcoor[1] = y_on_map;
            }
            break;
            case 1: {
                resultcoor[0] = x_on_map - 1;
                resultcoor[1] = y_on_map + 2;
                break;
            }
            case 2: {
                resultcoor[0] = x_on_map + 1;
                resultcoor[1] = y_on_map + 2;
                break;
            }
            case 3: {
                resultcoor[0] = x_on_map + 2;
                resultcoor[1] = y_on_map + 1;

                break;
            }
            case 4: {
                resultcoor[0] = x_on_map - 2;
                resultcoor[1] = y_on_map + 1;
                break;
            }
            case 6: {
                resultcoor[0] = x_on_map + 2;
                resultcoor[1] = y_on_map - 1;
                break;
            }
            case 7: {
                resultcoor[0] = x_on_map - 2;
                resultcoor[1] = y_on_map - 1;
                break;
            }
            case 8: {
                resultcoor[0] = x_on_map - 1;
                resultcoor[1] = y_on_map - 2;
                break;
            }
            case 9: {
                resultcoor[0] = x_on_map + 1;
                resultcoor[1] = y_on_map - 2;
                break;
            }
        }
        return resultcoor;
    }

    public ArrayList<int[]> getAllowedFields() {
        return new ArrayList<int[]>();
    }

    protected int getTile() {
        return GameData.map.getTileType(x_on_map, y_on_map);
    }

    protected Tile getObjTile() {
        return GameData.map.Map[x_on_map][y_on_map];
    }

    protected int getPrevTile() {
        return GameData.map.Map[prev_x_on_map][prev_y_on_map].type;
    }


}
