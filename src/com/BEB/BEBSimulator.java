package com.BEB;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class BEBSimulator {
    static int timeInterval = 1;
    public BEBSimulator(){
    }

    static HashSet<Node> createList(int num){
        HashSet<Node> nodeList = new HashSet<Node>();
        for(int i = 0; i < num; i++){
            nodeList.add(new Node(i));
        }
        return nodeList;
    }

    static void simulate(int numberOfNodes, int numberOfTimeSlots, double probabilityNodeSendFrame){
        HashSet<Node> nodes = createList(numberOfNodes);
        while(nodes.size() > 0) {
            List<Node> deleted = new ArrayList<Node>();
            for (int i = 0; i < numberOfTimeSlots; i++) {
                List<Node> sent = new ArrayList<Node>();
                for (Node node : nodes) {
                    if (node.sendFrame(probabilityNodeSendFrame)) {
                        sent.add(node);
                    }
                }
                if (sent.size() == 1) {
                    System.out.println("[SEND " + sent.get(0).id + "]");
                    deleted.add(sent.get(0));
                    break;
                } else if (sent.size() == 0) {
                    System.out.println("[UNUSED]");
                } else {
                    timeInterval *= 2;
                    String collision = "[COLLISION ";
                    for (Node node : sent) {
                        node.collision(timeInterval);
                        collision += node.id + " ";
                    }
                    collision += "]";
                    System.out.println(collision);
                }
            }
            if(deleted.size() > 0){
                nodes.remove(deleted.get(0));
            }
        }
    }

    public static void main(String[] args) {
        int numberOfNodes = 4;
        int numberOfTimeSlots = 4;
        double probabilityNodeSendFrame = 0.40;
//        int numberOfNodes = Integer.parseInt(args[0]);
//        int numberOfTimeSlots = Integer.parseInt(args[1]);
//        double probabilityNodeSendFrame = Double.parseDouble(args[2]);
        BEBSimulator bebSimulator = new BEBSimulator();
        bebSimulator.simulate(numberOfNodes, numberOfTimeSlots, probabilityNodeSendFrame);
    }
}