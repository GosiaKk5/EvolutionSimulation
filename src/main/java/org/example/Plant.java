package org.example;

public class Plant {
    private final Vector2d position;

    public Plant(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public String toString(){
        return "*";
    }

}
