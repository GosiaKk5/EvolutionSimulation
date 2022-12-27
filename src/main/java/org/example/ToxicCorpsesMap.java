package org.example;

import java.util.*;

public class ToxicCorpsesMap extends AbstractMap{

    private ArrayList<Vector2d> freeFields;
    private HashMap<Vector2d, Integer> allFieldsWithDeaths;

    public ToxicCorpsesMap(int width, int height, int noStartPlants){
        super(width, height, noStartPlants);
        this.allFieldsWithDeaths = new HashMap<>();
        this.freeFields = new ArrayList<>();
        setFields();
        addStartPlants(noStartPlants);

    }

    //to delate
    public HashMap<Vector2d, Integer> getAllFieldsWithDeaths(){
        return this.allFieldsWithDeaths;
    }
    public ArrayList<Vector2d> getFreeFields(){
        return this.freeFields;
    }
    //

    private void setFields(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                allFieldsWithDeaths.put(new Vector2d(x,y), 0);
                freeFields.add(new Vector2d(x, y));
            }
        }
    }

    private void sortFreeFields(){
        this.freeFields.sort((e1 ,e2)-> allFieldsWithDeaths.get(e1) - allFieldsWithDeaths.get(e2));
    }

    public void addStartPlants(int noPlants){
        Random random = new Random();
        for(int i = 0; i < noPlants; i++){
            int index = random.nextInt(0, freeFields.size());
            Vector2d newPlantPosition = this.freeFields.get(index);
            Plant newPlant = new Plant(newPlantPosition);
            this.freeFields.remove(newPlantPosition);
            this.plants.put(newPlantPosition, newPlant);
        }
    }

    @Override
    public void addPlants(int noPlants) {
        sortFreeFields();
        Random random = new Random();
        for(int i = 0; i < noPlants; i++){
            if(this.freeFields.size()>0){

                int noFertileFields = (int) Math.round((double) this.freeFields.size()/5);
                int indexProbability = random.nextInt(0, 10);
                int index = 0;

                if(indexProbability < 8 && noFertileFields > 0){
                    index = random.nextInt(0,noFertileFields);
                } else{
                    index = random.nextInt(noFertileFields,this.freeFields.size());
                }

                Vector2d newPlantPosition = this.freeFields.get(index);
                Plant newPlant = new Plant(newPlantPosition);
                
                this.freeFields.remove(newPlantPosition);
                this.plants.put(newPlantPosition, newPlant);
            }
        }
    }

    @Override
    public void removePlant(Vector2d position) {
        plants.remove(position);
        freeFields.add(position);
    }

    @Override
    public void removeAnimal(Animal animal){
        super.removeAnimal(animal);
        Vector2d key = animal.getPosition();
        allFieldsWithDeaths.put(key, allFieldsWithDeaths.get(key) + 1);
    }
}
