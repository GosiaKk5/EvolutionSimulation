package org.example;

public interface IPositionChangeObserver {
    void positionChanged(Animal element, Vector2d oldPosition, Vector2d newPosition);
}
