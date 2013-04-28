package src.com.godplayschess.jacal;

import java.awt.*;
import java.util.ArrayList;

public class Boat extends MovingObject {
    private int team = -1;

    private Team getOtherTeam() {
        if (this.team == 1)
            return GameData.Team2;
        else
            return GameData.Team1;
    }

    private Team getMyTeam() {
        if (this.team == 1)
            return GameData.Team1;
        else
            return GameData.Team2;
    }

    Boat(int team) {
        super();
        this.team = team;
        if (team == 1) {
            x_on_map = Map.NFIELDS / 2;
            y_on_map = 0;
        }

        if (team == 2) {
            x_on_map = Map.NFIELDS / 2;
            y_on_map = Map.NFIELDS - 1;
        }
    }

    public void render(Graphics2D g) {
        if (!is_selected)
            g.setColor(Color.BLACK);
        if (is_selected)
            g.setColor(GameData.SELECTED_OBJECT);
        g.fillRect(Map.getXCoordinate(x_on_map) + 9, Map.getYCoordinate(y_on_map) + Map.FIELD_SIZE - 16, 32, 10);

        g.setColor(new Color(111, 53, 14));
        g.fillRect(Map.getXCoordinate(x_on_map) + 10, Map.getYCoordinate(y_on_map) + Map.FIELD_SIZE - 15, 30, 8);

        if (is_selected)
            renderAllowedFields(g);
    }

    public void checkEvent() {
        //the only event occurs with boat is killing enemy pirate;
        MovingObject temp = null;
        for (MovingObject O : getOtherTeam().TeamObjects) {
            if (x_on_map == O.x_on_map && y_on_map == O.y_on_map)
                temp = O; //<to make normal remove!
        }
        getOtherTeam().TeamObjects.remove(temp);
    }


    private void renderAllowedFields(Graphics2D g) {
        g.setColor(new Color(24, 215, 30));
        for (int[] aF : getAllowedFields()) {
            g.drawRect(Map.getXCoordinate(aF[0]), Map.getYCoordinate(aF[1]), Map.FIELD_SIZE - 1, Map.FIELD_SIZE - 1);
            g.drawRect(Map.getXCoordinate(aF[0]) + 1, Map.getYCoordinate(aF[1]) + 1, Map.FIELD_SIZE - 3, Map.FIELD_SIZE - 3);
            g.drawRect(Map.getXCoordinate(aF[0]) + 2, Map.getYCoordinate(aF[1]) + 2, Map.FIELD_SIZE - 5, Map.FIELD_SIZE - 5);
        }
    }
/*    protected boolean movementAllowed(int direction_pad) {

        int newx = getNewPosition(direction_pad)[0];
        int newy = getNewPosition(direction_pad)[1];
        return movementAllowed(newx, newy);
    }*/

    protected void movementSecondaryActions(int targetX, int targetY) {
        //move all pirates within
        for (int i = 1; i < getMyTeam().TeamObjects.size(); i++) {
            if (this.prev_x_on_map == getMyTeam().TeamObjects.get(i).x_on_map && this.y_on_map == getMyTeam().TeamObjects.get(i).y_on_map)
                getMyTeam().TeamObjects.get(i).move(this.x_on_map, this.y_on_map);
        }

    }

/*    protected boolean movementAllowed(int targetX, int targetY) {
*//*        for (int[] aF : getAllowedFields()) {
            if (targetX == aF[0] && targetY == aF[1])
                return true;
        }
        return false;
    }*/

    public void movementSecondaryActions(int direction_pad) {
        for (int i = 1; i < getMyTeam().TeamObjects.size(); i++) {
            if (getMyTeam().TeamObjects.get(i).x_on_map == prev_x_on_map && getMyTeam().TeamObjects.get(i).y_on_map == prev_y_on_map) {
                getMyTeam().TeamObjects.get(i).move(direction_pad);
                getMyTeam().TeamObjects.get(i).is_moved = false;
            }
        }
    }

    public ArrayList<int[]> getAllowedFields() {
        ArrayList<int[]> allowedFields = new ArrayList<int[]>();

        allowedFields.add(new int[]{x_on_map - 1, y_on_map});
        allowedFields.add(new int[]{x_on_map + 1, y_on_map});

        if (x_on_map == 2)
            allowedFields.remove(0);
        if (x_on_map == Map.NFIELDS - 3)
            allowedFields.remove(1);

        return allowedFields;
    }
}
