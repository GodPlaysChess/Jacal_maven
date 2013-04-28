package src.com.godplayschess.jacal;


import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Team {

    public ArrayList<MovingObject> TeamObjects = new ArrayList<MovingObject>();

    private final int number;
    private int score;
    private int move_direction = 0;
    private int[] move_position = {-1, -1};
    public Color color;
    public boolean turn_is_made = false;

    LinkedList<Integer> died_pirates = new LinkedList<Integer>();


    public void setMove_direction(int move_direction) {
        this.move_direction = move_direction;
    }

    /**
     * src.com.godplayschess.jacal.Boat MUST BE the 0 element of the TeamObjects array. It really saves much code space
     */

    public Team(int teamnumber) {
        number = teamnumber;
        TeamObjects.add(new Boat(teamnumber));
        TeamObjects.add(new Pirate(teamnumber, 1));
        TeamObjects.add(new Pirate(teamnumber, 2));
        TeamObjects.add(new Pirate(teamnumber, 3));

        if (teamnumber == 1)
            color = new Color(186, 59, 255);
        else if (teamnumber == 2)
            color = new Color(79, 255, 195);

    }


    //this method checks which pirate/boat from the team is selected;

    public int findSelected() {
        for (int i = 0; i < TeamObjects.size(); i++)
            if (TeamObjects.get(i).is_selected)
                return i;

        return -1;
    }

    public MovingObject findSelectedObject() {
        if (findSelected() != -1)
            return TeamObjects.get(findSelected());
        else
            return TeamObjects.get(0);
    }

    public void takeCoin() {
        for (Coin O : GameData.Coins) {
            if (O.x_on_map == findSelectedObject().x_on_map && O.y_on_map == findSelectedObject().y_on_map && O.fields_left == findSelectedObject().fields_left) {
                O.takeOrPut();
                break;
            }
        }
    }

    public void render(Graphics2D g) {

        for (int i = 0; i < TeamObjects.size() && i != findSelected(); i++)
            TeamObjects.get(i).render(g);

        if (findSelected() != -1) {
            for (MovingObject O : TeamObjects)
                O.render(g);
            TeamObjects.get(findSelected()).render(g);
        }



    }

    public void dies() {
        if (findSelectedObject() instanceof Pirate)
            died_pirates.add(((Pirate) findSelectedObject()).getNumber());
        TeamObjects.remove(findSelectedObject());
        TeamObjects.get(0).is_moved = true;            //It is not accurate, but let's pretend that BOAT is moved and pirate is just died :)
    }

    public void update() {

        if (findSelected() != -1 && move_direction != 0) {
            findSelectedObject().update(move_direction);
            move_direction = 0;
        } else if (findSelected() != -1 && move_position[0] != -1 && move_position[1] != -1) {
            findSelectedObject().update(move_position[0], move_position[1]);
            move_position[0] = -1;
            move_position[1] = -1;
        }

        if (isMoved()) {
            findSelectedObject().unselect();
            summaryEvents();
            turn_is_made = true;
        }
    }

    private void summaryEvents() {
        for (MovingObject O : TeamObjects) {
            if (O instanceof Pirate) {
                if (((Pirate) O).isDrunk())
                    ((Pirate) O).sober();
            }
        }
    }

    private boolean isMoved() {
        boolean somebody_moved = false;
        for (MovingObject O : TeamObjects) {
            if (O.is_moved) {
                O.is_moved = false;
                somebody_moved = true;
            }
        }
        return somebody_moved;
    }

    public void incScore() {
        score++;
    }

    public void selectNext() {
        int index_of_selected_member = findSelected();
        if (index_of_selected_member != -1)
            findSelectedObject().unselect();
        TeamObjects.get(findNextCouldBeSelected(index_of_selected_member)).select();
    }

    private int findNextCouldBeSelected(int wasSelected) {
        for (int i = 0; i < TeamObjects.size(); i++) {
            if (TeamObjects.get((wasSelected + i + 1) % TeamObjects.size()).could_be_selected)
                return (wasSelected + i + 1) % TeamObjects.size();
        }

        return -1;
    }

    public int getScore() {
        return score;
    }

    public int getMove_direction() {
        return move_direction;
    }

    public void activateEvent() {
        if (findSelectedObject().getTile() == Tile.FORT_GIRL && TeamObjects.size() < 4) {
            ressurect();

        }
        if (findSelectedObject().getTile() == Tile.PLANE) {
            fly();
        }
    }

    private void fly() {
        if (findSelectedObject() instanceof Pirate)
            ((Pirate) findSelectedObject()).changePlane();

    }

    private void ressurect() {
        TeamObjects.add(new Pirate(number, died_pirates.removeFirst(), findSelectedObject().x_on_map, findSelectedObject().y_on_map));
        findSelectedObject().is_moved = true;
    }

    public void setMovePosition(int mouseX, int mouseY) {
        move_position[0] = mouseX;
        move_position[1] = mouseY;
    }
}
