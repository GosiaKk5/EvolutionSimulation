package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMap implements IMap, IPositionChangeObserver{

    protected int height;
    protected int width;

    protected Map<Vector2d, Plant> plants = new HashMap<>();
    protected Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();

    public AbstractMap(int width, int height, int noStartPlants){
        this.height = height;
        this.width = width;


        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                animals.put(new Vector2d(x,y), new ArrayList<Animal>());
            }
        }
    }

    @Override
    public void placeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();

        //if canmoveto
        this.animals.get(position).add(animal);
        animal.addObserver(this);
        //else exception - zwierze nie moze byc dodane poza mapa?
    }

    public void removeAnimal(Animal animal){
        Vector2d position = animal.getPosition();
        animals.get(position).remove(animal);
    }

    public ArrayList<Animal> animalsAt(Vector2d position){
        if(animals.get(position) != null){
            return animals.get(position);
        }
        return null;
    }

    public Plant plantAt(Vector2d position){ //popraw usun if
        if(plants.size() != 0){
            return plants.get(position);
        }
        return null;

    }

    // do rysowania mapy
    public Object objectAt(Vector2d position){

        if (animals.get(position).size() != 0 ) {
            return animals.get(position).get(0);
        }

        return plants.get(position);
    }
    public boolean isOccupied(Vector2d position){

        return objectAt(position) != null;
    }

    public Vector2d getUpperCorner(){
        return new Vector2d(width-1, height-1);
    }

    public Vector2d getLowerCorner(){ return new Vector2d(0,0); }

    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(width-1, height-1));
    }

    public Map<Vector2d, Plant> getPlants(){
        return this.plants;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        animals.get(oldPosition).remove(animal);
        animals.get(newPosition).add(animal);
    }

}
