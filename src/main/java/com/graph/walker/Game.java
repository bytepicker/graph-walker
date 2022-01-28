package com.graph.walker;

import java.util.*;

public class Game {
    private final List<Set<Node>> graph;

    private int level;
    private int currentNode = 0;
    private int lives;
    private boolean gameOver = false;

    Game() {
        graph = new ArrayList<>();
    }

    void addNode(int parent, int node) {
        if (parent == node) {
            throw new IllegalArgumentException("Can'\t connect node to itself");
        }

        if (parent < 0 || node < 0) {
            throw new IllegalArgumentException("Node number must be >= 0");
        }

        if (parent >= this.getLevel() || node >= this.getLevel()) {
            throw new IllegalArgumentException("Node number must be <= total nodes number");
        }

        graph.get(parent).add(new Node(node));
        graph.get(node).add(new Node(parent));
    }

    void initEmptySets(int nodes) {
        for (int i = 0; i < nodes; i++) {
            graph.add(new HashSet<>());
        }
    }

    void generateField(int nodes) {
        setLevel(nodes);
        currentNode = 0;
        lives = nodes + 3;

        initEmptySets(nodes);

        Random random = new Random();
        int randomLink;

        for (int i = 0; i < level; i++) {
            do {
                randomLink = random.nextInt(level);
            } while (randomLink == i);
            addNode(i, randomLink);
        }
    }

    void interact() {
        while (currentNode != level - 1 && lives != 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\nNodes: " + level + " Lives: " + lives);
            System.out.println("Current node: " + currentNode);

            try {
                System.out.print("Enter next node number: ");
                int input = sc.nextInt();
                if (input == currentNode) {
                    throw new IllegalArgumentException("A node can'\t link to itself");
                }
                if (input < 0 || input > getLevel()) {
                    throw new IllegalArgumentException("Value must be non-negative and below nodes count");
                }
                checkUserInputPath(input);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
                System.out.print("Want to continue? ");
                if (sc.next().equalsIgnoreCase("Y")) {
                    interact();
                } else {
                    System.exit(0);
                }
            }
        }
        roundFinished();
    }

    void roundFinished() {
        printNodes();
        if (lives == 0) {
            System.out.println("[ Game over ]");
            gameOver = true;
        } else {
            System.out.println("[ You won! Starting next level ]");
        }
        graph.clear();
    }

    void checkUserInputPath(int node) {
        if (graph.get(currentNode).stream().filter(e -> e.num == node).findFirst().isEmpty()) {
            lives--;
            System.out.println("[ No link from " + currentNode + " to " + node + " ]");
        } else {
            currentNode = node;
        }
    }

    void printNodes() {
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
        if (level < 3) {
            throw new IllegalArgumentException("At least 3 nodes required to generate field");
        }
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
