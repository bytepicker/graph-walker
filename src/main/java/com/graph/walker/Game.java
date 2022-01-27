package com.graph.walker;

import java.util.*;

public class Game {
    private List<Set<Node>> graph;

    private int level;
    private int currentNode = 0;
    private int lives;
    private boolean gameOver = false;

    Game() {
        this.graph = new ArrayList<>();
    }

    void addNode(int parent, int node){
        if(parent == node){
            throw new IllegalArgumentException("Can'\t connect node to itself");
        }

        if(parent < 0 || node < 0){
            throw new IllegalArgumentException("Node number must be >= 0");
        }

        if(parent >= this.getLevel() || node >= this.getLevel()){
            throw new IllegalArgumentException("Node number must be <= total nodes number");
        }

        graph.get(parent).add(new Node(node));
        graph.get(node).add(new Node(parent));
    }

    void initEmptySets(int nodes){
        for (int i = 0; i < nodes; i++) {
            graph.add(new HashSet<>());
        }
    }

    void generateField(int nodes){
        if(nodes < 3){
            throw new IllegalArgumentException("At least 3 nodes required to generate field");
        }

        this.level = nodes;
        this.lives = nodes + 3;

        initEmptySets(nodes);

        Random random = new Random();
        int randomLink;

        for(int i = 0; i < this.level; i++){
            do {
                randomLink = random.nextInt(this.level);
            } while (randomLink == i);
            addNode(i, randomLink);
        }
    }

    void interact(){
        while(this.currentNode != this.level-1 && this.lives != 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\nNodes: " + this.level + " Lives: " + this.lives);
            System.out.println("Current node: " + this.currentNode);
            System.out.print("Enter next node number: ");
            checkUserInputPath(sc.nextInt());
        }
        roundFinished();
    }

    void roundFinished(){
        printNodes();
        if(this.lives == 0){
            System.out.println("Game over");
            gameOver = true;
        } else {
            System.out.println("You won! Starting next level");
        }
    }

    void checkUserInputPath(int node){
        if(graph.get(this.currentNode).stream().filter(e -> e.num == node).findFirst().isEmpty()){
            this.lives--;
            System.out.println("No link from " + this.currentNode + " to " + node);
        } else {
            this.currentNode = node;
        }
    }

    void printNodes(){
        for (int i = 0; i < graph.size(); i++) {
            System.out.println("\nLinks of Node #" + i);
            System.out.println(graph.get(i));
        }
        System.out.println();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<Set<Node>> getGraph() {
        return graph;
    }

    public void setLevel(int level) {
        if(level < 3){
            throw new IllegalArgumentException("At least 3 nodes required to generate field");
        }
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
