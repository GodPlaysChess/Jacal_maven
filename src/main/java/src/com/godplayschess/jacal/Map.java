package src.com.godplayschess.jacal;

import java.awt.*;
import java.util.ArrayList;

public class Map {

    public Tile[][] Map = new Tile[NFIELDS][NFIELDS];

    public final static int NFIELDS = 13;
    public final static int FIELD_SIZE = 60;
    public final static int MAP_XSHIFT = 100;
    public final static int MAP_YSHIFT = 50;

    private final int FOREST_AMOUNT = 10;
    private final int CHEST1_AMOUNT = 10;
    private final int CHEST2_AMOUNT = 10;
    private final int CHEST3_AMOUNT = 10;
    private final int CHEST4_AMOUNT = 10;
    private final int CHEST5_AMOUNT = 10;
    private final int CROCO_AMOUNT = 10;
    private final int FORTS_AMOUNT = 10;
    private final int FORTS_GIRL_AMOUNT = 10;
    private final int HOLES_AMOUNT = 10;
    private final int PLANES_AMOUNT = 10;
    private final int HORSES_AMOUNT = 1000;

    private final int ARROW1_AMOUNT = 10;
    private final int ARROW2_AMOUNT = 10;
    private final int ARROW3_AMOUNT = 10;
    private final int ARROW4_AMOUNT = 10;

    private final int JUNGLES_AMOUNT = 10;
    private final int DESERTS_AMOUNT = 10;
    private final int SWAMP_AMOUNT = 10;
    private final int WATERFALLS_AMOUNT = 10; // <---coins

    private final int X2_AMOUNT = 10;
    private final int RHUM_AMOUNT = 10;
    private final int PARACHUTE_AMOUNT = 10;

    private final int CANNIBAL_AMOUNT = 10;

    private final int CANNON_AMOUNT = 10;

    private ArrayList<Integer> TerraIncoginta = new ArrayList<Integer>(NFIELDS * NFIELDS - 4 * (NFIELDS - 2));


    public Map() {
        //map initialization -> all unknowns
        for (int i = 1; i < Map.length - 1; i++)
            for (int j = 1; j < Map.length - 1; j++)
                Map[i][j] = new Tile();

        for (int i = 0; i < 13; i++) {
            Map[i][0] = new Tile(Tile.WATER);
            Map[i][12] = new Tile(Tile.WATER);
            Map[0][i] = new Tile(Tile.WATER);
            Map[12][i] = new Tile(Tile.WATER);
        }

        Map[1][1] = new Tile(Tile.WATER);
        Map[11][11] = new Tile(Tile.WATER);
        Map[1][11] = new Tile(Tile.WATER);
        Map[11][1] = new Tile(Tile.WATER);

        initializeUnknownTiles();

    }

    private void initializeUnknownTiles() {
        for (int i = 0; i < FOREST_AMOUNT; i++) {
            TerraIncoginta.add(Tile.FOREST);
        }
        for (int i = 0; i < CHEST1_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CHEST1);
        }
        for (int i = 0; i < CHEST2_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CHEST2);
        }
        for (int i = 0; i < CHEST3_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CHEST3);
        }
        for (int i = 0; i < CHEST4_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CHEST4);
        }
        for (int i = 0; i < CHEST5_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CHEST5);
        }
        for (int i = 0; i < CROCO_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CROCODILE);
        }
        for (int i = 0; i < FORTS_AMOUNT; i++) {
            TerraIncoginta.add(Tile.FORT);
        }
        for (int i = 0; i < FORTS_GIRL_AMOUNT; i++) {
            TerraIncoginta.add(Tile.FORT_GIRL);
        }
        for (int i = 0; i < HOLES_AMOUNT; i++) {
            TerraIncoginta.add(Tile.HOLE);
        }

        for (int i = 0; i < ARROW1_AMOUNT; i++) {
            TerraIncoginta.add(Tile.ARROW1);
        }
        for (int i = 0; i < ARROW2_AMOUNT; i++) {
            TerraIncoginta.add(Tile.ARROW2);
        }
        for (int i = 0; i < ARROW3_AMOUNT; i++) {
            TerraIncoginta.add(Tile.ARROW3);
        }
        for (int i = 0; i < ARROW4_AMOUNT; i++) {
            TerraIncoginta.add(Tile.ARROW4);
        }


        for (int i = 0; i < X2_AMOUNT; i++) {
            TerraIncoginta.add(Tile.ICE_2X);
        }
        for (int i = 0; i < JUNGLES_AMOUNT; i++) {
            TerraIncoginta.add(Tile.JUNGLE);
        }
        for (int i = 0; i < DESERTS_AMOUNT; i++) {
            TerraIncoginta.add(Tile.DESERT);
        }
        for (int i = 0; i < SWAMP_AMOUNT; i++) {
            TerraIncoginta.add(Tile.SWAMP);
        }
        for (int i = 0; i < WATERFALLS_AMOUNT; i++) {
            TerraIncoginta.add(Tile.WATERFALL);
        }

        for (int i = 0; i < RHUM_AMOUNT; i++) {
            TerraIncoginta.add(Tile.RHUM);
        }

        for (int i = 0; i < PARACHUTE_AMOUNT; i++) {
            TerraIncoginta.add(Tile.PARACHUTE);
        }
        for (int i = 0; i < CANNON_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CANNON);
        }
        for (int i = 0; i < CANNIBAL_AMOUNT; i++) {
            TerraIncoginta.add(Tile.CANNIBAL);
        }
        for (int i=0; i<PLANES_AMOUNT;i++){
            TerraIncoginta.add(Tile.PLANE);
        }
        for (int i=0; i<HORSES_AMOUNT;i++){
            TerraIncoginta.add(Tile.HORSE);
        }



    }

    public void openTile(int x, int y) {
        int random_number = (int) (Math.random() * TerraIncoginta.size());
        if (TerraIncoginta.get(random_number) > 80 && TerraIncoginta.get(random_number) < 85)
            Map[x][y] = new Arrow(TerraIncoginta.remove(random_number));
        else if (TerraIncoginta.get(random_number) == Tile.CANNON)
            Map[x][y] = new Cannon(TerraIncoginta.remove(random_number));
        else
            Map[x][y] = new Tile(TerraIncoginta.remove(random_number));
    }


    public void render(Graphics2D g) {
        renderTiles(g);
        //   renderGrid(g);
    }

    private void renderGrid(Graphics2D g) {

    }


    private void renderTiles(Graphics2D g) {
        for (int i = 0; i < Map.length; i++) {
            for (int j = 0; j < Map.length; j++) {
                Map[i][j].render(g, getXCoordinate(i), getYCoordinate(j));
            }
        }
    }

    public static int getXCoordinate(int xfield) {
        return (xfield * FIELD_SIZE + MAP_XSHIFT);
    }

    public static int getYCoordinate(int yfield) {
        return (yfield * FIELD_SIZE + MAP_YSHIFT);
    }


    public void openChest(int x_on_map, int y_on_map, int chest_type) {
        Map[x_on_map][y_on_map] = new Tile(70 + chest_type, chest_type);
    }

    public int getTileType(int x, int y) {
        try {
            return Map[x][y].type;
        } catch (IndexOutOfBoundsException IE) {
            return -1;
        }
    }
}
