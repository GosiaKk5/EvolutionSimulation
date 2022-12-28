package org.example;

public class HellishPortal implements IChangePositionHandler {

    private Vector2d lowerLeft;
    private Vector2d upperRight;
    public HellishPortal(int width, int height){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d upperRight = new Vector2d(width, height);
    }
    @Override
    public boolean newPositionInMap(Vector2d newPosition) {
        return newPosition.follows(this.lowerLeft) && newPosition.precedes(this.upperRight);
    }
}
