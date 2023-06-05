package sma;

import com.google.gson.Gson;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class QlearningAgent extends Agent {
    private double[][] qTable=new double[Utils.GRID_SIZE*Utils.GRID_SIZE][Utils.ACTION_SIZE];
    private int stateI;
    private int stateJ;

    @Override
    protected void setup() {
        DFAgentDescription dfAgentDescription=new DFAgentDescription();
        dfAgentDescription.setName(getAID());
        ServiceDescription serviceDescription=new ServiceDescription();
        serviceDescription.setType("q-learning");
        serviceDescription.setName("q-learning");
        dfAgentDescription.addServices(serviceDescription);
        try {
            DFService.register(this,dfAgentDescription);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();

                if(receivedMSG!=null){
                    //System.out.println(" inside Agent action after recieve"+receivedMSG.getContent());
                    double[][] qTableResult=runQlearning();

                    Gson gson = new Gson();
                    String json = gson.toJson(qTableResult);


                    ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
                    aclMessage.setContent(json);
                    aclMessage.addReceiver(receivedMSG.getSender());
                    send(aclMessage);
                }else {
                    block();
                }
            }
        });
    }

    private int chooseAction(double eps){
        Random random=new Random();
        double bestQ=0;
        int act=0;
        if (random.nextDouble()<eps){
            //exploration
            act= random.nextInt(Utils.ACTION_SIZE);
        }else {
            //exploitation
            int st=stateI*Utils.GRID_SIZE+stateJ;
            for (int i=0;i<Utils.ACTION_SIZE;i++){
                if (qTable[st][i]>bestQ){
                    bestQ=qTable[st][i];
                    act=i;
                }
            }
        }
        return act;
    }
    private int executeAction(int act){
        stateI=Math.max(0,Math.min(Utils.actions[act][0]+stateI,Utils.GRID_SIZE-1));
        stateJ=Math.max(0,Math.min(Utils.actions[act][1]+stateJ,Utils.GRID_SIZE-1));
        return stateI*Utils.GRID_SIZE+stateJ;
    }
    private boolean finished(){
        return Utils.grid[stateI][stateJ]==1;
    }
    public double[][] runQlearning(){
        int it=0;
        int currentState;
        int nextState;
        int act1,act;
        while (it<Utils.MAX_EPOCH){
            stateI=Utils.stateI;
            stateJ=Utils.stateJ;
            while (!finished()){
                currentState=stateI*Utils.GRID_SIZE+stateJ;

                act=chooseAction(0.4);
                nextState=executeAction(act);

                act1=chooseAction(0);
                qTable[currentState][act]=qTable[currentState][act]+Utils.ALPHA*(Utils.grid[stateI][stateJ]+Utils.GAMMA*qTable[nextState][act1]-qTable[currentState][act]);
            }
            it++;
        }
        return qTable;
    }
}
