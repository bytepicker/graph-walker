package com.graph.walker;

public class Main {
    public static void main(String[] args) {
        int initialNodesNum = 3;
        while (!Game.gameOver){
            Game walker = new Game(initialNodesNum++);
            walker.generateField();
            walker.interact();
        }
    }
}