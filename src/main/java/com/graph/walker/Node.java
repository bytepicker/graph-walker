package com.graph.walker;

import java.util.Objects;

public class Node {
    final int num;

    public Node(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Node #" + num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return num == node.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }
}