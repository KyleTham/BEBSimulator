package com.BEB;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class BEBSimulator {
    static int timeInterval = 1;

    // Create a list of Nodes with the amount inputted by user.
    static HashSet<Node> createList(int num){
        HashSet<Node> nodeList = new HashSet<Node>();
        for(int i = 0; i < num; i++){
            nodeList.add(new Node(i));
        }
        return nodeList;
    }

    static void simulate(int numberOfNodes, int numberOfTimeSlots, double probabilityNodeSendFrame){
        HashSet<Node> nodes = createList(numberOfNodes);
        int numOfFrames = 0;
        while(nodes.size() > 0) {
            System.out.printf("TIMEFRAME %d WITH %d SLOTS; TIME INTERVAL = %d %n", numOfFrames, numberOfTimeSlots, timeInterval);
            for (int i = 0; i < numberOfTimeSlots; i++) {
                List<Node> sent = new ArrayList<Node>();
                for (Node node : nodes) {
                    // If size of sent nodes = 2, that means there is a collision. We break out of the loop.
                    if(sent.size() == 2){
                        break;
                    }
                    if (node.sendFrame(probabilityNodeSendFrame)) {
                        sent.add(node);
                    }
                }
                // Only one node sent for the current timeframe; successful send.
                if (sent.size() == 1) {
                    System.out.println("[SEND " + sent.get(0).id + "]");
                    nodes.remove(sent.get(0));
                    sent.remove(0);
                    if(nodes.size() == 0){
                        break;
                    }
                    else {
                        continue;
                    }
                } else if (sent.size() == 0) {
                    System.out.println("[UNUSED]");
                    // Collision handling
                } else {
                    timeInterval *= 2;
                    Node temp1 = sent.get(0);
                    Node temp2 = sent.get(1);
                    for (Node node : sent) {
                        node.collision(timeInterval);
                    }
                    System.out.printf("[COLLISION %d %d]%n", temp1.id, temp2.id);
                    break;
                }
            }
            // Number of frames = how many complete trips of the total time slots + # of collisions.
            numOfFrames++;
        }
    }

    public static void main(String[] args) {
        /*
        Uncomment the lines below if you want to run from IDE.
         */
//        int numberOfNodes = 4;
//        int numberOfTimeSlots = 4;
//        double probabilityNodeSendFrame = 0.40;

        /*
        Below variables are used for running from command line.
        Comment out if you want to run from IDE.
         */
        int numberOfNodes = Integer.parseInt(args[0]);
        int numberOfTimeSlots = Integer.parseInt(args[1]);
        double probabilityNodeSendFrame = Double.parseDouble(args[2]);
        BEBSimulator bebSimulator = new BEBSimulator();
        bebSimulator.simulate(numberOfNodes, numberOfTimeSlots, probabilityNodeSendFrame);
    }
}