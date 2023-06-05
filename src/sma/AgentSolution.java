package sma;

import jade.core.AID;

import static sma.Utils.ACTION_SIZE;

public class AgentSolution {
    private AID aid;
    private double[][] qTable;

    private int nbrActionFinal=0;

    public AgentSolution(AID aid) {
        this.aid = aid;
    }

    public AID getAid() {
        return aid;
    }

    public void setAid(AID aid) {
        this.aid = aid;
    }

    public double[][] getqTable() {
        return qTable;
    }

    public void setqTable(double[][] qTable) {
        this.qTable = qTable;
    }

    public void showResultAction(){
        int statI;
        int statJ;
        System.out.println("**************************ACTIONS****************");
        statJ=Utils.stateJ;
        statI=Utils.stateI;
        while (Utils.grid[statI][statJ]!=1){
            int st=statI*Utils.GRID_SIZE+statJ;
            int act=exploration(st);
            System.out.println("state : "+(statI*Utils.GRID_SIZE+statJ)+" action = "+act);
            statI=Math.max(0,Math.min(Utils.actions[act][0]+statI,Utils.GRID_SIZE-1));
            statJ=Math.max(0,Math.min(Utils.actions[act][1]+statJ,Utils.GRID_SIZE-1));
            this.nbrActionFinal++;
        }
        System.out.println("final state : "+(statI*Utils.GRID_SIZE+statJ)+"with nbrActionFinal== "+nbrActionFinal);

    }

    public int exploration(int st){
        double bestQ=0;
        int act=0;

        for (int i=0;i<ACTION_SIZE;i++){
            if (qTable[st][i]>bestQ){
                bestQ=qTable[st][i];
                act=i;
            }
        }
        return act;
    }

    public int getNbrActionFinal() {
        return nbrActionFinal;
    }
}
