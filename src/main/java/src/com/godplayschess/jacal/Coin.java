package src.com.godplayschess.jacal;

import java.awt.*;

public class Coin extends MovingObject {
    public final static int RADII = 15;

    private final static Color GOLD = new Color(254, 246, 23);

    private boolean to_be_removed = false;


    Coin(int x, int y) {
        super();
        x_on_map = x;
        y_on_map = y;
        GameData.map.Map[x_on_map][y_on_map].addCoin();
    }

    public void render(Graphics2D g) {
        int c = Map.FIELD_SIZE / 10;
        g.setColor(Color.BLACK);

        if (getTile() == Tile.JUNGLE) {
            if (!is_selected) {
                g.fillOval(Map.getXCoordinate(x_on_map) - 1 + 6 * c * (1 - fields_left), Map.getYCoordinate(y_on_map) + 6 * c * (1 - fields_left) - 1, RADII + 2, RADII + 2);
                g.setColor(GOLD);
                g.fillOval(Map.getXCoordinate(x_on_map) + 6 * c * (1 - fields_left), Map.getYCoordinate(y_on_map) + 6 * c * (1 - fields_left), RADII, RADII);
            }
            if (is_selected) {
                g.setColor(GameData.SELECTED_OBJECT);
                g.fillOval(Map.getXCoordinate(x_on_map) - 1 + 6 * c * (1 - fields_left), Map.getYCoordinate(y_on_map) + 6 * c * (1 - fields_left) - 1, RADII + 2, RADII + 2);
            }
        } else if (getTile() == Tile.DESERT) {
            if (!is_selected) {
                g.fillOval(Map.getXCoordinate(x_on_map) - 1 + 9 * c - 6 * c * (1 - fields_left) * (1 - fields_left), Map.getYCoordinate(y_on_map) + 3 * c * (2 - fields_left) - 1, RADII + 2, RADII + 2);
                g.setColor(GOLD);
                g.fillOval(Map.getXCoordinate(x_on_map) + 9 * c - 6 * c * (1 - fields_left) * (1 - fields_left), Map.getYCoordinate(y_on_map) + 3 * c * (2 - fields_left), RADII, RADII);
            }
            if (is_selected) {
                g.setColor(GameData.SELECTED_OBJECT);
                g.fillOval(Map.getXCoordinate(x_on_map) - 1 + 9 * c - 6 * c * (1 - fields_left) * (1 - fields_left), Map.getYCoordinate(y_on_map) + 3 * c * (2 - fields_left) - 1, RADII + 2, RADII + 2);
            }
        } else if (getTile() == Tile.SWAMP) {
            if (!is_selected) {
                g.fillOval(Map.getXCoordinate(x_on_map) - 1 + 7 * c - 5 * c * (fields_left % 2), Map.getYCoordinate(y_on_map) + 2 * c * (3 - fields_left) - 1, RADII + 2, RADII + 2);
                g.setColor(GOLD);
                g.fillOval(Map.getXCoordinate(x_on_map) + 7 * c - 5 * c * (fields_left % 2), Map.getYCoordinate(y_on_map) + 2 * c * (3 - fields_left), RADII, RADII);
            }
            if (is_selected) {
                g.setColor(GameData.SELECTED_OBJECT);
                g.fillOval(Map.getXCoordinate(x_on_map) - 1 + 7 * c - 5 * c * (fields_left % 2), Map.getYCoordinate(y_on_map) + 2 * c * (3 - fields_left) - 1, RADII + 2, RADII + 2);
            }
        } else if (getTile() == Tile.WATERFALL) {
            renderCoinOnWaterfall(g, Map.getXCoordinate(x_on_map), Map.getYCoordinate(y_on_map));
        } else {


            g.setColor(Color.BLACK);
            g.fillOval(Map.getXCoordinate(x_on_map) - 1, Map.getYCoordinate(y_on_map) - 1, RADII + 2, RADII + 2);

            g.setColor(new Color(254, 246, 23));
            if (is_selected)
                g.setColor(new Color(254, 155, 14));

            g.fillOval(Map.getXCoordinate(x_on_map), Map.getYCoordinate(y_on_map), RADII, RADII);
        }
    }

    private void renderCoinOnWaterfall(Graphics2D g, int x, int y) {
        int c = Map.FIELD_SIZE / 10;
        int radii = 9;
        g.setColor(Color.BLACK);
        if (fields_left == 4) {
            g.fillOval(x + c, y + c, radii, radii);
            g.setColor(GOLD);
            g.fillOval(x + c + 1, y + c + 1, radii - 2, radii - 2);
            if (is_selected) {
                g.setColor(GameData.SELECTED_OBJECT);
                g.fillOval(x + c, y + c, radii, radii);
            }
        }

        if (fields_left == 3) {
            g.fillOval(x + 6 * c, y + 6 * c, radii, radii);
            g.setColor(GOLD);
            g.fillOval(x + 6 * c + 1, y + c + 1, radii - 2, radii - 2);
            if (is_selected) {
                g.setColor(GameData.SELECTED_OBJECT);
                g.fillOval(x + 6 * c, y + 6 * c, radii, radii);
            }
        }
        if (fields_left < 3) {
            g.fillOval(x + 7 * c - (2 - fields_left) * 3 * c, y + 6 * c, radii, radii);
            g.setColor(GOLD);
            g.fillOval(x + 7 * c - (2 - fields_left) * 3 * c + 1, y + 6 * c + 1, radii - 2, radii - 2);
            if (is_selected) {
                g.setColor(GameData.SELECTED_OBJECT);
                g.fillOval(x + 7 * c - (2 - fields_left) * 3 * c, y + 6 * c, radii, radii);
            }

        }
    }

    public void takeOrPut() {
        is_selected = !is_selected;
    }

    public boolean checkTaken() {
        return is_selected;
    }

    public void movementSecondaryActions(int direction_pad) {
        //simple redirection , which does nothing. May be it is useless method
        movementSecondaryActions(x_on_map, y_on_map);
        System.out.println("movementSecondaryActions(int direction_pad) is used. May be u need to reconsider the code!!");
    }


    public void checkEvent() {
        //System.out.println("event checked, TILE:" + getTile());
        switch (GameData.map.getTileType(x_on_map, y_on_map)) {
            case (Tile.CROCODILE):
                update(prev_x_on_map, prev_y_on_map);

                break;
            case (Tile.JUNGLE):
            case (Tile.DESERT):
            case (Tile.SWAMP):
            case (Tile.WATERFALL):
                if (fields_left == 0)
                    fields_left = getTile() - 91;
                else
                    fields_left--;


                break;

        }
    }

    protected void movementSecondaryActions(int targetX, int targetY) {
        //here events could happen with a coin (such as sink, score++ or even super movement)
        if (GameData.map.Map[x_on_map][y_on_map].type == Tile.WATER && getPrevTile() != Tile.WATER) {
            //score++ if boat here
            if (GameData.Team1.TeamObjects.get(0).x_on_map == x_on_map && GameData.Team1.TeamObjects.get(0).y_on_map == y_on_map)
                GameData.Team1.incScore();
            if (GameData.Team2.TeamObjects.get(0).x_on_map == x_on_map && GameData.Team2.TeamObjects.get(0).y_on_map == y_on_map)
                GameData.Team2.incScore();
                //else remove(x_on_map, y_on_map); //need to do it properly. some clean stage...
            else to_be_removed = true;
        }

        if (inJungle()) {
            if (fields_left > 0) {
                x_on_map = prev_x_on_map;
                y_on_map = prev_y_on_map;
            }
        }

        //changes coins counter value on the src.com.godplayschess.jacal.Tile
        GameData.map.Map[prev_x_on_map][prev_y_on_map].removeCoin();
        GameData.map.Map[x_on_map][y_on_map].addCoin();
        is_moved = false;

        unselect();

    }

    public boolean isTo_be_removed(){
        return to_be_removed;
    }

    public boolean movementAllowed(int direction_pad) {
        return movementAllowed(getNewPosition(direction_pad)[0], getNewPosition(direction_pad)[1]);           //!!! AKS DOES IT PERFORM THE WHOLE METHOD, OR JUST RETURNS VALUE (unselect part)
    }

    protected boolean movementAllowed(int newX, int newY) {
        unselect();

        if (fields_left == 0 && GameData.map.Map[newX][newY].type == Tile.UNKNOWN)
            return false;
        //Forbidden to attack opponent
        return  (!fight());

    }

    private boolean fight() {
        for (int i = 1; i < GameData.Team1.TeamObjects.size(); i++)
            for (int j = 1; j < GameData.Team2.TeamObjects.size(); j++)
                if (GameData.Team1.TeamObjects.get(i).x_on_map == GameData.Team2.TeamObjects.get(j).x_on_map && GameData.Team1.TeamObjects.get(i).y_on_map == GameData.Team2.TeamObjects.get(i).y_on_map)
                    if (GameData.Team1.TeamObjects.get(i).fields_left == GameData.Team2.TeamObjects.get(j).fields_left) //on the same field in jungles
                        return true;

        return false;
    }


}
