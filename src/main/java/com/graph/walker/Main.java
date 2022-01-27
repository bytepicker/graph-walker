package com.graph.walker;

public class Main {
    public static void main(String[] args) {
        int initialNodesNum = 3;
        Game walker = new Game();
        while (!walker.isGameOver()){
            walker.generateField(initialNodesNum++);
            walker.interact();
        }
    }
}