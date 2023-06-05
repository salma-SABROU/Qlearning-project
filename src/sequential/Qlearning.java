package sequential;

import java.util.Random;

public class Qlearning {
    private final double ALPHA=0.1;
    private final double GAMMA=0.9;
    private final int MAX_EPOCH=1000;
    private final int GRID_SIZE=3;
    private final int ACTION_SIZE=4;

    private int[][] grid=new int[GRID_SIZE][GRID_SIZE];
    private double[][] qTable=new double[GRID_SIZE*GRID_SIZE][ACTION_SIZE];
    private int[][]actions;
    private int stateI;
    private int stateJ;

    public Qlearning(){
        actions=new int[][]{
                {0,-1},//gauche
                {0,1},//droite
                {1,0},//bas
                {-1,0}//haut
        };
        //les recompanse ::
        grid=new int[][]{
                {1,0,0},
                {-1,0,0},
                {0,0,0}
        };
    }

    private void resetState (){
        stateI=2;
        stateJ=0;
    }

    private int chooseAction(double eps){
        Random random=new Random();
        double bestQ=0;
        int act=0;
        if (random.nextDouble()<eps){
            //exploration
            act= random.nextInt(ACTION_SIZE);
        }else {
            //exploitation
            int st=stateI*GRID_SIZE+stateJ;
            for (int i=0;i<ACTION_SIZE;i++){
                if (qTable[st][i]>bestQ){
                    bestQ=qTable[st][i];
                    act=i;
                }
            }
        }
        return act;
    }

    private int executeAction(int act){
        stateI=Math.max(0,Math.min(actions[act][0]+stateI,GRID_SIZE-1));
        stateJ=Math.max(0,Math.min(actions[act][1]+stateJ,GRID_SIZE-1));
        return stateI*GRID_SIZE+stateJ;
    }
    private boolean finished(){
        return grid[stateI][stateJ]==1;
    }

    private void showResult(){
        System.out.println("**************************Qtable****************");
        for (double []line:qTable){
            System.out.printf("[ ");
            for (double qvalue:line){
                System.out.printf(qvalue+" , ");
            }
            System.out.println(" ]");
        }
        System.out.println("*************************************************");
        resetState();
        while (!finished()){
            int act=chooseAction(0);
            System.out.println("state : "+(stateI*GRID_SIZE+stateJ)+" action = "+act);
            executeAction(act);
        }
        System.out.println("final state : "+(stateI*GRID_SIZE+stateJ));
    }
    public void runQlearning(){
        int it=0;
        int currentState;
        int nextState;
        int act1,act;
        while (it<MAX_EPOCH){
            resetState();
            while (!finished()){
                currentState=stateI*GRID_SIZE+stateJ;

                act=chooseAction(0.4);
                nextState=executeAction(act);
                //System.out.println(stateI+" : "+stateJ+" == "+grid[stateI][stateJ]);

                act1=chooseAction(0);
                qTable[currentState][act]=qTable[currentState][act]+ALPHA*(grid[stateI][stateJ]+GAMMA*qTable[nextState][act1]-qTable[currentState][act]);
            }
            it++;
        }
        showResult();
    }


}
