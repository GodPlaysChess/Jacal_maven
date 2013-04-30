package src.com.godplayschess.jacal;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Game implements KeyListener, MouseListener {
    boolean game_on;
    Screen screen;
    public int current_team;


    public Game() {
        game_on = true;
        screen = new Screen();


        current_team = 1;

        screen.addKeyListener(this);
        screen.addMouseListener(this);
        screen.setFocusTraversalKeysEnabled(false);
    }

    public Team getCurrentTeam() {

        if (current_team == 1)
            return GameData.Team1;
        else
            return GameData.Team2;


    }

    public void start() {
        while (game_on) {
            update();
            screen.render();
        }
    }

    public void update() {
        //everything required updates is here
        getCurrentTeam().update();
        if (getCurrentTeam().turn_is_made) {
            getCurrentTeam().turn_is_made = false;
            nextTour();

        }
    }

    private void nextTour() {
        GameData.nextTurn();
        current_team = GameData.getTours_made() % 2 + 1;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 97 && e.getKeyCode() <= 105) {
            if (getCurrentTeam().findSelected() != -1) {
                getCurrentTeam().setMove_direction(e.getKeyCode() - 96);
            }
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_T:
                if (getCurrentTeam().findSelected() != -1) {
                    getCurrentTeam().takeCoin();
                }
                break;
            case KeyEvent.VK_R:
                if (getCurrentTeam().findSelected() != -1) {
                    getCurrentTeam().activateEvent();
                }
                break;
            case KeyEvent.VK_K:
                if (getCurrentTeam().findSelected() > 0) {
                    getCurrentTeam().dies();
                }
                break;

            case KeyEvent.VK_TAB:
                getCurrentTeam().selectNext();
                System.out.println("TAB " + GameData.getTours_made() + "  current team = " + current_team);

                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = screen.getMousePosition().x;
        int mouseY = screen.getMousePosition().y;
        int mapX = (mouseX - Map.MAP_XSHIFT) / Map.FIELD_SIZE;
        int mapY = (mouseY - Map.MAP_YSHIFT) / Map.FIELD_SIZE;
        getCurrentTeam().setMovePosition(mapX, mapY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
