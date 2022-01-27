package com.graph.walker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void constructorShouldCreateGraph() {
        assertTrue(game.getGraph() != null);
    }

    @Test
    public void constructorShouldGenerateEmptyGraph() {
        assertTrue(game.getGraph().isEmpty());
    }

    @Test
    public void generateFieldShouldThrowExceptionNegativeInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.generateField(-5);
        });

        String expectedMessage = "At least 3 nodes required to generate field";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void generateFieldShouldThrowExceptionPositiveInputLessThanThree() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.generateField(2);
        });

        String expectedMessage = "At least 3 nodes required to generate field";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void generateFieldShouldCreateSets() {
        game.generateField(100);
        assertEquals(100, game.getGraph().size());
    }

    @Test
    public void generateFieldShouldCreateEmptySets() {
        boolean isEmpty = true;
        game.generateField(10);

        if (!game.getGraph().stream().filter(e -> e.isEmpty()).findFirst().isEmpty()) {
            isEmpty = false;
        }

        assertTrue(isEmpty);
    }

    @Test
    public void addNodeShouldThrowExceptionOnSameNodes() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.addNode(1, 1);
        });

        String expectedMessage = "Can'\t connect node to itself";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void addNodeShouldThrowExceptionOnNegativeNodeNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.addNode(-1, 1);
        });

        String expectedMessage = "Node number must be >= 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void addNodeShouldThrowExceptionOnBothNegativeNodeNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.addNode(-1, -5);
        });

        String expectedMessage = "Node number must be >= 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void addNodeShouldThrowExceptionOnNodeNumberEqualToLevel() {
        game.setLevel(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.addNode(3, 1);
        });

        String expectedMessage = "Node number must be <= total nodes number";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void addNodeShouldThrowExceptionOnNodeNumberMoreThanLevel() {
        game.setLevel(3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.addNode(5, 1);
        });

        String expectedMessage = "Node number must be <= total nodes number";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void addNodeShouldEnlargesGraphTwoTimes() {
        int parent = 0;
        int node = 1;

        game.initEmptySets(3);
        game.setLevel(3);

        int oldParentSize = game.getGraph().get(parent).size();
        int oldNodeSize = game.getGraph().get(node).size();

        game.addNode(parent, node);

        int newParentSize = game.getGraph().get(parent).size();
        int newNodeSize = game.getGraph().get(node).size();

        assertTrue(oldParentSize == newParentSize - 1 &&
                oldNodeSize == newNodeSize - 1);
    }


    @Test
    public void setLevelShouldThrowExceptionOnLessThanThree() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            game.setLevel(1);
        });

        String expectedMessage = "At least 3 nodes required to generate field";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}