package org.example;

public record Plant(Vector2d position) implements IMapElement {
    public String toString() {
        return "*";
    }

}
