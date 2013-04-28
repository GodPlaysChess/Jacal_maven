package src.com.godplayschess.jacal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Pirate extends MovingObject {

    private boolean is_trapped = false;
    private int is_drunk = 0;
    private int team = -1;
    private int number; //1, 2 or 3. The same as position in the array
    private boolean on_plane = false;


    Pirate(int team, int number) {
        super();
        this.number = number;
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

    Pirate(int team, int number, int x, int y) {
        super();
        this.number = number;
        this.team = team;
        x_on_map = x;
        y_on_map = y;

    }

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

    protected void movementSecondaryActions(int direction_pad) {
        movementSecondaryActions(getNewPosition(direction_pad)[0], getNewPosition(direction_pad)[1]);
    }

    protected void movementSecondaryActions(int targetX, int targetY) {

        if (inJungle()) {
            if (fields_left > 0) {
                x_on_map = prev_x_on_map;
                y_on_map = prev_y_on_map;
            }
        }

        if (getPrevTile() >= Tile.ARROW1 && getPrevTile() <= Tile.ICE_2X) {
            for (MovingObject O : getMyTeam().TeamObjects)
                O.could_be_selected = true;
        }

        if (getPrevTile() == Tile.PLANE && on_plane)
            GameData.map.Map[prev_x_on_map][prev_y_on_map] = new Tile(Tile.USED_PLANE);

        for (Coin O : GameData.Coins) {
            if (prev_x_on_map == O.x_on_map && prev_y_on_map == O.y_on_map && O.checkTaken()) {
                O.update(targetX, targetY);
                break; //just to be sure that only one coins will be taken;
            }
        }
    }
/*
    protected boolean movementAllowed(int direction_pad) {
        int newx = getNewPosition(direction_pad)[0];
        int newy = getNewPosition(direction_pad)[1];
        return movementAllowed(newx, newy);

    }*/

/*    protected boolean movementAllowed(int newx, int newy) {
        for (int[] aF : getAllowedFields()) {
            if (newx == aF[0] && newy == aF[1])
                return true;
        }
        return false;*/
    /*


        int dir = getMyTeam().getMove_direction();
        //out of map check
        if (newx < 0 || newx > src.com.godplayschess.jacal.Map.NFIELDS - 1 || newy < 0 || newy > src.com.godplayschess.jacal.Map.NFIELDS - 1) {
            return false;
        }

        if (getTile() != src.com.godplayschess.jacal.Tile.HORSE) {
            if (Math.abs(newx - x_on_map) > 1 || (Math.abs(newy - y_on_map) > 1))
                return false;
        }

        if (getTile() == src.com.godplayschess.jacal.Tile.HORSE) {
            if (Math.abs(newx - x_on_map) == 2 && (Math.abs(newy - y_on_map) == 1) || Math.abs(newx - x_on_map) == 1 && (Math.abs(newy - y_on_map) == 2))
                return true;
        }

        //there's only one way out of the water
        if (!onBoat() && getTile() == src.com.godplayschess.jacal.Tile.WATER && src.com.godplayschess.jacal.GameData.map.getTileType(newx, newy) != src.com.godplayschess.jacal.Tile.WATER)
            return false;

        //there's only one way out of the boat
        if (onBoat() && src.com.godplayschess.jacal.GameData.map.getTileType(newx, newy) != src.com.godplayschess.jacal.Tile.WATER && getMyTeam().getMove_direction() != 8 && getMyTeam().getMove_direction() != 2)
            return false;


        //common conditions
        if (is_trapped || is_drunk != 0)
            return false;

        //arrows
        if (getObjTile() instanceof src.com.godplayschess.jacal.Arrow) {
            for (int allowed_direction : ((src.com.godplayschess.jacal.Arrow) getObjTile()).AllowedDirections) {
                if (dir == allowed_direction)
                    return true;
            }                                                                                           //MAY BE BAD CAUSE RETURNS WITHOUT CHECKING OTHER CONDITIONS
            return false;
        }

        if (getTile() == src.com.godplayschess.jacal.Tile.ICE_2X) {
            if ((newx + prev_x_on_map) != 2 * x_on_map || (newy + prev_y_on_map) != 2 * y_on_map) {
                return false;
            }
        }

        return true;*/

    // }

    public boolean changePlane() {
        return on_plane = !on_plane;
    }

    public void checkEvent() {
        checkLandEvent();
        checkOpponentEvent();
    }


    private boolean onBoat() {
        if (x_on_map == getMyTeam().TeamObjects.get(0).x_on_map && y_on_map == getMyTeam().TeamObjects.get(0).y_on_map)
            return true;
        else return false;
    }


    /**
     * If you stroke the boat then, return to your ship. Else kill every pirate     *
     */

    private void checkOpponentEvent() {
        if (x_on_map == getOtherTeam().TeamObjects.get(0).x_on_map && y_on_map == getOtherTeam().TeamObjects.get(0).y_on_map) {
            x_on_map = getMyTeam().TeamObjects.get(0).x_on_map;
            y_on_map = getMyTeam().TeamObjects.get(0).y_on_map;
            getMyTeam().dies();
        } else {
            for (int i = 1; i < getOtherTeam().TeamObjects.size(); i++) {
                if (fields_left == getOtherTeam().TeamObjects.get(i).fields_left) {
                    if (x_on_map == getOtherTeam().TeamObjects.get(i).x_on_map && y_on_map == getOtherTeam().TeamObjects.get(i).y_on_map) {
                        if (getTile() != Tile.FORT && getTile() != Tile.FORT_GIRL) {
                            getOtherTeam().TeamObjects.get(i).x_on_map = getOtherTeam().TeamObjects.get(0).x_on_map;
                            getOtherTeam().TeamObjects.get(i).y_on_map = getOtherTeam().TeamObjects.get(0).y_on_map;
                            if (getOtherTeam().TeamObjects.get(i) instanceof Pirate)
                                ((Pirate) getOtherTeam().TeamObjects.get(i)).releaseFromTrap();
                        } else {
                            x_on_map = getMyTeam().TeamObjects.get(0).x_on_map;
                            y_on_map = getMyTeam().TeamObjects.get(0).y_on_map;
                        }
                    }
                }
            }
        }
    }

    private void checkLandEvent() {
        System.out.println("event checked, TILE:" + getTile());
        switch (GameData.map.getTileType(x_on_map, y_on_map)) {
            case (Tile.UNKNOWN):
                GameData.map.openTile(x_on_map, y_on_map);
                checkLandEvent();
                break;
            case (Tile.CHEST1):
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.map.openChest(x_on_map, y_on_map, 1);
                //OPENED CHEST to avoid event repetition
                break;
            case (Tile.CHEST2):
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.map.openChest(x_on_map, y_on_map, 2);
                break;
            case (Tile.CHEST3):
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.map.openChest(x_on_map, y_on_map, 3);
                break;
            case (Tile.CHEST4):
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.map.openChest(x_on_map, y_on_map, 4);
                break;
            case (Tile.CHEST5):
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.Coins.add(new Coin(x_on_map, y_on_map));
                GameData.map.openChest(x_on_map, y_on_map, 5);
                break;
            case (Tile.CROCODILE):
                update(prev_x_on_map, prev_y_on_map);
                //move + checkevent = update
                break;
            case (Tile.HOLE):
                inTheHole();
                break;
            case (Tile.PARACHUTE):
                getMyTeam().takeCoin();
                move(getMyTeam().TeamObjects.get(0).x_on_map, getMyTeam().TeamObjects.get(0).y_on_map);
                break;
            case (Tile.CANNON):
                getMyTeam().takeCoin();
                cannonEvent();
                break;
            case (Tile.CANNIBAL):
                getMyTeam().dies();
                break;

            case (Tile.ICE_2X):
            case (Tile.ARROW4):
            case (Tile.ARROW3):
            case (Tile.ARROW2):
            case (Tile.ARROW1):
                onTheArrow();
                break;
            case (Tile.JUNGLE):
            case (Tile.DESERT):
            case (Tile.SWAMP):
            case (Tile.WATERFALL):
                slowedSomewhere();
                break;
            case (Tile.RHUM):
                is_drunk = getMyTeam().TeamObjects.size() - 1;
                break;
        }
    }

    private void cannonEvent() {
        int dir = 0;
        if (getObjTile() instanceof Cannon) {
            dir = ((Cannon) getObjTile()).direction;
        }
        switch (dir) {
            case 2:
                push(x_on_map, 0);
                break;
            case 4:
                push(0, y_on_map);
                break;
            case 6:
                push(Map.NFIELDS - 1, y_on_map);
                break;
            case 8:
                push(x_on_map, Map.NFIELDS - 1);
                break;
        }
    }

    //checking that was not in the jungle before)
    // And if was - then go one step forward
    private void slowedSomewhere() {
        if (fields_left == 0)
            fields_left = getTile() - 91;
        else
            fields_left--;
    }

    private void onTheArrow() {
        System.out.println("I am on the arrow");
        is_moved = false;
        for (MovingObject O : getMyTeam().TeamObjects) {
            if (O != this) {
                O.could_be_selected = false;
            }
        }
    }

    private void inTheHole() {
        is_trapped = true;
        for (MovingObject O : getMyTeam().TeamObjects) {
            if (O != this && O.x_on_map == x_on_map && O.y_on_map == y_on_map) {
                is_trapped = false;
                if (O instanceof Pirate) {
                    ((Pirate) O).releaseFromTrap();
                }
            }
        }
    }

    //think about it more!
    public void releaseFromTrap() {
        is_trapped = false;
    }

    public boolean isDrunk() {
        return is_drunk > 0;
    }

    public int sober() {
        return is_drunk--;
    }

    public int getNumber() {
        return number;
    }


    public ArrayList<int[]> getAllowedFields() {
        ArrayList<int[]> allowedFields = new ArrayList<int[]>();
        if (isDrunk() || is_trapped)
            return allowedFields;


        switch (getTile()) {
            case Tile.WATER:
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++) {
                        if (GameData.map.getTileType(x_on_map + i, y_on_map + j) == Tile.WATER && (i != 0 || j != 0))
                            allowedFields.add(new int[]{x_on_map + i, y_on_map + j});
                    }
                if (onBoat()) {
                    allowedFields.add(new int[]{x_on_map, Math.abs(y_on_map - 1)});
                }
                break;
            case Tile.ICE_2X:
                allowedFields.add(new int[]{2 * x_on_map - prev_x_on_map, 2 * y_on_map - prev_y_on_map});
                break;
            case Tile.ARROW1:
            case Tile.ARROW2:
            case Tile.ARROW3:
            case Tile.ARROW4:
                if (getObjTile() instanceof Arrow) {
                    for (int allowed_direction : ((Arrow) getObjTile()).AllowedDirections) {
                        allowedFields.add(getNewPosition(allowed_direction));
                    }
                }
                break;
            case Tile.HORSE:
                for (int i = -2; i <= 2; i++)
                    for (int j = -2; j <= 2; j++) {
                        if (Math.abs(j + i) % 2 == 1 && i != 0 && j != 0)
                            allowedFields.add(new int[]{x_on_map + i, y_on_map + j});
                    }
                break;
            case Tile.PLANE:
                if (on_plane) {
                    for (int i = 0; i < Map.NFIELDS; i++)
                        for (int j = 0; j < Map.NFIELDS; j++)
                            allowedFields.add(new int[]{i, j});
                }
                //break; - If not on plane - then default field.
            default:
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0)
                            continue;
                        allowedFields.add(new int[]{x_on_map + i, y_on_map + j});
                    }

        }

        // remove out of bounds fields        DOES NOT WORK NOW BAD TO REMOVE OBJECTS WHILE ITERATING
        Iterator<int[]> it = allowedFields.iterator();
        while (it.hasNext()) {
            int[] aF = it.next();
            if (aF[0] < 0 || aF[0] >= Map.NFIELDS || aF[1] < 0 || aF[1] >= Map.NFIELDS) {
                it.remove();
                it.next();
            }

        }
        for (int[] aF : allowedFields) {
            if (aF[0] < 0 || aF[0] >= Map.NFIELDS || aF[1] < 0 || aF[1] >= Map.NFIELDS)
                allowedFields.remove(aF);
        }

        return allowedFields;
    }


    public void render(Graphics2D g) {
        int c = Map.FIELD_SIZE / 10;
        if (!is_selected)
            g.setColor(Color.BLACK);
        if (is_selected)
            g.setColor(GameData.SELECTED_OBJECT);

        if (getTile() == Tile.JUNGLE) {
            g.fillRect(Map.getXCoordinate(x_on_map) + 8 * c - 8 * number - fields_left * 3 * c - 1, Map.getYCoordinate(y_on_map) + c + (1 - fields_left) * 5 * c - 1, 11, 21);
            g.setColor(getMyTeam().color);
            g.fillRect(Map.getXCoordinate(x_on_map) + 8 * c - 8 * number - fields_left * 3 * c, Map.getYCoordinate(y_on_map) + c + (1 - fields_left) * 5 * c, 9, 19);
        } else if (getTile() == Tile.DESERT) {
            g.fillRect(Map.getXCoordinate(x_on_map) + 9 * c - 6 * number - (1 - fields_left) * (1 - fields_left) * 5 * c - 1, Map.getYCoordinate(y_on_map) + c + (2 - fields_left) * 5 / 2 * c - 1, 11, 21);
            g.setColor(getMyTeam().color);
            g.fillRect(Map.getXCoordinate(x_on_map) + 9 * c - 6 * number - (1 - fields_left) * (1 - fields_left) * 5 * c, Map.getYCoordinate(y_on_map) + c + (2 - fields_left) * 5 / 2 * c, 9, 19);
        } else if (getTile() == Tile.SWAMP) {
            g.fillRect(Map.getXCoordinate(x_on_map) + 8 * c - 8 * number - (fields_left % 2) * 3 * c - 1, Map.getYCoordinate(y_on_map) + c + (3 - fields_left) * 2 * c - 1 - 3, 11, 21);
            g.setColor(getMyTeam().color);
            g.fillRect(Map.getXCoordinate(x_on_map) + 8 * c - 8 * number - (fields_left % 2) * 3 * c, Map.getYCoordinate(y_on_map) + c + (3 - fields_left) * 2 * c - 3, 9, 19);
        } else if (getTile() == Tile.WATERFALL) {
            renderOnWaterfall(g, Map.getXCoordinate(x_on_map), Map.getYCoordinate(y_on_map));
        } else {
            g.fillRect(Map.getXCoordinate(x_on_map) - 1 + Map.FIELD_SIZE - 10 * number - 12, Map.getYCoordinate(y_on_map) - 1 + 3, 17, 32);
            g.setColor(getMyTeam().color);
            g.fillRect(Map.getXCoordinate(x_on_map) + Map.FIELD_SIZE - 10 * number - 12, Map.getYCoordinate(y_on_map) + 3, 15, 30);
        }

        if (is_drunk != 0) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 13));
            g.drawString(is_drunk + "", Map.getXCoordinate(x_on_map) + Map.FIELD_SIZE - 10 * number - 12 + 5, Map.getYCoordinate(y_on_map) + 20);
        }

        if (is_selected)
            renderAllowedFields(g);

    }

    private void renderOnWaterfall(Graphics2D g, int x, int y) {
        int c = Map.FIELD_SIZE / 10;
        int pwsize = 9;
        if (!is_selected)
            g.setColor(Color.BLACK);
        if (is_selected)
            g.setColor(GameData.SELECTED_OBJECT);

        if (fields_left == 4) {
            g.fillRect(x + 4 * c - number * 5, y + c, pwsize, pwsize * 2);
            g.setColor(getMyTeam().color);
            g.fillRect(x + 4 * c + 1 - number * 5, y + c + 1, pwsize - 2, pwsize * 2 - 2);
        }
        if (fields_left == 3) {
            g.fillRect(x + 9 * c - number * 5, y + c, pwsize, pwsize * 2);
            g.setColor(getMyTeam().color);
            g.fillRect(x + 9 * c + 1 - number * 5, y + c + 1, pwsize - 2, pwsize * 2 - 2);
        }
        if (fields_left < 3) {
            g.fillRect(x + 9 * c - number * 5 - (2-fields_left)*3*c, y + 7*c, pwsize, pwsize * 2);
            g.setColor(getMyTeam().color);
            g.fillRect(x + 9 * c + 1 - number * 5 -(2-fields_left)*3*c, y + 7*c + 1, pwsize - 2, pwsize * 2 - 2);
        }
        g.setColor(getMyTeam().color);
    }

    private void renderAllowedFields(Graphics2D g) {
        g.setColor(new Color(24, 215, 30));
        if (fields_left == 0) {
            for (int[] aF : getAllowedFields()) {

                g.drawRect(Map.getXCoordinate(aF[0]), Map.getYCoordinate(aF[1]), Map.FIELD_SIZE - 1, Map.FIELD_SIZE - 1);
                g.drawRect(Map.getXCoordinate(aF[0]) + 1, Map.getYCoordinate(aF[1]) + 1, Map.FIELD_SIZE - 3, Map.FIELD_SIZE - 3);
                g.drawRect(Map.getXCoordinate(aF[0]) + 2, Map.getYCoordinate(aF[1]) + 2, Map.FIELD_SIZE - 5, Map.FIELD_SIZE - 5);
            }
        } else {
            g.drawRect(Map.getXCoordinate(x_on_map), Map.getYCoordinate(y_on_map), Map.FIELD_SIZE - 1, Map.FIELD_SIZE - 1);
            g.drawRect(Map.getXCoordinate(x_on_map) + 1, Map.getYCoordinate(y_on_map) + 1, Map.FIELD_SIZE - 3, Map.FIELD_SIZE - 3);
            g.drawRect(Map.getXCoordinate(x_on_map) + 2, Map.getYCoordinate(y_on_map) + 2, Map.FIELD_SIZE - 5, Map.FIELD_SIZE - 5);
        }
    }


}
