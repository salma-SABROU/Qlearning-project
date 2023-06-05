package sma;

public class Utils {
    public static final int NBR_AGENT=5;
    public static final double ALPHA=0.1;
    public static final double GAMMA=0.9;
    public static final int MAX_EPOCH=1000;
    public static final int GRID_SIZE=3;
    public static final int ACTION_SIZE=4;

    public static final int[][] grid=new int[][]{
            {1,0,0},
            {-1,0,0},
            {0,0,0}
    };

    public static final int[][]actions=new int[][]{
            {0,-1},//gauche
            {0,1},//droite
            {1,0},//bas
            {-1,0}//haut
    };

    public static final int stateI=2;
    public static final int stateJ=0;
}
