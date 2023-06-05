package sma;

import com.google.gson.Gson;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class MainAgent extends Agent {
    List<AgentSolution> agentSolutionList=new ArrayList<>();

    @Override
    protected void setup() {
        DFAgentDescription dfAgentDescription=new DFAgentDescription();
        ServiceDescription serviceDescription=new ServiceDescription();
        serviceDescription.setType("q-learning");
        dfAgentDescription.addServices(serviceDescription);

        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour(this);

        sequentialBehaviour.addSubBehaviour(new Behaviour() {
            DFAgentDescription[] agentsDescriptions;
            @Override
            public void action() {
                try {
                    agentsDescriptions = DFService.search(this.myAgent, dfAgentDescription);
                    //System.out.println("agentsDescriptions.length : "+agentsDescriptions.length);
                    for (DFAgentDescription dfAD:agentsDescriptions) {
                        agentSolutionList.add(new AgentSolution(dfAD.getName()));
                    }
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean done() {
                return agentsDescriptions.length==Utils.NBR_AGENT;
            }
        });
        sequentialBehaviour.addSubBehaviour(new Behaviour() {
            int nbr=0;
            @Override
            public void action() {
                for (int i=0;i<agentSolutionList.size();i++){
                    //System.out.println("*********************************************for i = "+i);
                    ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
                    aclMessage.addReceiver(agentSolutionList.get(i).getAid());
                    aclMessage.setContent("getSolution");
                    send(aclMessage);

                    aclMessage=blockingReceive();
                    Gson gson = new Gson();
                    double[][] qTableAgent = gson.fromJson(aclMessage.getContent(), double[][].class);
                    agentSolutionList.get(i).setqTable(qTableAgent);
                    //System.out.println(agentSolutionList.get(i).getAid().getName()+" : ");
                    //showResult(qTableAgent);
                    //System.out.println("*********************************************END i = "+i);
                }
                nbr++;
            }

            @Override
            public boolean done() {
                return nbr==Utils.NBR_AGENT;
            }
        });

        sequentialBehaviour.addSubBehaviour(new OneShotBehaviour() {
            int nbrAction=0;
            int agentIndex;
            @Override
            public void action() {
                for (int i=0;i<agentSolutionList.size();i++){
                    //System.out.println("For : "+agentSolutionList.get(i).getAid().getName()+" : ");
                    //agentSolutionList.get(i).showResultAction();
                    if(nbrAction > agentSolutionList.get(i).getNbrActionFinal()){
                        nbrAction=agentSolutionList.get(i).getNbrActionFinal();
                        agentIndex=i;
                    }
                }
                System.out.println("index of the final agent result is : "+agentIndex);
                agentSolutionList.get(agentIndex).showResultAction();
            }

        });

        addBehaviour(sequentialBehaviour);

    }

    private void showResult(double[][] qTable){
        System.out.println("**************************Qtable****************");
        for (double []line:qTable){
            System.out.printf("[ ");
            for (double qvalue:line){
                System.out.printf(qvalue+" , ");
            }
            System.out.println(" ]");
        }
        System.out.println("*************************************************");
    }

}
